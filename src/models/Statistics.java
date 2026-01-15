package models;

import observers.SessionStatistics;

import java.util.HashSet;
import java.util.Set;

public class Statistics {
    private int completedLessons = 0;
    private int correctOverall = 0;
    private int incorrectOverall = 0;
    private int longestStreak = 0;
    private Set<Word> learnedWords = new HashSet<Word>();
    private int perfectLessons = 0;
    private int totalFlashCards = 0;

    public void addToStatistics(SessionStatistics sessionStats) {
        completedLessons++;
        correctOverall += sessionStats.getCorrectCount();
        incorrectOverall += sessionStats.getIncorrectCount();
        if (sessionStats.getCorrectPercent() == 100) {
            perfectLessons++;
        }
        longestStreak = Math.max(sessionStats.getMaxSessionStreak(), longestStreak);
        learnedWords.addAll(sessionStats.getLearnedWords());
        totalFlashCards += sessionStats.getFlashCardCount();
        System.out.println(completedLessons);
    }

    public int getCompletedLessons() {
        return completedLessons;
    }

    public int getCorrectOverall() {
        return correctOverall;
    }

    public int getIncorrectOverall() {
        return incorrectOverall;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public int getLearnedWordsAmount() {
        return learnedWords.size();
    }

    public int getPerfectLessons() {
        return perfectLessons;
    }

    public int getTotalFlashCards() {
        return totalFlashCards;
    }
}
