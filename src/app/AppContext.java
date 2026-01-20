package app;

import models.*;
import services.WordSetProvider.WordSetLoader;
import services.WordSetProvider.WordSetLoaderProxy;
import services.WordSetProvider.WordsetProvider;
import services.observers.ReviewScheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Centralny kontekst aplikacji.
 *
 * Klasa pełni rolę wspólnego punktu dostępu do aktualnego stanu aplikacji,
 * przechowując informacje o:
 * - aktualnie zalogowanym użytkowniku,
 * - jego statystykach,
 * - aktualnie wybranym zestawie słówek,
 * - mechanizmach dostępu do danych oraz planowania powtórek.
 *
 * AppContext integruje kilka wzorców projektowych:
 * - Singleton (pośrednio, poprzez klasę Connector),
 * - Proxy (kontrolowany dostęp do zestawów słówek),
 * - Observer (współpraca z ReviewScheduler).
 *
 * Klasa jest wykorzystywana głównie przez kontrolery jako warstwa pośrednia
 * pomiędzy logiką aplikacji a trwałym źródłem danych.
 */
public class AppContext {
    private User currentUser;
    private Statistics currentUsersStatistics;
    private WordSet currentWordSet;
    private final ReviewScheduler reviewScheduler = new ReviewScheduler();
    private final WordsetProvider wordSetProvider;

    /**
     * Tworzy kontekst aplikacji oraz inicjalizuje mechanizm
     * bezpiecznego dostępu do zestawów słówek przy użyciu wzorca Proxy.
     */
    public AppContext() {
        WordSetLoader realLoader = new WordSetLoader(this);
        this.wordSetProvider = new WordSetLoaderProxy(realLoader);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public Statistics getCurrentUserStatistics() {
        return currentUsersStatistics;
    }

    public void setUserStatistics(Statistics statistics) {
        this.currentUsersStatistics = statistics;
    }

    public WordSet getCurrentWordSet() {
        return currentWordSet;
    }

    public void setCurrentWordSet(WordSet currentWordSet) {
        this.currentWordSet = currentWordSet;
    }

    public ReviewScheduler getReviewScheduler() {
        return reviewScheduler;
    }

    /**
     * Ładuje zestaw słówek z uwzględnieniem uprawnień użytkownika.
     *
     * Metoda wykorzystuje wzorzec Proxy do kontroli dostępu
     * do zasobów edukacyjnych.
     *
     * @param filename nazwa pliku zestawu słówek
     * @return załadowany zestaw słówek
     * @throws Exception jeśli dostęp do zestawu jest niedozwolony
     */
    public WordSet loadWordSetSecurely(String filename) throws Exception {
        return wordSetProvider.getWordSet(filename, currentUser);
    }


    public List<String> getDatabaseNamesList() {
        try {
            return Connector.getInstance().getAviableDatabaseNames();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WordSet getNewWordSetFromDb(String fileName) {
        try {
            return Connector.getInstance().readWordSetFromFile(fileName + ".txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getUserNamesList() {
        try {
            return Connector.getInstance().getUsersList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserPasswordFromDb(String username) {
        try {
            return Connector.getInstance().readUserPasswordFromFile(username + ".txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Wczytuje wszystkie dane użytkownika z bazy danych.
     *
     * @param username nazwa użytkownika
     * @return obiekt użytkownika
     */
    public User getNewUserFromDb(String username) {
        try {
            return Connector.getInstance().readUserFromFile(username + ".txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Wczytuje statystyki użytkownika z bazy danych.
     *
     * @param username nazwa użytkownika
     * @return statystyki użytkownika
     */
    public Statistics getNewStatisticsFromDb(String username) {
        try {
            return Connector.getInstance().readStatisticsFromFile(username + ".txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Zapisuje nowego użytkownika w bazie danych wraz
     * z początkowymi statystykami.
     *
     * @param user nowo utworzony użytkownik
     */
    public void saveNewUserToDb(User user) {
        Statistics stats = new Statistics(0, 0, 0, 0, 0, 0, 0);
        try {
            Connector.getInstance().saveUserToFile(user, stats);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Zapisuje dane aktualnego użytkownika i jego statystyki
     * przed zakończeniem pracy aplikacji.
     */
    public void saveToDbAndExit() {
        if (currentUser != null & currentUsersStatistics != null){
            try {
                Connector.getInstance().saveUserToFile(this.currentUser, this.currentUsersStatistics);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

