package services.WordSetProvider;

import models.User;
import models.WordSet;

/**
 * Dostawca zestawów słów wykorzystywanych w trybach nauki.
 *
 * Interfejs definiuje jednolity punkt dostępu do źródeł
 * danych z zestawami słów, niezależnie od sposobu ich
 * ładowania lub dodatkowych mechanizmów kontroli.
 */
public interface WordsetProvider {

    /**
     * Zwraca zestaw słów dla danego użytkownika.
     *
     * @param filename nazwa pliku zestawu słów
     * @param user użytkownik, dla którego pobierany jest zestaw
     * @return zestaw słów
     * @throws Exception gdy zestaw nie może zostać pobrany
     *         lub dostęp do niego jest zabroniony
     */
    WordSet getWordSet(String filename, User user) throws Exception;
}
