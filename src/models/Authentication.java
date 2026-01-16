package models;

import java.util.List;

public class Authentication {
    public boolean isUserAlreadyRegistered(String username, List<String> existingUsers) {
        return existingUsers.contains(username);
    }

    public boolean isPasswordCorrect(String realPassword, String insertedPassword) {
        return realPassword.equals(insertedPassword);
    }

    public boolean isPasswordValidate(String password, String username) {
        if (password.contains(username) || password.length() < 6) {
            return false;
        }
        return true;
    }

}
