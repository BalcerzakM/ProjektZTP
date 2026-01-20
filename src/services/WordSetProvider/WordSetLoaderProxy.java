package services.WordSetProvider;

import models.LanguageCERFLevel;
import models.User;
import models.WordSet;

/**
 * Proxy dostawcy zestawów słów.
 *
 * Klasa pośredniczy w dostępie do zestawów słów,
 * rozszerzając proces ich ładowania o kontrolę
 * dostępu zależną od poziomu językowego użytkownika.
 */
public class WordSetLoaderProxy implements WordsetProvider{
    private final WordSetLoader wordSetLoader;

    public WordSetLoaderProxy(WordSetLoader wordSetLoader) {
        this.wordSetLoader = wordSetLoader;
    }

    /**
     * Zwraca zestaw słów, jeżeli użytkownik spełnia
     * wymagania poziomu językowego.
     *
     * @param filename nazwa pliku zestawu słów
     * @param user aktualny użytkownik
     * @return zestaw słów dostępny dla użytkownika
     * @throws Exception gdy poziom użytkownika
     *         nie pozwala na dostęp do zestawu
     *         lub wystąpi błąd ładowania danych
     */
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
