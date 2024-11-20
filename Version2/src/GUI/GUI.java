package Version2.src.GUI;

import javax.swing.*;

public class GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("UniGoal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        ImageIcon icon = new ImageIcon("D:/UniGOAL (1).png");
        frame.setIconImage(icon.getImage());

        JTabbedPane tabbedPane = new JTabbedPane();

        ShowInputTab1 Tab1 = new ShowInputTab1();
        ShowInputTab2 Tab2 = new ShowInputTab2();

        tabbedPane.add("Tính điểm tốt nghiệp THPT", Tab1.getPanel());
        tabbedPane.add("Tính điểm xét tuyển đại học", Tab2.getPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}