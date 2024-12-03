//package Version2.src.Model;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableCellRenderer;
//import java.awt.*;
//import java.time.LocalDate;
//
//public class CalendarPanel extends JPanel {
//    private JTable calendarTable;
//    private Calendar calendar;
//
//    public CalendarPanel() {
//        this.calendar = new Calendar(LocalDate.now());
//        this.setLayout(new BorderLayout());
//        initTable();
//        updateCalendar();
//    }
//
//    private void initTable() {
//        calendarTable = new JTable(6, 7);
//        calendarTable.setFont(new Font("Arial", Font.PLAIN, 14));
//        calendarTable.setRowHeight(60);
//        calendarTable.setCellSelectionEnabled(true);
//
//        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) calendarTable.getDefaultRenderer(Object.class);
//        renderer.setHorizontalAlignment(SwingConstants.CENTER);
//        renderer.setVerticalAlignment(SwingConstants.CENTER);
//
//        calendarTable.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent e) {
//                int row = calendarTable.getSelectedRow();
//                int col = calendarTable.getSelectedColumn();
//
//                // Lấy giá trị của ô ngày
//                String currentValue = (String) calendarTable.getValueAt(row, col);
//
//                if (currentValue != null && currentValue.contains(" - ")) {
//                    // Nếu ô ngày đã có ghi chú, cho phép chỉnh sửa
//                    String existingNote = currentValue.split(" - ")[1];  // Lấy phần ghi chú hiện tại
//                    String newNote = JOptionPane.showInputDialog(null, "Chỉnh sửa ghi chú:", existingNote);
//
//                    if (newNote != null && !newNote.isEmpty()) {
//                        // Cập nhật ghi chú vào ô ngày
//                        String updatedValue = currentValue.split(" - ")[0] + " - " + newNote;
//                        calendarTable.setValueAt(updatedValue, row, col);
//                    }
//                } else {
//                    // Nếu ô ngày chưa có ghi chú, thêm ghi chú mới
//                    String newNote = JOptionPane.showInputDialog(null, "Thêm ghi chú:");
//
//                    if (newNote != null && !newNote.isEmpty()) {
//                        // Thêm ghi chú vào ô ngày
//                        String updatedValue = currentValue + " - " + newNote;
//                        calendarTable.setValueAt(updatedValue, row, col);
//                    }
//                }
//            }
//        });
//
//        JScrollPane scrollPane = new JScrollPane(calendarTable);
//
//        // Nút điều hướng tháng
//        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        JButton prevMonthButton = new JButton("<");
//        prevMonthButton.addActionListener(e -> changeMonth(-1));
//        JButton nextMonthButton = new JButton(">");
//        nextMonthButton.addActionListener(e -> changeMonth(1));
//
//        JButton prevYearButton = new JButton("<<");
//        prevYearButton.addActionListener(e -> changeYear(-1));
//        JButton nextYearButton = new JButton(">>");
//        nextYearButton.addActionListener(e -> changeYear(1));
//
//        navigationPanel.add(prevYearButton);
//        navigationPanel.add(prevMonthButton);
//        navigationPanel.add(nextMonthButton);
//        navigationPanel.add(nextYearButton);
//
//        this.add(navigationPanel, BorderLayout.SOUTH);
//        this.add(scrollPane, BorderLayout.CENTER);
//    }
//
//    public void updateCalendar() {
//        LocalDate firstDayOfMonth = LocalDate.of(calendar.getCurrentDate().getYear(), calendar.getCurrentDate().getMonth(), 1);
//        int daysInMonth = calendar.getDaysInMonth();
//        int startDayOfWeek = calendar.getDayOfWeekForFirstDayOfMonth();
//
//        for (int row = 0; row < 6; row++) {
//            for (int col = 0; col < 7; col++) {
//                int dayIndex = row * 7 + col - startDayOfWeek + 1;
//                if (dayIndex > 0 && dayIndex <= daysInMonth) {
//                    LocalDate date = LocalDate.of(calendar.getCurrentDate().getYear(), calendar.getCurrentDate().getMonth(), dayIndex);
//                    String displayText = String.valueOf(dayIndex);
//
//                    Note note = calendar.getNoteForDate(date);
//                    if (note != null) {
//                        displayText += note.toString();
//                    }
//
//                    calendarTable.setValueAt(displayText, row, col);
//                } else {
//                    calendarTable.setValueAt("", row, col);
//                }
//            }
//        }
//    }
//
//    public void changeMonth(int increment) {
//        calendar.changeMonth(increment);
//        updateCalendar();
//    }
//
//    public void changeYear(int increment) {
//        calendar.changeYear(increment);
//        updateCalendar();
//    }
//}
//

package Version2.src.View;

import Version2.src.Model.Calendar;
import Version2.src.Model.Note;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;

public class CalendarView extends JPanel {
    private JTable calendarTable;
    private Calendar calendar;

