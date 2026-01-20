package services.WordSetProvider;

import app.AppContext;
import models.User;
import models.WordSet;

/**
 * Podstawowa implementacja dostawcy zestawów słów.
 *
 * Klasa odpowiada wyłącznie za fizyczne załadowanie
 * zestawu słów z dostępnego źródła danych
 * poprzez kontekst aplikacji.
 *
 * Logika kontroli dostępu do zestawów realizowana jest
 * przez warstwę proxy.
 */
public class WordSetLoader implements WordsetProvider {
    private final AppContext context;

    public WordSetLoader(AppContext context) {
        this.context = context;
    }

    /**
     * Ładuje zestaw słów na podstawie nazwy pliku.
     *
     * @param filename nazwa pliku zestawu słów
     * @param user aktualny użytkownik
     * @return załadowany zestaw słów
     * @throws Exception gdy wystąpi błąd ładowania danych
     */
    @Override
    public WordSet getWordSet(String filename, User user) throws Exception {
        return context.getNewWordSetFromDb(filename);
    }
}
