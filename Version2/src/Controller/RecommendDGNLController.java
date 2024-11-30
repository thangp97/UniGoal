package Version2.src.Controller;

import Version2.src.Utils.DatabaseConnection;
import Version2.src.Utils.NonEditableTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecommendDGNLController {
    private final Connection connection;

    public RecommendDGNLController() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void loadAllUniversitySuggestions(JTable bangGoiY) {
        String sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.diemChuan " +
                "FROM truongdaihoc t " +
                "JOIN dgnl d ON t.maTruong = d.maTruong " +
                "JOIN chuyennganh c ON d.maNganh = c.maNganh";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Chuẩn bị dữ liệu cho bảng
            ArrayList<Object[]> data = new ArrayList<>();
            while (resultSet.next()) {
                String maTruong = resultSet.getString("maTruong");
                String tenTruong = resultSet.getString("tenTruong");
                String tenNganh = resultSet.getString("tenNganh");
                double diemChuan = resultSet.getDouble("diemChuan");

                data.add(new Object[]{maTruong, tenTruong, tenNganh, diemChuan});
            }

            // Chuyển đổi dữ liệu sang mảng để truyền vào NonEditableTableModel
            Object[][] dataArray = data.toArray(new Object[0][]);
            String[] columnNames = {"Mã Trường", "Tên Trường", "Tên Ngành", "Điểm Chuẩn"};

            // Áp dụng NonEditableTableModel
            NonEditableTableModel model = new NonEditableTableModel(dataArray, columnNames);
            bangGoiY.setModel(model); // Cập nhật bảng với mô hình mới

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading all data: " + e.getMessage());
        }
    }

    public void loadNganhData(JComboBox<String> comboBoxChonNganh) {
        String sql = "SELECT DISTINCT c.tenNganh FROM chuyennganh c JOIN dgnl d ON c.maNganh = d.maNganh";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            comboBoxChonNganh.removeAllItems(); // Xóa các item cũ
            comboBoxChonNganh.addItem("Tất Cả Các Ngành"); // Thêm lựa chọn mặc định

            while (resultSet.next()) {
                comboBoxChonNganh.addItem(resultSet.getString("tenNganh"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading ngành data: " + e.getMessage());
        }
    }

    public void getUniversitySuggestionsByDGNL(int diem, String selectedNganh, JTable bangGoiY) {
        String sql;

        if ("Tất Cả Các Ngành".equals(selectedNganh)) {
            sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.diemChuan " +
                    "FROM truongdaihoc t " +
                    "JOIN dgnl d ON t.maTruong = d.maTruong " +
                    "JOIN chuyennganh c ON d.maNganh = c.maNganh " +
                    "WHERE d.diemChuan <= ?";
        } else {
            sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.diemChuan " +
                    "FROM truongdaihoc t " +
                    "JOIN dgnl d ON t.maTruong = d.maTruong " +
                    "JOIN chuyennganh c ON d.maNganh = c.maNganh " +
                    "WHERE c.tenNganh = ? AND d.diemChuan <= ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (!"Tất Cả Các Ngành".equals(selectedNganh)) {
                preparedStatement.setString(1, selectedNganh);
                preparedStatement.setInt(2, diem);
            } else {
                preparedStatement.setInt(1, diem);
            }

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