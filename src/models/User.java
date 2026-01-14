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

    public void setLanguageLevel(LanguageCERFLevel languageLevel) {
        this.langLevel = languageLevel;
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if(isCorrectPassword(oldPassword)) {
            this.password = newPassword;
            return true;
        } else {
            return false;
        }
    }

}
