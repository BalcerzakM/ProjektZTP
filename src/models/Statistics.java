package models;

public class Statistics {
    private int completedLessons = 0;

    public int getCompletedLessonsAmount() {
        return this.completedLessons;
    }

    public void setCompletedLessonsAmount(int amount) {
        this.completedLessons = amount;
    }

    public void incrementCompletedLessonsAmount() {
        this.completedLessons++;
    }

}
