package Version2.src.Controller;

import Version2.src.Utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;

public class RecommendDGNLController {
    private final Connection connection;

    public RecommendDGNLController() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void loadNganhData(JComboBox<String> comboBoxChonNganh) {
        String sql = "SELECT DISTINCT c.tenNganh FROM chuyennganh c JOIN dgnl d ON c.maNganh = d.maNganh";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            comboBoxChonNganh.removeAllItems(); // Xóa các item cũ

            while (resultSet.next()) {
                comboBoxChonNganh.addItem(resultSet.getString("tenNganh"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading ngành data: " + e.getMessage());
        }
    }

    public void getUniversitySuggestionsByDGNL(int diem, String selectedNganh, JTable bangGoiY) {
        String sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.diemChuan " +
                "FROM truongdaihoc t " +
                "JOIN dgnl d ON t.maTruong = d.maTruong " +
                "JOIN chuyennganh c ON d.maNganh = c.maNganh " +
                "WHERE c.tenNganh = ? AND d.diemChuan <= ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, selectedNganh);
            preparedStatement.setInt(2, diem);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                updateTableData(resultSet, diem, bangGoiY);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

    private void updateTableData(ResultSet resultSet, int diemNguoiDung, JTable bangGoiY) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) bangGoiY.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        DecimalFormat df = new DecimalFormat("#.##"); // Định dạng số double
        boolean hasResults = false;

        while (resultSet.next()) {
            String maTruong = resultSet.getString("maTruong");
            String tenTruong = resultSet.getString("tenTruong");
            String tenNganh = resultSet.getString("tenNganh");
            double diemChuan = resultSet.getDouble("diemChuan");

            if (diemNguoiDung >= diemChuan) {
                model.addRow(new Object[]{
                        maTruong, tenTruong, tenNganh, df.format(diemChuan)
                });
                hasResults = true;
            }
        }

        if (!hasResults) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy trường phù hợp với điểm của bạn.");
        }
    }
}
