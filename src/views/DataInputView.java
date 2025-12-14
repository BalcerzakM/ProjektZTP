package views;

import java.util.List;
import java.util.Scanner;


public class DataInputView {
    private List<String> fileNameList;

    public DataInputView(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    public void show() {
        ComponentsDrawer drawer = new ComponentsDrawer();
        drawer.showTopBar();
        System.out.println("Dostępne zestawy słówek:");
        for (int i = 0; i < fileNameList.size(); i++) {
            System.out.println(i + ". " + fileNameList.get(i));
        }
    }

    public String showChooseFileNamePrompt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Wybierz zestaw słówek, którego chcesz się nauczyć: ");
        return scanner.nextLine();
    }

    public void showError() {
        System.out.println("Błąd: Nie można wczytać pliku.");
    }
}
