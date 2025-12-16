package observers;

import models.Word;

import java.util.HashSet;
import java.util.Set;

public class SessionStatistics implements AnswerObserver {
    //statystyki na lekcje
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int streak = 0;
    private int maxSessionStreak = 0;
    private int correctPercent = 0;
    private int flashCardCount = 0;
    private Set<Word> learnedWords = new HashSet<Word>();

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

    public int getIncorrectCount() { return incorrectCount; }

    public int getCorrectPercent() { return correctPercent; }

    public int getMaxSessionStreak() { return maxSessionStreak; }

    public Set<Word> getLearnedWords() { return learnedWords; }

    public int getFlashCardCount() { return flashCardCount; }

    public StringBuilder showStatistics() {
        StringBuilder sb = new StringBuilder();
        String perfect = (correctPercent == 100) ? " IDEALNIE!" : "";
        sb.append("\n===========STATYSTYKI=============\n");
        if (flashCardCount > 0) {
            sb.append("Przejrzane fiszki: " + flashCardCount + "\n");
        }
        else {
            sb.append("Poprawne odpowiedzi: " + correctCount + "\n")
                    .append("Niepoprawne odpowiedzi: " + incorrectCount + "\n")
                    .append("Streak: " + maxSessionStreak + "\n")
                    .append("Skuteczność: " + correctPercent + "% " + perfect + "\n");
        }

        return sb.append("==================================\n");
    }

//    public void resetStatistics() {
//        correctCount = 0;
//        incorrectCount = 0;
//        streak = 0;
//        maxSessionStreak = 0;
//        correctPercent = 0;
//        flashCardCount = 0;
//    }
}
