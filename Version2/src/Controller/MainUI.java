package Version2.src.Controller;

import javax.swing.*;
import java.sql.*;

public class MainUI {

    public static void main(String[] args) throws SQLException {
        // Tạo JFrame chính
        JFrame frame = new JFrame("Gợi ý trường đại học");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Tạo JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Thêm các tab
        tabbedPane.addTab("Đánh giá Năng lực", new RecommendDGNL());
        tabbedPane.addTab("Đánh giá Tư duy", new RecommendDGTD());

        // Thêm tabbedPane vào cửa sổ
        frame.add(tabbedPane);

        // Hiển thị cửa sổ
        frame.setVisible(true);
    }
}
