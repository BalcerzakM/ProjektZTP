import java.io.*;
import java.util.*;

public class LearningApp {
    private WordSet wordSet;

    public LearningApp() {}

    private void readWordSetFromFile(String fileName) throws FileNotFoundException {
        this.wordSet = new WordSet(fileName, new ArrayList<>());
        Scanner scanner = new Scanner(new File(fileName));
        String difficulty = scanner.nextLine().strip();
        Word word = new Word("", "", difficulty, fileName);
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("-");
            word.setSource(line[0].strip());
            word.setTarget(line[1].strip());
            this.wordSet.addWord(word);
        }
        scanner.close();
    }
}
