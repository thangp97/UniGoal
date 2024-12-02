package Version2.src.Model;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.*;
import java.util.ArrayList;

public class CalendarPanel extends JPanel {

    private final JTable calendarTable;
    private LocalDate currentDate;
    private final ArrayList<String> notes;

    public CalendarPanel() {
        this.currentDate = LocalDate.now();
        this.notes = new ArrayList<>();
        this.setLayout(new BorderLayout());

        // Tạo bảng lịch
        calendarTable = new JTable(6, 7);
        calendarTable.setFont(new Font("Arial", Font.PLAIN, 14));
        calendarTable.setRowHeight(40);

        // Chắc chắn rằng các ô không thể chỉnh sửa
        calendarTable.setCellSelectionEnabled(true);
        TableModel model = calendarTable.getModel();
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                model.setValueAt("", row, col);  // Đảm bảo không có giá trị mặc định
            }
        }

        // Tạo bảng lịch cho tháng hiện tại
        updateCalendar();

        // Cài đặt render cho các ô ngày
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) calendarTable.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setVerticalAlignment(SwingConstants.CENTER);

        // Chỉnh sửa màu sắc cho ngày hiện tại và ngày đã chọn
        calendarTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String day = value == null ? "" : value.toString();
                if (!day.isEmpty()) {
                    LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), Integer.parseInt(day));
                    if (date.equals(LocalDate.now())) {
                        comp.setBackground(Color.YELLOW);  // Ngày hiện tại
                    } else {
                        comp.setBackground(Color.WHITE);
                    }
                }
                return comp;
            }
        });

        JScrollPane scrollPane = new JScrollPane(calendarTable);
        this.add(scrollPane, BorderLayout.CENTER);

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
    }

    private void changeMonth(int increment) {
        currentDate = currentDate.plusMonths(increment);
        updateCalendar();
    }

    private void changeYear(int increment) {
        currentDate = currentDate.plusYears(increment);
        updateCalendar();
    }

    private void updateCalendar() {
        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
        int daysInMonth = firstDayOfMonth.lengthOfMonth();
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();  // Chỉ số ngày đầu tiên trong tuần (1 = Monday, 7 = Sunday)

        // Cập nhật các giá trị ngày tháng vào bảng
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                int dayIndex = row * 7 + col - startDayOfWeek + 1;
                if (dayIndex > 0 && dayIndex <= daysInMonth) {
                    calendarTable.setValueAt(String.valueOf(dayIndex), row, col);
                } else {
                    calendarTable.setValueAt("", row, col);
                }
            }
        }
    }

    public void addNoteToSelectedDate(String note) {
        int row = calendarTable.getSelectedRow();
        int col = calendarTable.getSelectedColumn();
        if (row != -1 && col != -1 && calendarTable.getValueAt(row, col) != null) {
            String selectedDay = calendarTable.getValueAt(row, col).toString();
            notes.add("Ngày " + selectedDay + ": " + note);

            // Chỉ hiển thị ghi chú trong ô đã chọn (không cho phép chỉnh sửa trực tiếp)
            calendarTable.setValueAt(note, row, col);  // Hiển thị ghi chú trực tiếp trên bảng
        }
    }
}
