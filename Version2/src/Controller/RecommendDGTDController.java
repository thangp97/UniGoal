package Version2.src.Controller;

import Version2.src.Utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class RecommendDGTDController {
    private final Connection connection;

    public RecommendDGTDController() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void loadNganhData(JComboBox<String> comboBoxChonNganh) {
        try {
            String sql = "SELECT DISTINCT c.tenNganh FROM chuyennganh c JOIN dgtd d ON c.maNganh = d.maNganh";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Xóa các item cũ trong comboBox
            comboBoxChonNganh.removeAllItems();

            while (resultSet.next()) {
                comboBoxChonNganh.addItem(resultSet.getString("tenNganh"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading ngành data: " + e.getMessage());
        }
    }

    public void getUniversitySuggestionsByDGTYDiem(int diem, String selectedNganh, JTable bangGoiY) {
        try {
            String sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.diemChuan " +
                    "FROM truongdaihoc t " +
                    "JOIN dgtd d ON t.maTruong = d.maTruong " +
                    "JOIN chuyennganh c ON d.maNganh = c.maNganh " +
                    "WHERE c.tenNganh = ? AND d.diemChuan <= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedNganh);
            preparedStatement.setInt(2, diem);

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) bangGoiY.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            boolean hasResults = false;
            while (resultSet.next()) {
                String maTruong = resultSet.getString("maTruong");
                String tenTruong = resultSet.getString("tenTruong");
                String tenNganh = resultSet.getString("tenNganh");
                int diemChuan = resultSet.getInt("diemChuan");

                if (diem >= diemChuan) {
                    model.addRow(new Object[]{maTruong, tenTruong, tenNganh, diemChuan});
                    hasResults = true;
                }
            }

            if (!hasResults) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy trường phù hợp với điểm của bạn.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }
}
