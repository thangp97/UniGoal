package Version2.src.Controller;

import Version2.src.Utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class RecommendDGTD extends JPanel {
    private Connection connection;
    private JTextField textNhapDiem;
    private JComboBox<String> comboBoxChonNganh;
    private JTable bangGoiY;
    private JButton goiYTruongDaiHoc;

    public RecommendDGTD() throws SQLException {
        connection = DatabaseConnection.getConnection();
        setLayout(new BorderLayout());

        // Khởi tạo các thành phần giao diện
        textNhapDiem = new JTextField(10);
        comboBoxChonNganh = new JComboBox<>();
        goiYTruongDaiHoc = new JButton("Gợi ý trường đại học");
        bangGoiY = new JTable(new DefaultTableModel(new String[]{"Mã Trường", "Tên Trường", "Tên Ngành", "Điểm Đánh Giá Tư Duy"}, 0));

        // Panel cho đầu trang
        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(new JLabel("Nhập điểm:"));
        topPanel.add(textNhapDiem);
        topPanel.add(new JLabel("Chọn ngành:"));
        topPanel.add(comboBoxChonNganh);

        // Thêm các thành phần vào JPanel
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(bangGoiY), BorderLayout.CENTER);
        add(goiYTruongDaiHoc, BorderLayout.SOUTH);

        // Sự kiện nút gợi ý
        goiYTruongDaiHoc.addActionListener(e -> {
            try {
                int diem = Integer.parseInt(textNhapDiem.getText());
                String selectedNganh = (String) comboBoxChonNganh.getSelectedItem();
                getUniversitySuggestionsByDGTYDiem(diem, selectedNganh);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ!");
            }
        });

        loadNganhData();
    }

    private void loadNganhData() {
        try {
            String sql = "SELECT DISTINCT c.tenNganh FROM chuyennganh c JOIN dgtd d ON c.maNganh = d.maNganh";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Xóa các item cũ trong comboBox
            comboBoxChonNganh.removeAllItems();

            while (resultSet.next()) {
                String tenNganh = resultSet.getString("tenNganh");
                comboBoxChonNganh.addItem(tenNganh);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading ngành data: " + e.getMessage());
        }
    }

    private void getUniversitySuggestionsByDGTYDiem(int diem, String selectedNganh) {
        try {
            String sql = "SELECT t.maTruong, t.tenTruong, c.tenNganh, d.diemChuan " +
                    "FROM truongdaihoc t " +
                    "JOIN dgtv d ON t.maTruong = d.maTruong " +
                    "JOIN chuyennganh c ON d.maNganh = c.maNganh " +
                    "WHERE c.tenNganh = ? AND d.diemChuan <= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedNganh);
            preparedStatement.setInt(2, diem);

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) bangGoiY.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            while (resultSet.next()) {
                String maTruong = resultSet.getString("maTruong");
                String tenTruong = resultSet.getString("tenTruong");
                String tenNganh = resultSet.getString("tenNganh");
                int diemChuan = resultSet.getInt("diemChuan");

                if (diem >= diemChuan) {
                    model.addRow(new Object[]{maTruong, tenTruong, tenNganh, diemChuan});
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
}
