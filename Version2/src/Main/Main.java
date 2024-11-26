package Version2.src.Main;

import Version2.src.View.RecommendDGNLView;
import Version2.src.Controller.UniversitySearch;
import Version2.src.View.GraduateCaculateTab;
import Version2.src.View.RecommendDGTDView;
import Version2.src.View.UniversityCaculateTab;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("UniGoal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        ImageIcon icon = new ImageIcon("D:/UniGOAL (1).png");
        frame.setIconImage(icon.getImage());

        JTabbedPane tabbedPane = new JTabbedPane();

        GraduateCaculateTab tab1 = new GraduateCaculateTab();
        UniversityCaculateTab tab2 = new UniversityCaculateTab();
        UniversitySearch tab3 = new UniversitySearch();

        tabbedPane.add("Tính điểm tốt nghiệp THPT", tab1.getPanel());
        tabbedPane.add("Tính điểm xét tuyển đại học", tab2.getPanel());
        tabbedPane.add("Tìm kiếm trường đại học", tab3.getPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
