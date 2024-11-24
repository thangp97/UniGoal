package Version2.src.GUI;

import Version2.src.Controller.UniversitySearch;

import javax.swing.*;

public class GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("UniGoal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        ImageIcon icon = new ImageIcon("D:/UniGOAL (1).png");
        frame.setIconImage(icon.getImage());

        JTabbedPane tabbedPane = new JTabbedPane();

        ShowInputTab1 tab1 = new ShowInputTab1();
        ShowInputTab2 tab2 = new ShowInputTab2();
        UniversitySearch tab3 = new UniversitySearch();

        tabbedPane.add("Tính điểm tốt nghiệp THPT", tab1.getPanel());
        tabbedPane.add("Tính điểm xét tuyển đại học", tab2.getPanel());
        tabbedPane.add("Tìm kiếm trường đại học", tab3.getPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
