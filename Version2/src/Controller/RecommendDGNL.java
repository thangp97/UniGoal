package Version2.src.Controller;

import Version2.src.Utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.text.DecimalFormat;

public class RecommendDGNL extends JPanel {
    private final Connection connection;
    private final JTextField textNhapDiem;
    private final JComboBox<String> comboBoxChonNganh;
    private final JTable bangGoiY;
    private final JButton goiYTruongDaiHoc;

    public RecommendDGNL() throws SQLException {
        connection = DatabaseConnection.getConnection();
        setLayout(new BorderLayout());

        // Khởi tạo giao diện
        textNhapDiem = new JTextField(10);
        comboBoxChonNganh = new JComboBox<>();
        goiYTruongDaiHoc = new JButton("Gợi ý trường đại học");
        bangGoiY = new JTable(new DefaultTableModel(new String[]{"Mã Trường", "Tên Trường", "Tên Ngành", "Điểm Đánh Giá Năng Lực"}, 0));

        setupUI();
        setupEvents();

        loadNganhData();
    }

    private void setupUI() {
        // Panel đầu trang
        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(new JLabel("Nhập điểm:"));
        topPanel.add(textNhapDiem);
        topPanel.add(new JLabel("Chọn ngành:"));
        topPanel.add(comboBoxChonNganh);

        // Thêm các thành phần vào JPanel
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(bangGoiY), BorderLayout.CENTER);
        add(goiYTruongDaiHoc, BorderLayout.SOUTH);

        // Thêm sorter cho bảng
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) bangGoiY.getModel());
        bangGoiY.setRowSorter(sorter);
    }

    private void setupEvents() {
        goiYTruongDaiHoc.addActionListener(e -> {
            try {
                int diem = Integer.parseInt(textNhapDiem.getText());
                String selectedNganh = (String) comboBoxChonNganh.getSelectedItem();
                getUniversitySuggestionsByDGNL(diem, selectedNganh);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ!");
            }
        });
    }

    private void loadNganhData() {
        String sql = "SELECT DISTINCT c.tenNganh FROM chuyennganh c JOIN dgnl d ON c.maNganh = d.maNganh";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            comboBoxChonNganh.removeAllItems(); // Xóa các item cũ

            while (resultSet.next()) {
                comboBoxChonNganh.addItem(resultSet.getString("tenNganh"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading ngành data: " + e.getMessage());
        }
    }

    private void getUniversitySuggestionsByDGNL(int diem, String selectedNganh) {
        String sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.diemChuan " +
                "FROM truongdaihoc t " +
                "JOIN dgnl d ON t.maTruong = d.maTruong " +
                "JOIN chuyennganh c ON d.maNganh = c.maNganh " +
                "WHERE c.tenNganh = ? AND d.diemChuan <= ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, selectedNganh);
            preparedStatement.setInt(2, diem);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                updateTableData(resultSet, diem);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void updateTableData(ResultSet resultSet, int diemNguoiDung) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) bangGoiY.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        DecimalFormat df = new DecimalFormat("#.##"); // Định dạng số double
        boolean hasResults = false;

        while (resultSet.next()) {
            String maTruong = resultSet.getString("maTruong");
            String tenTruong = resultSet.getString("tenTruong");
            String tenNganh = resultSet.getString("tenNganh");
            double diemChuan = resultSet.getDouble("diemChuan");

            // Chỉ thêm trường nếu điểm người dùng >= điểm chuẩn
            if (diemNguoiDung >= diemChuan) {
                model.addRow(new Object[]{
                        maTruong, tenTruong, tenNganh, df.format(diemChuan)
                });
                hasResults = true;
            }
        }

        if (!hasResults) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy trường phù hợp với điểm của bạn.");
        }
    }
}