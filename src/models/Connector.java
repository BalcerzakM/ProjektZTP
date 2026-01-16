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
        PrintWriter pw = new PrintWriter(new File("resources/users/" + username + ".txt"));
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
                String[] parts = line.split(":");

                if (parts.length > 1) {
                    userPassword = parts[1].strip();
                }
                break;
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
        switch(languageCEFRLevelString) {
            case "A1":
                languageCEFRLevel = LanguageCERFLevel.A1;
                break;
            case "A2":
                languageCEFRLevel = LanguageCERFLevel.A2;
                break;
            case "B1":
                languageCEFRLevel = LanguageCERFLevel.B1;
                break;
            case "B2":
                languageCEFRLevel = LanguageCERFLevel.B2;
                break;
            case "C1":
                languageCEFRLevel = LanguageCERFLevel.C1;
                break;
            case "C2":
                languageCEFRLevel = LanguageCERFLevel.C2;
            default:
                languageCEFRLevel = LanguageCERFLevel.A1;
                break;
        }
        return new User(username, password, languageCEFRLevel);
    }

    public Statistics readStatisticsFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources/users/" + fileName));
        Statistics statistics = new Statistics();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("----STATS----")) {
                break;
            }
        }
        statistics.setCompletedLessonsAmount(Integer.parseInt(scanner.nextLine().strip().replace("completedLessons: ", "")));
        scanner.close();
        return statistics;
    }
}
