package Version2.src.Controller;

import Version2.src.Utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class    UniversityCheckUI {
    private Connection connection;
    private JFrame frame;
    private JPanel panelMain;
    private JLabel chonHinhThuc;
    private JRadioButton luaChon1;
    private JRadioButton luaChon2;
    private ButtonGroup buttonGroup;
    private JLabel chonNganh;
    private JComboBox<String> comboBoxChonNganh;
    private JLabel nhapDiem;
    private JTextField textNhapDiem;
    private JButton goiYTruongDaiHoc;
    private JTable bangGoiY;
    private JLabel textDSDaiHoc;

    public UniversityCheckUI() {
        // Khởi tạo các thành phần
        chonHinhThuc = new JLabel("Chọn hình thức:");
        luaChon1 = new JRadioButton("Đánh Giá Năng Lực");
        luaChon2 = new JRadioButton("Đánh Giá Tư Duy");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(luaChon1);
        buttonGroup.add(luaChon2);

        comboBoxChonNganh = new JComboBox<>();
        chonNganh = new JLabel("Chọn ngành:");
        nhapDiem = new JLabel("Nhập điểm:");
        textNhapDiem = new JTextField(10);
        goiYTruongDaiHoc = new JButton("Gợi ý trường đại học");
        textDSDaiHoc = new JLabel("Danh sách gợi ý:");

        // Bảng dữ liệu
        bangGoiY = new JTable(new DefaultTableModel(new String[]{"Mã Trường", "Tên Trường", "Tên Ngành", "Điểm Đánh Giá Năng Lực"}, 0));

        // Khởi tạo panel
        panelMain = new JPanel(new BorderLayout());

        // Tạo layout cho giao diện
        JPanel topPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        topPanel.add(chonHinhThuc);
        topPanel.add(luaChon1);
        topPanel.add(new JLabel()); // Ô trống
        topPanel.add(luaChon2);
        topPanel.add(chonNganh);
        topPanel.add(comboBoxChonNganh);
        topPanel.add(nhapDiem);
        topPanel.add(textNhapDiem);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(textDSDaiHoc, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(bangGoiY), BorderLayout.CENTER);

        panelMain.add(topPanel, BorderLayout.NORTH);
        panelMain.add(centerPanel, BorderLayout.CENTER);
        panelMain.add(goiYTruongDaiHoc, BorderLayout.SOUTH);

        // Sự kiện nút gợi ý
        goiYTruongDaiHoc.addActionListener(e -> {
            try {
                int diem = Integer.parseInt(textNhapDiem.getText());
                String selectedNganh = (String) comboBoxChonNganh.getSelectedItem();
                DefaultTableModel model = (DefaultTableModel) bangGoiY.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ

                // Kiểm tra lựa chọn và gọi phương thức để lấy dữ liệu phù hợp
                if (luaChon1.isSelected()) {
                    getUniversitySuggestionsByDGNLDiem(diem, selectedNganh);
                } else if (luaChon2.isSelected()) {
                    // Thực hiện gợi ý cho "Đánh Giá Tư Duy" ở đây (nếu có)
                    // Tạm thời bỏ qua trong ví dụ này
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Vui lòng nhập điểm hợp lệ!");
            }
        });

        luaChon1.addActionListener(e -> handleHinhThucSelection());
        luaChon2.addActionListener(e -> handleHinhThucSelection());
    }

    private void handleHinhThucSelection() {
        // Khi lựa chọn hình thức, không thay đổi danh sách ngành
        String selectedNganh = (String) comboBoxChonNganh.getSelectedItem();  // Lưu lại ngành đã chọn

        // Cập nhật hành động tùy theo hình thức đã chọn
        if (luaChon1.isSelected()) {
            // Nếu chọn Đánh Giá Năng Lực thì gọi phương thức gợi ý trường đại học phù hợp
            System.out.println("Chọn Đánh Giá Năng Lực");
        } else if (luaChon2.isSelected()) {
            // Nếu chọn Đánh Giá Tư Duy thì gọi phương thức khác (nếu có)
            System.out.println("Chọn Đánh Giá Tư Duy");
        }

        // Giữ nguyên lựa chọn ngành
        if (selectedNganh != null && comboBoxChonNganh.getItemCount() > 0) {
            for (int i = 0; i < comboBoxChonNganh.getItemCount(); i++) {
                if (comboBoxChonNganh.getItemAt(i).equals(selectedNganh)) {
                    comboBoxChonNganh.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void getUniversitySuggestionsByDGNLDiem(int diem, String selectedNganh) {
        try {
            // Cập nhật câu lệnh SQL chỉ lấy dữ liệu từ bảng dgnl
            String sql = "SELECT t.maTruong, t.tenTruong, c.tenNganh, d.diemChuan " +
                    "FROM truongdaihoc t " +
                    "JOIN dgnl d ON t.maTruong = d.maTruong " +
                    "JOIN chuyennganh c ON d.maNganh = c.maNganh " +
                    "WHERE c.tenNganh = ? AND d.diemChuan <= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedNganh);
            preparedStatement.setInt(2, diem);

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) bangGoiY.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            Set<String> uniqueSchools = new HashSet<>();  // Set để lưu các mã trường đã hiển thị

            boolean hasResults = false; // Biến đánh dấu nếu có kết quả
            while (resultSet.next()) {
                String maTruong = resultSet.getString("maTruong");
                String tenTruong = resultSet.getString("tenTruong");
                String tenNganh = resultSet.getString("tenNganh");
                int diemChuan = resultSet.getInt("diemChuan");

                // Kiểm tra nếu điểm của người dùng đủ và trường chưa được hiển thị
                if (diem >= diemChuan && !uniqueSchools.contains(maTruong)) {
                    model.addRow(new Object[]{maTruong, tenTruong, tenNganh, diemChuan});
                    uniqueSchools.add(maTruong);  // Đánh dấu trường này đã được thêm vào bảng
                    hasResults = true;
                }
            }

            // Nếu không có kết quả, hiển thị thông báo
            if (!hasResults) {
                JOptionPane.showMessageDialog(frame, "Không có trường phù hợp với điểm và ngành đã chọn.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading data: " + e.getMessage());
        }
    }


    public void init() {
        try {
            connection = DatabaseConnection.getConnection();
            frame = new JFrame("University Suggestion");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setContentPane(panelMain);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            loadNganhData();  // Đọc ngành từ cơ sở dữ liệu vào comboBox
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }

    private void loadNganhData() {
        try {
            String sql = "SELECT DISTINCT c.tenNganh " +
                    "FROM chuyennganh c " +
                    "JOIN dgnl d ON c.maNganh = d.maNganh";  // Lấy ngành chỉ từ bảng dgnl
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Xóa các item cũ trong comboBox trước khi thêm mới
            comboBoxChonNganh.removeAllItems();

            while (resultSet.next()) {
                String tenNganh = resultSet.getString("tenNganh");
                comboBoxChonNganh.addItem(tenNganh);  // Thêm ngành vào comboBox
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading ngành data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UniversityCheckUI().init());
    }
}
