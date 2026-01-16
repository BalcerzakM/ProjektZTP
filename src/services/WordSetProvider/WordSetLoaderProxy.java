package services.WordSetProvider;

import models.LanguageCERFLevel;
import models.User;
import models.WordSet;

public class WordSetLoaderProxy implements WordsetProvider{
    private final WordSetLoader wordSetLoader;

    public WordSetLoaderProxy(WordSetLoader wordSetLoader) {
        this.wordSetLoader = wordSetLoader;
    }

    @Override
    public WordSet getWordSet(String filename, User user) throws Exception {
        WordSet wordSet = wordSetLoader.getWordSet(filename, user);
        LanguageCERFLevel wordSetLevel = wordSet.getCERFLevel();
        LanguageCERFLevel userLevel = user.getLanguageLevel();
        if(LanguageCERFLevel.isAccessAllowed(wordSetLevel, userLevel)) {
            return wordSet;
        } else {
            throw new Exception("Twój poziom znajomości języka (" + userLevel + ") jest zbyt niski dla tego zestawu. Poćwicz coś łatwiejszego.");
        }
    }
}
