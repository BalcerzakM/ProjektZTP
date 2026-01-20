package models;

/**
 * Reprezentuje użytkownika aplikacji.
 *
 * Przechowuje dane identyfikacyjne użytkownika
 * oraz aktualny poziom językowy, który jest
 * aktualizowany na podstawie postępów w nauce.
 *
 * Klasa stanowi element modelu domenowego aplikacji.
 */
public class User {
    /**
     * Nazwa użytkownika.
     */
    private final String username;
    /**
     * Hasło użytkownika.
     *
     * Przechowywane w postaci jawnej
     * jedynie na potrzeby projektu.
     */
    private final String password;
    /**
     * Aktualny poziom językowy użytkownika.
     */
    private LanguageCERFLevel langLevel;

    public User(String username, String password, LanguageCERFLevel langLevel) {
        this.username = username;
        this.password = password;
        this.langLevel = langLevel;
    }

    public String getUsername() {
        return username;
    }

    public LanguageCERFLevel getLanguageLevel() {
        return langLevel;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Aktualizuje poziom językowy użytkownika
     * na podstawie liczby zdobytych punktów.
     *
     * Metoda porównuje postęp użytkownika z progami
     * poziomów CEFR i ustawia najwyższy osiągnięty poziom.
     */
    public void updateLanguageLevel(int levelProgress) {
        for (LanguageCERFLevel languageLevel : LanguageCERFLevel.values()) {
            if (levelProgress >= languageLevel.getMinPoints()) {
                this.langLevel = languageLevel;
            }
        }
    }

}
