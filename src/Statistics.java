public class Statistics implements AnswerObserver {
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int streak = 0;
    private int correctPercent = 0;
    private int correctOverall = 0;
    private int incorrectOverall = 0;
    private int streakOverall = 0;
//    private static Statistics instance;
//
//    public static Statistics getInstance() {
//        if (instance == null) {
//            instance = new Statistics();
//        }
//        return instance;
//    }

    @Override
    public void onAnswer(Word w, boolean correct) {
        if (correct) {
            correctCount++;
            streak++;
        }
        if (!correct) {
            incorrectCount++;
            streak = 0;
        }
        if (correctCount + incorrectCount > 0) {
            correctPercent = (correctCount*100)/(correctCount + incorrectCount);
        }
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public StringBuilder showStatistics() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\n===========STATYSTYKI=============\n")
                .append("Poprawne odpowiedzi: " + correctCount + "\n")
                .append("Niepoprawne odpowiedzi: " + incorrectCount + "\n")
                .append("Streak: " + streak + "\n")
                .append("Skuteczność: " + correctPercent + "%\n")
                .append("==================================\n");
        return sb;
    }

    public void resetStatistics() {
        correctCount = 0;
        incorrectCount = 0;
        streak = 0;
        correctPercent = 0;
    }

    public void toOverallStats() {
        correctOverall += correctCount;
        incorrectOverall += incorrectCount;
    }

    public void savetoFile(String filename){
        System.out.println("Zapis statystyk do pliku " + filename);
    }

    public void readFromFile(String filename){
        System.out.println("Wczytywanie statystyk z pliku " + filename);
    }
}
