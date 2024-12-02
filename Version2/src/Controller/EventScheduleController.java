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

        // Panel điều khiển thêm sự kiện
        JPanel controlPanel = createControlPanel();
        eventPanel.add(controlPanel, BorderLayout.SOUTH);

        return eventPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Các trường nhập liệu và nút thêm ghi chú
        JButton addEventButton = new JButton("Thêm ghi chú");
        addEventButton.addActionListener(e -> {
            String note = descriptionField.getText();
            if (!note.isEmpty()) {
                calendarPanel.addNoteToSelectedDate(note);
                descriptionField.setText("");  // Reset trường nhập liệu
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập ghi chú trước!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        controlPanel.add(new JLabel("Ghi chú:"));
        controlPanel.add(descriptionField);
        controlPanel.add(addEventButton);

        return controlPanel;
    }
}


