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


        return eventPanel;
    }
}


