package models;

public class User {
    private String username;
    private String langCEFRLevel;

    public User(String username, String languageLevel) {
        this.username = username;
        this.languageLevel = languageLevel;
    }

    public String getUsername() {
        return username;
    }

    public String getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(String languageLevel) {
        this.languageLevel = languageLevel;
    }

}
