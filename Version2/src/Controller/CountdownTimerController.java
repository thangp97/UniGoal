package Version2.src.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class CountdownTimerController {
    private final JLabel daysLabel;
    private final JLabel hoursLabel;
    private final JLabel minutesLabel;
    private final JLabel secondsLabel;

    public CountdownTimerController() {
        daysLabel = createTimeLabel();
        hoursLabel = createTimeLabel();
        minutesLabel = createTimeLabel();
        secondsLabel = createTimeLabel();
        initializeTimer();
    }

    public JPanel getCountdownTimerPanel() {
        JPanel countdownPanel = new JPanel(new GridLayout(1, 4, 20, 0)); // 4 cột
        countdownPanel.setBackground(new Color(20, 30, 48)); // Cùng màu nền

        // Thêm các phần tử vào countdown panel
        countdownPanel.add(createCountdownPanel(daysLabel, "Ngày"));
        countdownPanel.add(createCountdownPanel(hoursLabel, "Giờ"));
        countdownPanel.add(createCountdownPanel(minutesLabel, "Phút"));
        countdownPanel.add(createCountdownPanel(secondsLabel, "Giây"));

        return countdownPanel;
    }

    private JLabel createTimeLabel() {
        JLabel label = new JLabel("00", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JPanel createCountdownPanel(JLabel timeLabel, String unitText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(44, 62, 80)); // Màu tối hơn cho khối
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel unitLabel = new JLabel(unitText, SwingConstants.CENTER);
        unitLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        unitLabel.setForeground(Color.WHITE);

        panel.add(timeLabel, BorderLayout.CENTER);
        panel.add(unitLabel, BorderLayout.SOUTH);
        return panel;
    }

    private void initializeTimer() {
        Timer timer = new Timer(1000, e -> updateCountdown());
        timer.start();
    }

    private void updateCountdown() {
        try {
            String examDateTime = "2025-06-01T08:00:00"; // Thời gian thi
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime examTime = LocalDateTime.parse(examDateTime);

            if (now.isBefore(examTime)) {
                Duration duration = Duration.between(now, examTime);

                long days = duration.toDays();
                long hours = duration.toHours() % 24;
                long minutes = duration.toMinutes() % 60;
                long seconds = duration.getSeconds() % 60;

                daysLabel.setText(String.format("%02d", days));
                hoursLabel.setText(String.format("%02d", hours));
                minutesLabel.setText(String.format("%02d", minutes));
                secondsLabel.setText(String.format("%02d", seconds));
            } else {
                daysLabel.setText("00");
                hoursLabel.setText("00");
                minutesLabel.setText("00");
                secondsLabel.setText("00");
            }
        } catch (Exception ex) {
            daysLabel.setText("ERR");
            hoursLabel.setText("ERR");
            minutesLabel.setText("ERR");
            secondsLabel.setText("ERR");
        }
    }
}
