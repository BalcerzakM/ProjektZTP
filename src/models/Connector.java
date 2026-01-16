package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
        LanguageCERFLevel cerfLevel;
        try {
            cerfLevel = LanguageCERFLevel.valueOf(scanner.nextLine().strip());
        } catch (IllegalArgumentException e) {
            cerfLevel = LanguageCERFLevel.A1;
        }
        WordSet wordSet = new WordSet(fileName, new ArrayList<>(), cerfLevel);
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

    private void removeTxtFileExtensionFromList(List<String> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String singleString = list.get(i);
            list.set(i, singleString.substring(0, singleString.length() - 4));
        }
    }

    public List<String> getAviableDatabaseNames() throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get("resources/wordSets/"))) {
            List<String> databaseNames = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.endsWith(".txt"))
                    .collect(Collectors.toList());
            removeTxtFileExtensionFromList(databaseNames);
            return databaseNames;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<String> getUsersList() throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get("resources/users/"))) {
             List<String> usernames = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.endsWith(".txt"))
                    .collect(Collectors.toList());
             removeTxtFileExtensionFromList(usernames);
             return usernames;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveNewUserToFile(String username, String password, LanguageCERFLevel level) throws IOException {
        PrintWriter pw = new PrintWriter("resources/users/" + username + ".txt");
        pw.println("----USER----");
        pw.println("username: " + username);
        pw.println("password: " + password);
        pw.println("languageCEFRLevel: " + level);
        pw.println("----STATS----");
        pw.println("completedLessons: " + 0);
        pw.close();
    }

    public String readUserPasswordFromFile(String username) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources/users/" + username));
        String userPassword = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("password:")) {
                userPassword = line.strip().replace("password: ", "");
            }
        }
        scanner.close();
        if (userPassword == null) {
            throw new RuntimeException("Nie znaleziono hasła w pliku użytkownika!");
        }
        return userPassword;
    }

    public User readUserFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources/users/" + fileName));
        scanner.nextLine();
        String username = scanner.nextLine().strip().replace("username: ", "");
        String password = scanner.nextLine().strip().replace("password: ", "");
        String languageCEFRLevelString = scanner.nextLine().strip().replace("languageCEFRLevel: ", "");
        LanguageCERFLevel languageCEFRLevel;
        try {
            languageCEFRLevel = LanguageCERFLevel.valueOf(languageCEFRLevelString);
        } catch (IllegalArgumentException e) {
            languageCEFRLevel = LanguageCERFLevel.A1;
        }
        return new User(username, password, languageCEFRLevel);
    }

    public Statistics readStatisticsFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources/users/" + fileName));
        Statistics statistics = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("----STATS----")) {
                break;
            }
        }
        //statistics.setCompletedLessonsAmount(Integer.parseInt(scanner.nextLine().strip().replace("completedLessons: ", "")));
        scanner.close();
        return statistics;
    }
}
