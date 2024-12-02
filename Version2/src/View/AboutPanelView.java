package Version2.src.View;

import Version2.src.Controller.CountdownTimerController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AboutPanelView {
    public JPanel getAboutPanel() {
        JPanel aboutPanel = new JPanel(new BorderLayout());
        aboutPanel.setBackground(new Color(245, 245, 245));

        // Thông tin giới thiệu
        JTextArea aboutText = new JTextArea("Chào mừng bạn đến với UNIGOAL!\n\n" +
                "UniGOAL hỗ trợ học sinh THPT trong việc:\n" +
                "- Tính điểm tốt nghiệp THPT.\n" +
                "- Tìm kiếm thông tin tuyển sinh đại học.\n" +
                "- Theo dõi các sự kiện liên quan đến kỳ thi.\n" +
                "- Quản lý lịch sự kiện cá nhân.\n");
        aboutText.setEditable(false);
        aboutText.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutText.setBackground(Color.WHITE);
        aboutText.setBorder(new EmptyBorder(10, 10, 10, 10));

        aboutPanel.add(new JScrollPane(aboutText), BorderLayout.NORTH);

        // Đếm ngược (Countdown Timer)
        CountdownTimerController countdownController = new CountdownTimerController();
        aboutPanel.add(countdownController.getCountdownTimerPanel(), BorderLayout.CENTER);

        return aboutPanel;
    }
}