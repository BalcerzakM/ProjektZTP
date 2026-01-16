package services.WordSetProvider;

import models.User;
import models.WordSet;

public interface WordsetProvider {
    WordSet getWordSet(String filename, User user) throws Exception;
}
