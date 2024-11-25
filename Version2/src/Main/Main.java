package Version2.src.Main;

import Version2.src.View.*;
import Version2.src.Controller.UniversitySearch;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("UniGoal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        ImageIcon icon = new ImageIcon("D:/UniGOAL (1).png");
        frame.setIconImage(icon.getImage());

        JTabbedPane tabbedPane = new JTabbedPane();

        GraduateCaculateView tab1 = new GraduateCaculateView();
        UniversityCaculateTab tab2 = new UniversityCaculateTab();
        UniversitySearchView tab3 = new UniversitySearchView();
        RecommendDGNLView tab4 = new RecommendDGNLView();
        RecommendDGTDView tab5 = new RecommendDGTDView();

        tabbedPane.add("Tính điểm tốt nghiệp THPT", tab1.getPanel());
        tabbedPane.add("Tính điểm xét tuyển đại học", tab2.getPanel());
        tabbedPane.add("Tìm kiếm trường đại học", tab3.getPanel());
        tabbedPane.add("Đề xuất dựa trên kết quả ĐGNL", tab4.getPanel());
        tabbedPane.add("Đề xuất dựa trên kết quả ĐGTD", tab5.getPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
