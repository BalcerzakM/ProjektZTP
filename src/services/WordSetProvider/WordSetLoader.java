package services.WordSetProvider;

import app.AppContext;
import models.User;
import models.WordSet;

public class WordSetLoader implements WordsetProvider {
    private final AppContext context;

    public WordSetLoader(AppContext context) {
        this.context = context;
    }

    @Override
    public WordSet getWordSet(String filename, User user) throws Exception {
        return context.getNewWordSetFromDb(filename);
    }
}
