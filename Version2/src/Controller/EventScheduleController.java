package Version2.src.Controller;

import Version2.src.Model.CalendarPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class EventScheduleController {

    private final JTextField descriptionField;
    private final CalendarPanel calendarPanel;

    public EventScheduleController() {
        descriptionField = new JTextField(20);
        calendarPanel = new CalendarPanel();
    }

    public JPanel getEventSchedulePanel() {
        JPanel eventPanel = new JPanel(new BorderLayout());
        eventPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)), "Lịch sự kiện",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLUE));
        eventPanel.setBackground(new Color(245, 245, 245));

        // Thêm bảng lịch vào panel
        eventPanel.add(calendarPanel, BorderLayout.CENTER);

        // Thêm phần hiển thị tên các ngày trong tuần
        JPanel dayNamesPanel = new JPanel(new GridLayout(1, 7)); // GridLayout cho tên các ngày
        String[] dayNames = {"Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"};

        for (String dayName : dayNames) {
            JLabel label = new JLabel(dayName, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setBackground(new Color(220, 220, 220));
            label.setOpaque(true);
            label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            dayNamesPanel.add(label);
        }

        eventPanel.add(dayNamesPanel, BorderLayout.NORTH);

        return eventPanel;
    }
}
