package views;

import java.util.Scanner;

public class LearningSessionView {
    private String wordSetName;

    public LearningSessionView(String wordSetName) {
        this.wordSetName = wordSetName;
    }

    public void showMainPage() {
        ComponentsDrawer drawer = new ComponentsDrawer();
        drawer.showTopBar();
        System.out.println("Witaj w aplikacji do nauki języków!");
        System.out.println("Wybrano zestaw słówek: " + wordSetName);
        System.out.println("Dostępne trybt nauki:");
        System.out.println("1. Fiszki");
        System.out.println("2. Łączenie wyrazów");
        System.out.println("3. Milionerzy");
        System.out.println("4. Wpisz odpowiedź");
    }


    public String showChooseModePrompt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Wybierz tryb nauki: ");
        return scanner.nextLine();
    }

    public void showError() {
        System.out.println("Błąd: Nieprawidłowa instrukcja.");
    }



}
