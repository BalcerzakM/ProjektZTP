package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Connector {
    private static Connector instance;

    public static Connector getInstance() {
        if (instance == null) {
            instance = new Connector();
        }
        return instance;
    }

    public WordSet readWordSetFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources/wordSets/" + fileName));
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

    public List<String> getAviableDatabases() throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get("resources/wordSets/"))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.endsWith(".txt"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
