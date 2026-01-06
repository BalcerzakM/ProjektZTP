package views;

import javax.swing.*;
import java.awt.*;
import java.util.List;


//testowe
//nie wiem czy tu nie nadpisuje logiki biznesowej  z ConnectMode
public class ConnectPanel extends JPanel {

    private JList<String> leftList;
    private JList<String> rightList;
    private JButton checkBtn = new JButton("Połącz");

    public ConnectPanel(List<String> left, List<String> right) {
        setSize(600, 300);
        setLayout(new BorderLayout());

        leftList = new JList<>(left.toArray(new String[0]));
        rightList = new JList<>(right.toArray(new String[0]));

        add(new JScrollPane(leftList), BorderLayout.WEST);
        add(new JScrollPane(rightList), BorderLayout.EAST);
        add(checkBtn, BorderLayout.SOUTH);
    }

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
