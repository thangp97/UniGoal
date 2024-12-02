//package Version2.src.Model;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableCellRenderer;
//import java.awt.*;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Map;
//
//public class CalendarPanel extends JPanel {
//
//    private final JTable calendarTable;
//    private LocalDate currentDate;
//    private final Map<LocalDate, String> notes;  // Lưu ghi chú theo ngày
//
//    public CalendarPanel() {
//        this.currentDate = LocalDate.now();
//        this.notes = new HashMap<>();
//        this.setLayout(new BorderLayout());
//
//        // Tạo bảng lịch
//        calendarTable = new JTable(6, 7);
//        calendarTable.setFont(new Font("Arial", Font.PLAIN, 14));
//        calendarTable.setRowHeight(60);
//
//        // Chắc chắn rằng các ô không thể chỉnh sửa
//        calendarTable.setCellSelectionEnabled(true);
//
//        // Tạo bảng lịch cho tháng hiện tại
//        updateCalendar();
//
//        // Cài đặt render cho các ô ngày
//        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) calendarTable.getDefaultRenderer(Object.class);
//        renderer.setHorizontalAlignment(SwingConstants.CENTER);
//        renderer.setVerticalAlignment(SwingConstants.CENTER);
//
//        // Chỉnh sửa màu sắc cho ngày hiện tại và ngày đã chọn
//        calendarTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                String day = value == null ? "" : value.toString();
//
//                if (!day.isEmpty()) {
//                    // Kiểm tra xem ô có chứa ghi chú hay không
//                    String cellValue = value.toString();
//                    boolean hasNote = cellValue.contains(" - ");  // Kiểm tra xem có dấu phân cách ghi chú hay không
//
//                    // Nếu có ghi chú, không thay đổi màu nền
//                    if (!hasNote) {
//                        LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), Integer.parseInt(day));
//                        if (date.equals(LocalDate.now())) {
//                            comp.setBackground(Color.YELLOW);  // Ngày hiện tại
//                        } else {
//                            comp.setBackground(Color.WHITE);
//                        }
//                    } else {
//                        comp.setBackground(Color.WHITE);  // Nếu có ghi chú, giữ màu nền mặc định
//                    }
//                }
//                return comp;
//            }
//        });
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
//        this.add(scrollPane, BorderLayout.CENTER);
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
//    }
//
//    private void changeMonth(int increment) {
//        currentDate = currentDate.plusMonths(increment);
//        updateCalendar();
//    }
//
//    private void changeYear(int increment) {
//        currentDate = currentDate.plusYears(increment);
//        updateCalendar();
//    }
//
//    private void updateCalendar() {
//        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
//        int daysInMonth = firstDayOfMonth.lengthOfMonth();
//        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();  // Chỉ số ngày đầu tiên trong tuần (1 = Monday, 7 = Sunday)
//
//        // Cập nhật các giá trị ngày tháng vào bảng
//        for (int row = 0; row < 6; row++) {
//            for (int col = 0; col < 7; col++) {
//                int dayIndex = row * 7 + col - startDayOfWeek + 1;
//                if (dayIndex > 0 && dayIndex <= daysInMonth) {
//                    LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), dayIndex);
//                    String displayText = String.valueOf(dayIndex);
//
//                    // Hiển thị ghi chú nếu có
//                    if (notes.containsKey(date)) {
//                        displayText += " - " + notes.get(date);  // Hiển thị ghi chú bên cạnh ngày
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
//        public void addNoteToSelectedDate(String note) {
//        int row = calendarTable.getSelectedRow();
//        int col = calendarTable.getSelectedColumn();
//
//        if (row != -1 && col != -1 && calendarTable.getValueAt(row, col) != null) {
//            String selectedDay = calendarTable.getValueAt(row, col).toString();
//            // Lấy ngày cụ thể từ bảng
//            LocalDate selectedDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), Integer.parseInt(selectedDay));
//
//            // Lưu ghi chú vào Map
//            notes.put(selectedDate, note);
//
//            // Cập nhật lại bảng lịch để hiển thị ghi chú
//            updateCalendar();
//        } else {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ngày hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}
//


package Version2.src.Model;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;

public class CalendarPanel extends JPanel {
    private JTable calendarTable;
    private Calendar calendar;

    public CalendarPanel() {
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

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) calendarTable.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setVerticalAlignment(SwingConstants.CENTER);

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

    private void handleNoteForDate(LocalDate selectedDate) {
        Note existingNote = calendar.getNoteForDate(selectedDate);

        if (existingNote != null) {
            // Nếu đã có ghi chú, cho phép chỉnh sửa ghi chú hiện tại
            String updatedNote = JOptionPane.showInputDialog(null, "Chỉnh sửa ghi chú:", existingNote.getContent());
            if (updatedNote != null && !updatedNote.isEmpty()) {
                // Cập nhật ghi chú mới vào đối tượng Note
                calendar.updateNote(selectedDate, updatedNote);
                updateCalendar(); // Cập nhật lại bảng lịch
            }
        } else {
            // Nếu chưa có ghi chú, thêm ghi chú mới
            String newNote = JOptionPane.showInputDialog(null, "Thêm ghi chú:");
            if (newNote != null && !newNote.isEmpty()) {
                // Thêm ghi chú vào đối tượng Note
                calendar.addNote(selectedDate, newNote);
                updateCalendar(); // Cập nhật lại bảng lịch
            }
        }
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
}

