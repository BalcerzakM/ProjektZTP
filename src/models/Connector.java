package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Connector {
    private static Connector instance;

    public static Connector getInstance() {
        if (instance == null) {
            instance = new Connector();
        }
        return instance;
    }

    public WordSet readWordSetFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        String difficulty = scanner.nextLine().strip();
        WordSet wordSet = new WordSet(fileName, new ArrayList<>(), difficulty);
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("-");
            String source = line[0].strip();
            String target = line[1].strip();
            Word word = new Word(source, target);
            wordSet.addWord(word);
        }
        scanner.close();
        return wordSet;
    }
}
