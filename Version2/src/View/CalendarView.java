package Version2.src.View;

import Version2.src.Model.Calendar;
import Version2.src.Model.Note;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
        // Tạo bảng không có tiêu đề
        calendarTable = new JTable(new DefaultTableModel(6, 7));
        calendarTable.setFont(new Font("Arial", Font.PLAIN, 16));
        calendarTable.setRowHeight(60);
        calendarTable.setCellSelectionEnabled(false);

        calendarTable.setDefaultRenderer(Object.class, new HighlightRenderer());

        // Căn giữa dữ liệu trong bảng
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setVerticalAlignment(SwingConstants.CENTER);
        calendarTable.setDefaultRenderer(Object.class, renderer);

        calendarTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = calendarTable.getSelectedRow();
                int col = calendarTable.getSelectedColumn();

                if (row == 0) return; // Bỏ qua tiêu đề ngày trong tuần

                // Lấy giá trị của ô ngày
                String currentValue = (String) calendarTable.getValueAt(row, col);

                if (currentValue != null && currentValue.contains(" - ")) {
                    String existingNote = currentValue.split(" - ")[1]; // Lấy phần ghi chú hiện tại
                    String newNote = JOptionPane.showInputDialog(null, "Chỉnh sửa ghi chú:", existingNote);

                    if (newNote != null && !newNote.isEmpty()) {
                        String updatedValue = currentValue.split(" - ")[0] + " - " + newNote;
                        calendarTable.setValueAt(updatedValue, row, col);
                    }
                } else {
                    String newNote = JOptionPane.showInputDialog(null, "Thêm ghi chú:");

                    if (newNote != null && !newNote.isEmpty()) {
                        String updatedValue = currentValue + " - " + newNote;
                        calendarTable.setValueAt(updatedValue, row, col);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(calendarTable);

        // Nút điều hướng tháng và năm
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prevMonthButton = new JButton("<");
        prevMonthButton.addActionListener(e -> changeMonth(-1));
        JButton nextMonthButton = new JButton(">");
        nextMonthButton.addActionListener(e -> changeMonth(1));

        JButton prevYearButton = new JButton("<<");
        prevYearButton.addActionListener(e -> changeYear(-1));
        JButton nextYearButton = new JButton(">>");
        nextYearButton.addActionListener(e -> changeYear(1));

        JLabel monthYearLabel = new JLabel("", JLabel.CENTER);
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 18));
        monthYearLabel.setForeground(Color.BLUE);

        navigationPanel.add(prevYearButton);
        navigationPanel.add(prevMonthButton);
        navigationPanel.add(monthYearLabel);
        navigationPanel.add(nextMonthButton);
        navigationPanel.add(nextYearButton);

        this.add(navigationPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void updateCalendar() {
        LocalDate firstDayOfMonth = LocalDate.of(calendar.getCurrentDate().getYear(), calendar.getCurrentDate().getMonth(), 1);
        int daysInMonth = calendar.getDaysInMonth();
        int startDayOfWeek = calendar.getDayOfWeekForFirstDayOfMonth();

        JLabel monthYearLabel = (JLabel) ((JPanel) this.getComponent(0)).getComponent(2);
        monthYearLabel.setText(calendar.getCurrentDate().getMonth() + " " + calendar.getCurrentDate().getYear());

        // Điền ngày vào bảng
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
            if (row > 0 && value != null && !value.toString().isEmpty()) {
                LocalDate currentDate = LocalDate.now();
                String[] parts = value.toString().split("/");
                try {
                    int day = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int year = Integer.parseInt(parts[2]);

                    LocalDate cellDate = LocalDate.of(year, month, day);

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
