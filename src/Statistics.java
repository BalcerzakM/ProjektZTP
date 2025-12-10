public class Statistics implements AnswerObserver {
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int streak = 0;
    private int correctPercent;
//    private static Statistics instance;
//
//    public static Statistics getInstance() {
//        if (instance == null) {
//            instance = new Statistics();
//        }
//        return instance;
//    }

    public void onAnswer(Word w, boolean correct) {
        if (correct) {
            correctCount++;
            streak++;
        }
        if (!correct) {
            incorrectCount++;
            streak = 0;
        }
        correctPercent = correctCount/(correctCount + incorrectCount)*100;
    }

    public StringBuilder showStatistics() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("==================================")
                .append("\tSTATYSTYKI:\n")
                .append("Poprawne odpowiedzi: " + correctCount + "\n")
                .append("Niepoprawne odpowiedzi: " + incorrectCount + "\n")
                .append("Streak: " + streak + "\n")
                .append("Skuteczność: " + correctPercent + "%")
                .append("==================================");
        return sb;
    }

    public void resetStatistics() {
        correctCount = 0;
        incorrectCount = 0;
        streak = 0;
    }

}
