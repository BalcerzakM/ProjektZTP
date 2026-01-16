package models;

import services.observers.SessionStatistics;

public class Statistics {
    private int completedLessons;
    private int correctOverall;
    private int incorrectOverall;
    private int longestStreak;
    private int learnedWords;
    private int perfectLessons;
    private int totalFlashCards;

    public Statistics(int completedLessons, int correctOverall, int incorrectOverall, int longestStreak, int learnedWords, int perfectLessons, int totalFlashCards) {
        this.completedLessons = completedLessons;
        this.correctOverall = correctOverall;
        this.incorrectOverall = incorrectOverall;
        this.longestStreak = longestStreak;
        this.learnedWords = learnedWords;
        this.perfectLessons = perfectLessons;
        this.totalFlashCards = totalFlashCards;
    }

    public void addToStatistics(SessionStatistics sessionStats) {
        completedLessons++;
        correctOverall += sessionStats.getCorrectCount();
        incorrectOverall += sessionStats.getIncorrectCount();
        if (sessionStats.getCorrectPercent() == 100) {
            perfectLessons++;
        }
        longestStreak = Math.max(sessionStats.getMaxSessionStreak(), longestStreak);
        learnedWords += (sessionStats.getLearnedWords().size());
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
        return learnedWords;
    }

    public int getPerfectLessons() {
        return perfectLessons;
    }

    public int getTotalFlashCards() {
        return totalFlashCards;
    }

    public int calculateLevelProgress() {
        return completedLessons + correctOverall/2 + longestStreak*3 + perfectLessons*2;
    }

    public int getLevelProgressPercent(LanguageCERFLevel level) {
        int levelMin = level.getMinPoints();
        int levelRange = level.getPointsRange();

        int currentLevelProgress = Math.max(0, calculateLevelProgress() - levelMin);
        int percent = (int) ((currentLevelProgress / (double) levelRange) * 100);

        return Math.min(percent, 100);
    }
}
