package Version2.src.Controller;

import Version2.src.Utils.DatabaseConnection;
import Version2.src.Utils.NonEditableTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class RecommendDGTDController {
    private final Connection connection;

    public RecommendDGTDController() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void loadAllUniversitySuggestions(JTable bangGoiY) {
        String sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.toHopMon, d.diemChuan " +
                "FROM truongdaihoc t " +
                "JOIN dgtd d ON t.maTruong = d.maTruong " +
                "JOIN chuyennganh c ON d.maNganh = c.maNganh";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ArrayList<Object[]> data = new ArrayList<>();
            while (resultSet.next()) {
                String maTruong = resultSet.getString("maTruong");
                String tenTruong = resultSet.getString("tenTruong");
                String tenNganh = resultSet.getString("tenNganh");
                String toHopMon = resultSet.getString("toHopMon");
                int diemChuan = resultSet.getInt("diemChuan");

                data.add(new Object[]{maTruong, tenTruong, tenNganh, toHopMon, diemChuan});
            }

            Object[][] dataArray = data.toArray(new Object[0][]);
            String[] columnNames = {"Mã Trường", "Tên Trường", "Tên Ngành", "Tổ Hợp Môn", "Điểm chuẩn"};

            NonEditableTableModel model = new NonEditableTableModel(dataArray, columnNames);
            bangGoiY.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading all data: " + e.getMessage());
        }
    }

    public void loadNganhData(JComboBox<String> comboBoxChonNganh) {
        try {
            String sql = "SELECT DISTINCT c.tenNganh FROM chuyennganh c JOIN dgtd d ON c.maNganh = d.maNganh";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            comboBoxChonNganh.removeAllItems(); // Xóa các item cũ
            comboBoxChonNganh.addItem("Tất Cả Các Ngành"); // Thêm lựa chọn mặc định

            while (resultSet.next()) {
                comboBoxChonNganh.addItem(resultSet.getString("tenNganh"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading ngành data: " + e.getMessage());
        }
    }

    public void getUniversitySuggestionsByDGTD(int diem, String selectedNganh, String selectedToHopMon, JTable bangGoiY) {
        String sql;

        if ("Tất Cả Các Ngành".equals(selectedNganh)) {
            sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.toHopMon, d.diemChuan " +
                    "FROM truongdaihoc t " +
                    "JOIN dgtd d ON t.maTruong = d.maTruong " +
                    "JOIN chuyennganh c ON d.maNganh = c.maNganh " +
                    "WHERE d.toHopMon = ? AND d.diemChuan <= ?";
        } else {
            sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.toHopMon, d.diemChuan " +
                    "FROM truongdaihoc t " +
                    "JOIN dgtd d ON t.maTruong = d.maTruong " +
                    "JOIN chuyennganh c ON d.maNganh = c.maNganh " +
                    "WHERE c.tenNganh = ? AND d.toHopMon = ? AND d.diemChuan <= ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if ("Tất Cả Các Ngành".equals(selectedNganh)) {
                preparedStatement.setString(1, selectedToHopMon);
                preparedStatement.setInt(2, diem);
            } else {
                preparedStatement.setString(1, selectedNganh);
                preparedStatement.setString(2, selectedToHopMon);
                preparedStatement.setInt(3, diem);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) bangGoiY.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            boolean hasResults = false;
            while (resultSet.next()) {
                String maTruong = resultSet.getString("maTruong");
                String tenTruong = resultSet.getString("tenTruong");
                String tenNganh = resultSet.getString("tenNganh");
                String toHopMon = resultSet.getString("toHopMon");
                int diemChuan = resultSet.getInt("diemChuan");

                if (diem >= diemChuan) {
                    model.addRow(new Object[]{maTruong, tenTruong, tenNganh, toHopMon, diemChuan});
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
