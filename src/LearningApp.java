import models.Word;
import models.WordSet;

import java.io.*;
import java.util.*;

public class LearningApp {
    private WordSet wordSet;

    public LearningApp() {}

    private void readWordSetFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        String difficulty = scanner.nextLine().strip();
        this.wordSet = new WordSet(fileName, new ArrayList<>(), difficulty);
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("-");
            String source = line[0].strip();
            String target = line[1].strip();
            Word word = new Word(source, target);
            this.wordSet.addWord(word);
        }
        scanner.close();
    }
}
