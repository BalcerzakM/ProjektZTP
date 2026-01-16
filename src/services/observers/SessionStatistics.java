package services.observers;

import events.SessionEventBus;
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
    private final Set<Word> learnedWords = new HashSet<>();

    private SessionEventBus eventBus;


    @Override
    public void onAnswer(Word w, boolean correct) {
        if (w == null) {
            System.out.print(flashCardCount);
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

        if (streak > 4) {
            eventBus.streakFeedback(streak);
        }

    }

    public void setEventBus(SessionEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public int getCorrectCount() { return correctCount; }

    public int getIncorrectCount() { return incorrectCount; }

    public int getCorrectPercent() { return correctPercent; }

    public int getMaxSessionStreak() { return maxSessionStreak; }

    public Set<Word> getLearnedWords() { return learnedWords; }

    public int getFlashCardCount() { return flashCardCount; }

    public boolean isFlashCardSession() {
        return flashCardCount > 0;
    }

    public boolean isPerfect() {
        return correctPercent == 100;
    }

    public boolean hasAnyData() {
        return getCorrectCount() != 0 || getIncorrectCount() != 0 || getFlashCardCount() != 0;
    }

    public void resetStatistics() {
        correctCount = 0;
        incorrectCount = 0;
        streak = 0;
        maxSessionStreak = 0;
        correctPercent = 0;
        flashCardCount = 0;
        learnedWords.clear();
    }
}
