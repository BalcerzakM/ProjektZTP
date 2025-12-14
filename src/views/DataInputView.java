package views;

import java.util.List;
import java.util.Scanner;


public class DataInputView implements View {
    private List<String> fileNameList;

    public DataInputView(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    @Override
    public void show() {
        View topBar = new TopBar();
        topBar.show();
        System.out.println("Dostępne zestawy słówek:");
        for (int i = 0; i < fileNameList.size(); i++) {
            System.out.println(i + ". " + fileNameList.get(i));
        }
    }

    public String prompt(String promptMessage) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print(promptMessage);
        return scanner.nextLine();
    }

    public void showError() {
        System.out.println("Błąd: Nieprawidłowa instrukcja.");
    }
}
