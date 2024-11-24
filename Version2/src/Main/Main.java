package Version2.src.Main;

import Version2.src.Controller.RecommendDGNL;
import Version2.src.Controller.RecommendDGTD;
import Version2.src.Controller.UniversitySearch;
import Version2.src.GUI.GraduateCaculateTab;
import Version2.src.GUI.UniversityCaculateTab;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("UniGoal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        ImageIcon icon = new ImageIcon("D:/UniGOAL (1).png");
        frame.setIconImage(icon.getImage());

        JTabbedPane tabbedPane = new JTabbedPane();

        GraduateCaculateTab tab1 = new GraduateCaculateTab();
        UniversityCaculateTab tab2 = new UniversityCaculateTab();
        UniversitySearch tab3 = new UniversitySearch();
        RecommendDGNL tab4 = new RecommendDGNL();
        RecommendDGTD tab5 = new RecommendDGTD();

        tabbedPane.add("Tính điểm tốt nghiệp THPT", tab1.getPanel());
        tabbedPane.add("Tính điểm xét tuyển đại học", tab2.getPanel());
        tabbedPane.add("Tìm kiếm trường đại học", tab3.getPanel());
        tabbedPane.add("Đề xuất dựa trên kết quả ĐGNL", tab4.getPanel());
        tabbedPane.add("Đề xuất dựa trên kết quả ĐGTD", tab5.getPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
