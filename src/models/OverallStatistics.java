package models;

import observers.SessionStatistics;

import java.util.HashSet;
import java.util.Set;

public class OverallStatistics {
    //statystyki ogolne
    private int correctOverall = 0;
    private int incorrectOverall = 0;
    private int longestStreak = 0;
    private Set<Word> learnedWords = new HashSet<Word>();
    private int perfectLessons = 0;
    private int totalFlashCards = 0;

    private static OverallStatistics instance;

    private OverallStatistics() {}

    public static OverallStatistics getInstance() {
        if (instance == null) {
            instance = new OverallStatistics();
        }
        return instance;
    }

    public void addToOverallStats(SessionStatistics sessionStats) {
        correctOverall += sessionStats.getCorrectCount();
        incorrectOverall += sessionStats.getIncorrectCount();
        if (sessionStats.getCorrectPercent() == 100) {
            perfectLessons++;
        }
        longestStreak = Math.max(sessionStats.getMaxSessionStreak(), longestStreak);
        learnedWords.addAll(sessionStats.getLearnedWords());
        totalFlashCards += sessionStats.getFlashCardCount();
    }

    public StringBuilder showOverallStatistics() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\n========OGÓLNE STATYSTYKI=========\n")
                .append("Poprawne odpowiedzi: " + correctOverall + "\n")
                .append("Niepoprawne odpowiedzi: " + incorrectOverall + "\n")
                .append("Najlepszy streak: " + longestStreak + "\n")
                .append("Przerobione slowa: " + learnedWords.size() + "\n")
                .append("Przejrzane fiszki: " + totalFlashCards + "\n")
                .append("Idealne lekcje: " + perfectLessons + "\n")
                .append("==================================\n");
        return sb;
    }

    public void resetOverallStatistics() {
        correctOverall = 0;
        incorrectOverall = 0;
        longestStreak = 0;
        learnedWords.clear();
        perfectLessons = 0;
    }

    public void savetoFile(String filename){
        System.out.println("Zapis statystyk do pliku " + filename);
    }

    public void readFromFile(String filename){
        System.out.println("Wczytywanie statystyk z pliku " + filename);
    }
}
