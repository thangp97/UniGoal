package Version2.src.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class CountdownTimerController {
    private final JLabel countdownLabel;

    public CountdownTimerController() {
        countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 24));
        initializeTimer();
    }

    public JPanel getCountdownTimerPanel() {
        JPanel countdownPanel = new JPanel(new BorderLayout());
        countdownPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)), "Đếm ngược đến ngày thi",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLUE));
        countdownPanel.setBackground(new Color(245, 245, 245));
        countdownPanel.add(countdownLabel, BorderLayout.CENTER);
        return countdownPanel;
    }

    private void initializeTimer() {
        Timer timer = new Timer(1000, e -> updateCountdown());
        timer.start();
    }

    private void updateCountdown() {
        try {
            String examDateTime = "2025-07-01T08:00:00"; // Thời gian thi THPT: 8:00 sáng
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime examTime = LocalDateTime.parse(examDateTime);

            if (now.isBefore(examTime)) {
                Duration duration = Duration.between(now, examTime);

                long days = duration.toDays();
                long hours = duration.toHours() % 24;
                long minutes = duration.toMinutes() % 60;
                long seconds = duration.getSeconds() % 60;

                String timeLeft = String.format("Còn %d ngày %02d:%02d:%02d đến kỳ thi!", days, hours, minutes, seconds);
                countdownLabel.setText(timeLeft);
            } else {
                countdownLabel.setText("Kỳ thi đã bắt đầu hoặc kết thúc!");
            }
        } catch (Exception ex) {
            countdownLabel.setText("Lỗi trong tính toán thời gian!");
        }
    }
}
