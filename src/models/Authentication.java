package models;

import java.util.List;

/**
 * Klasa odpowiedzialna za walidację danych uwierzytelniających użytkownika.
 *
 * Zawiera logikę sprawdzającą poprawność logowania oraz rejestracji,
 * niezależnie od warstwy widoku i dostępu do danych.
 *
 * Pełni rolę Modelu w procesie autoryzacji.
 */
public class Authentication {
    /**
     * Sprawdza, czy użytkownik o podanej nazwie jest już zarejestrowany.
     *
     * @param username nazwa użytkownika do sprawdzenia
     * @param existingUsers lista istniejących użytkowników
     * @return {@code true} jeśli użytkownik istnieje, w przeciwnym razie {@code false}
     */
    public boolean isUserAlreadyRegistered(String username, List<String> existingUsers) {
        return existingUsers.contains(username);
    }

    /**
     * Sprawdza poprawność wprowadzonego hasła.
     *
     * @param realPassword hasło zapisane w systemie
     * @param insertedPassword hasło wprowadzone przez użytkownika
     * @return {@code true} jeśli hasła są zgodne
     */
    public boolean isPasswordCorrect(String realPassword, String insertedPassword) {
        return realPassword.equals(insertedPassword);
    }

    /**
     * Waliduje hasło podczas rejestracji użytkownika.
     *
     * Hasło:
     * - nie może zawierać nazwy użytkownika,
     * - musi mieć co najmniej 6 znaków.
     *
     * @param password hasło do sprawdzenia
     * @param username nazwa użytkownika
     * @return {@code true} jeśli hasło spełnia wymagania
     */
    public boolean isPasswordValidate(String password, String username) {
        if (password.contains(username) || password.length() < 6) {
            return false;
        }
        return true;
    }

}
