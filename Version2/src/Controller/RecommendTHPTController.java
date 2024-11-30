package Version2.src.Controller;

import Version2.src.Model.TinhDiemTHPT;
import Version2.src.Model.ToHopMon;
import Version2.src.Utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;

import static Version2.src.Utils.DatabaseConnection.getConnection;

public class RecommendTHPTController {
    private final Connection connection;

    public RecommendTHPTController() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void loadAllUniversitySuggestions(JTable bangGoiY) {
        String sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.toHopMon, d.diemTrungTuyen " +
                "FROM truongdaihoc t " +
                "JOIN diemtrungtuyen d ON t.maTruong = d.maTruong " +
                "JOIN chuyennganh c ON d.maNganh = c.maNganh";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            DefaultTableModel model = (DefaultTableModel) bangGoiY.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            while (resultSet.next()) {
                String maTruong = resultSet.getString("maTruong");
                String tenTruong = resultSet.getString("tenTruong");
                String tenNganh = resultSet.getString("tenNganh");
                String toHopMon = resultSet.getString("toHopMon");
                double diemTrungTuyen = resultSet.getDouble("diemTrungTuyen");

                model.addRow(new Object[]{maTruong, tenTruong, tenNganh, toHopMon, diemTrungTuyen});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading all data: " + e.getMessage());
        }
    }
    public void loadToHopMonToComboBox(JComboBox<String> comboBox) {
        String query = "SELECT maToHop, mon1, mon2, mon3 FROM toHopMon";  // Truy vấn lấy maToHop và các môn

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            comboBox.removeAllItems();

            // Duyệt qua các bản ghi trong kết quả truy vấn và thêm vào comboBox
            while (resultSet.next()) {
                String maToHop = resultSet.getString("maToHop");
                String mon1 = resultSet.getString("mon1");
                String mon2 = resultSet.getString("mon2");
                String mon3 = resultSet.getString("mon3");

                // Tạo chuỗi hiển thị trong JComboBox (có thể hiển thị tên môn học hoặc maToHop tùy thích)
                String displayText = maToHop + " - " + mon1 + ", " + mon2 + ", " + mon3;
                comboBox.addItem(displayText); // Thêm vào JComboBox
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading ngành data: " + e.getMessage());// Xử lý lỗi
        }
    }

    public void loadNganhData(JComboBox<String> comboBoxChonNganh) {
        try {
            String sql = "SELECT DISTINCT c.tenNganh FROM chuyennganh c JOIN diemtrungtuyen d ON c.maNganh = d.maNganh";
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
    public void getUniversitySuggestionsByTHPT(double diemXetTuyen, String selectedNganh, String selectedToHopMon, JTable bangGoiY) {
        String sql;
        if ("Tất Cả Các Ngành".equals(selectedNganh)) {
            sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, dt.toHopMon, dt.diemTrungTuyen " +
                    "FROM truongdaihoc t " +
                    "JOIN diemtrungtuyen dt ON t.maTruong = dt.maTruong " +
                    "JOIN chuyennganh c ON dt.maNganh = c.maNganh " +
                    "WHERE dt.toHopMon LIKE ? AND dt.diemTrungTuyen <= ?";
        } else {
            sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, dt.toHopMon, dt.diemTrungTuyen " +
                    "FROM truongdaihoc t " +
                    "JOIN diemtrungtuyen dt ON t.maTruong = dt.maTruong " +
                    "JOIN chuyennganh c ON dt.maNganh = c.maNganh " +
                    "WHERE c.tenNganh = ? AND dt.toHopMon LIKE ? AND dt.diemTrungTuyen <= ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Tính ngưỡng điểm xét tuyển
            double diemThreshold = diemXetTuyen + 0.5;

            // Nếu chọn tất cả các ngành
            if ("Tất Cả Các Ngành".equals(selectedNganh)) {
                preparedStatement.setString(1, "%" + selectedToHopMon + "%"); // Tìm tổ hợp môn trong chuỗi
                preparedStatement.setDouble(2, diemThreshold);
            } else {
                preparedStatement.setString(1, selectedNganh);
                preparedStatement.setString(2, "%" + selectedToHopMon + "%"); // Tìm tổ hợp môn trong chuỗi
                preparedStatement.setDouble(3, diemThreshold);
            }

            // Thực thi truy vấn
            ResultSet resultSet = preparedStatement.executeQuery();

            // Xử lý kết quả trả về
            DefaultTableModel model = (DefaultTableModel) bangGoiY.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            boolean hasResults = false;
            while (resultSet.next()) {
                String maTruong = resultSet.getString("maTruong");
                String tenTruong = resultSet.getString("tenTruong");
                String tenNganh = resultSet.getString("tenNganh");
                String toHopMon = resultSet.getString("toHopMon");
                double diemTrungTuyen = resultSet.getDouble("diemTrungTuyen");

                // Thêm dữ liệu vào bảng
                model.addRow(new Object[]{maTruong, tenTruong, tenNganh, toHopMon, diemTrungTuyen});
                hasResults = true;
            }

            if (!hasResults) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy ngành nào phù hợp với điểm của bạn.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

}