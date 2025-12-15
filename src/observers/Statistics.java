package observers;

import models.Word;

import java.util.HashSet;
import java.util.Set;

public class Statistics implements AnswerObserver {
    //statystyki na lekcje
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int streak = 0;
    private int maxSessionStreak = 0;
    private int correctPercent = 0;
    private int flashCardCount = 0;
    //statystyki ogolne
    private int correctOverall = 0;
    private int incorrectOverall = 0;
    private int longestStreak = 0;
    private Set<Word> learnedWords = new HashSet<Word>();
    private int perfectLessons = 0;

    private static Statistics instance;

    private Statistics() {}

    public static Statistics getInstance() {
        if (instance == null) {
            instance = new Statistics();
        }
        return instance;
    }

    @Override
    public void onAnswer(Word w, boolean correct) {
        if (w == null) {
            flashCardCount++;
            return;
        }
        if (correct) {
            correctCount++;
            streak++;
            if (streak > maxSessionStreak) {
                maxSessionStreak = streak;
            }
            learnedWords.add(w);
        } else {
            incorrectCount++;
            streak = 0;
        }
        if (correctCount + incorrectCount > 0) {
            correctPercent = (correctCount*100)/(correctCount + incorrectCount);
        }

        if (maxSessionStreak % 5 == 0 && maxSessionStreak > 0) {
            System.out.printf("\n-- %d pod rząd! Dobra robota! --\n", maxSessionStreak);
        }
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public StringBuilder showStatistics() {
        StringBuilder sb = new StringBuilder();
        String perfect = (correctPercent == 100) ? " IDEALNIE!" : "";
        sb.append("\n===========STATYSTYKI=============\n");
        if (flashCardCount > 0) {
            sb.append("Przerobione fiszki: " + flashCardCount + "\n");
        }
        else {
            sb.append("Poprawne odpowiedzi: " + correctCount + "\n")
                    .append("Niepoprawne odpowiedzi: " + incorrectCount + "\n")
                    .append("Streak: " + maxSessionStreak + "\n")
                    .append("Skuteczność: " + correctPercent + "% " + perfect + "\n");
        }

        return sb.append("==================================\n");
    }

    public void resetStatistics() {
        correctCount = 0;
        incorrectCount = 0;
        streak = 0;
        maxSessionStreak = 0;
        correctPercent = 0;
        flashCardCount = 0;
    }

    public void addToOverallStats() {
        correctOverall += correctCount;
        incorrectOverall += incorrectCount;
        if (correctPercent == 100) {
            perfectLessons++;
        }
        longestStreak = Math.max(maxSessionStreak, longestStreak);
        resetStatistics();
    }

    public StringBuilder showOverallStatistics() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\n========OGÓLNE STATYSTYKI=========\n")
                .append("Poprawne odpowiedzi: " + correctOverall + "\n")
                .append("Niepoprawne odpowiedzi: " + incorrectOverall + "\n")
                .append("Najlepszy streak: " + longestStreak + "\n")
                .append("Przerobione slowa: " + learnedWords.size() + "\n")
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
        resetStatistics();
    }

    public void savetoFile(String filename){
        System.out.println("Zapis statystyk do pliku " + filename);
    }

    public void readFromFile(String filename){
        System.out.println("Wczytywanie statystyk z pliku " + filename);
    }
}
