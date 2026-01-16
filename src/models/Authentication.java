package models;

import java.util.List;

public class Authentication {
    public boolean isUserAlreadyRegistered(String username, List<String> existingUsers) {
        return existingUsers.contains(username);
    }

    public boolean isPasswordCorrect(String realPassword, String insertedPassword) {
        return realPassword.equals(insertedPassword);
    }

    public void registerNewUser(String username, String password, String langLevel) {

    }

}
