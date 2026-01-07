package views;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ConnectPanel extends JPanel {

    private final JList<String> leftList;
    private final JList<String> rightList;
    private final JButton checkBtn = new JButton("Połącz");

    public ConnectPanel(List<String> left, List<String> right) {

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ===== TYTUŁ =====
        JLabel title = new JLabel("Tryb łączenia", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        // ===== LISTY =====
        leftList = createList(left);
        rightList = createList(right);

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 20, 0));

        listsPanel.add(createColumn("Lewo", leftList));
        listsPanel.add(createColumn("Prawo", rightList));

        add(listsPanel, BorderLayout.CENTER);

        // ===== PRZYCISK =====
        checkBtn.setFont(checkBtn.getFont().deriveFont(Font.BOLD));
        JPanel btnPanel = new JPanel();
        btnPanel.add(checkBtn);

        add(btnPanel, BorderLayout.SOUTH);
    }

    // ===== POMOCNICZE =====

    private JList<String> createList(List<String> data) {
        JList<String> list = new JList<>(data.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(4);
        list.setFixedCellHeight(30);
        return list;
    }

    private JPanel createColumn(String title, JList<String> list) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        return panel;
    }

    // ===== API DLA CONTROLLERA =====

    public int getLeftIndex() {
        return leftList.getSelectedIndex();
    }

    public int getRightIndex() {
        return rightList.getSelectedIndex();
    }

    public void onCheck(Runnable action) {
        checkBtn.addActionListener(e -> action.run());
    }

    public void updateLists(List<String> left, List<String> right) {
        leftList.setListData(left.toArray(new String[0]));
        rightList.setListData(right.toArray(new String[0]));
    }
}

