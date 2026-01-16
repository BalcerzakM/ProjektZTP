package models;

public class User {
    private final String username;
    private String password;
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

    public void updateLanguageLevel(int levelProgress) {
        for (LanguageCERFLevel languageLevel : LanguageCERFLevel.values()) {
            if (levelProgress >= languageLevel.getMinPoints()) {
                this.langLevel = languageLevel;
            }
        }
    }

}