    public CalendarView() {
        this.calendar = new Calendar(LocalDate.now());
        this.setLayout(new BorderLayout());
        initTable();
        updateCalendar();
    }

    private void initTable() {
        calendarTable = new JTable(6, 7);
        calendarTable.setFont(new Font("Arial", Font.PLAIN, 14));
        calendarTable.setRowHeight(60);
        calendarTable.setCellSelectionEnabled(true);

        // Sử dụng lớp HighlightRenderer để làm nổi bật ngày hiện tại
        calendarTable.setDefaultRenderer(Object.class, new HighlightRenderer());

        calendarTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = calendarTable.getSelectedRow();
                int col = calendarTable.getSelectedColumn();

                // Lấy giá trị của ô ngày
                String currentValue = (String) calendarTable.getValueAt(row, col);

                if (currentValue != null && currentValue.contains(" - ")) {
                    // Nếu ô ngày đã có ghi chú, cho phép chỉnh sửa
                    String existingNote = currentValue.split(" - ")[1];  // Lấy phần ghi chú hiện tại
                    String newNote = JOptionPane.showInputDialog(null, "Chỉnh sửa ghi chú:", existingNote);

                    if (newNote != null && !newNote.isEmpty()) {
                        // Cập nhật ghi chú vào ô ngày
                        String updatedValue = currentValue.split(" - ")[0] + " - " + newNote;
                        calendarTable.setValueAt(updatedValue, row, col);
                    }
                } else {
                    // Nếu ô ngày chưa có ghi chú, thêm ghi chú mới
                    String newNote = JOptionPane.showInputDialog(null, "Thêm ghi chú:");

                    if (newNote != null && !newNote.isEmpty()) {
                        // Thêm ghi chú vào ô ngày
                        String updatedValue = currentValue + " - " + newNote;
                        calendarTable.setValueAt(updatedValue, row, col);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(calendarTable);

        // Nút điều hướng tháng
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton prevMonthButton = new JButton("<");
        prevMonthButton.addActionListener(e -> changeMonth(-1));
        JButton nextMonthButton = new JButton(">");
        nextMonthButton.addActionListener(e -> changeMonth(1));

        JButton prevYearButton = new JButton("<<");
        prevYearButton.addActionListener(e -> changeYear(-1));
        JButton nextYearButton = new JButton(">>");
        nextYearButton.addActionListener(e -> changeYear(1));

        navigationPanel.add(prevYearButton);
        navigationPanel.add(prevMonthButton);
        navigationPanel.add(nextMonthButton);
        navigationPanel.add(nextYearButton);

        this.add(navigationPanel, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void updateCalendar() {
        LocalDate firstDayOfMonth = LocalDate.of(calendar.getCurrentDate().getYear(), calendar.getCurrentDate().getMonth(), 1);
        int daysInMonth = calendar.getDaysInMonth();
        int startDayOfWeek = calendar.getDayOfWeekForFirstDayOfMonth();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                int dayIndex = row * 7 + col - startDayOfWeek + 1;
                if (dayIndex > 0 && dayIndex <= daysInMonth) {
                    LocalDate date = LocalDate.of(calendar.getCurrentDate().getYear(), calendar.getCurrentDate().getMonth(), dayIndex);
                    String displayText = String.valueOf(dayIndex);

                    Note note = calendar.getNoteForDate(date);
                    if (note != null) {
                        displayText += note.toString();
                    }

                    calendarTable.setValueAt(displayText, row, col);
                } else {
                    calendarTable.setValueAt("", row, col);
                }
            }
        }
    }

    public void changeMonth(int increment) {
        calendar.changeMonth(increment);
        updateCalendar();
    }

    public void changeYear(int increment) {
        calendar.changeYear(increment);
        updateCalendar();
    }

    // Lớp HighlightRenderer để làm nổi bật ngày hiện tại
    private class HighlightRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Kiểm tra nếu giá trị là ngày hiện tại
            if (value != null && !value.toString().isEmpty()) {
                LocalDate currentDate = LocalDate.now();
                String dayText = value.toString().split(" ")[0]; // Lấy số ngày
                try {
                    int day = Integer.parseInt(dayText);
                    LocalDate cellDate = LocalDate.of(calendar.getCurrentDate().getYear(), calendar.getCurrentDate().getMonth(), day);

                    if (cellDate.equals(currentDate)) {
                        cell.setBackground(Color.YELLOW); // Màu nền vàng cho ngày hiện tại
                        cell.setFont(cell.getFont().deriveFont(Font.BOLD)); // Chữ đậm
                    } else {
                        cell.setBackground(Color.WHITE); // Màu nền trắng cho ngày khác
                        cell.setFont(cell.getFont().deriveFont(Font.PLAIN));
                    }
                } catch (NumberFormatException ignored) {
                }
            } else {
                cell.setBackground(Color.WHITE);
            }

            return cell;
        }
    }
}

