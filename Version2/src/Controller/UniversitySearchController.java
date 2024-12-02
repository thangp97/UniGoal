package Version2.src.Controller;

import Version2.src.Model.DaiHocLoadData;
import Version2.src.Model.DaiHocSearchResult;
import Version2.src.Model.DaiHocFavoriteData;
import Version2.src.Utils.DatabaseConnection;
import Version2.src.Utils.NonEditableTableModel;
import Version2.src.View.LoginView;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class UniversitySearchController {
    private Connection connection;
    private LoginView loginView;

    public UniversitySearchController() throws SQLException {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveFavoriteToDatabase(DaiHocFavoriteData favoriteData, String username) {
        String query = "INSERT INTO danh_sach_yeu_thich (username, maTruong, tenTruong, tenNganh, diemTrungTuyen) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, favoriteData.getMaTruong());
            stmt.setString(3, favoriteData.getTenTruong());
            stmt.setString(4, favoriteData.getTenNganh());
            stmt.setDouble(5, favoriteData.getDiemTrungTuyen());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Đã thêm trường và ngành vào danh sách yêu thích.",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu vào cơ sở dữ liệu: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // In ra lỗi chi tiết để debug
        }
    }

    public void loadFavoritesFromDatabase(JTable universityTable, String username) {
        String query = "String query = \"SELECT maTruong, tenTruong, tenNganh, diemTrungTuyen FROM danh_sach_yeu_thich WHERE username = ?\";\n";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);  // Thay đổi username với tên người dùng hiện tại

            try (ResultSet rs = stmt.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) universityTable.getModel();
                model.setRowCount(0);  // Clear old data

                // Lặp qua các kết quả và thêm chúng vào bảng
                while (rs.next()) {
                    String maTruong = rs.getString("maTruong");
                    String tenTruong = rs.getString("tenTruong");
                    String tenNganh = rs.getString("tenNganh");
                    double diemTrungTuyen = rs.getDouble("diemTrungTuyen");

                    // Thêm dữ liệu vào bảng
                    model.addRow(new Object[]{maTruong, tenTruong, tenNganh, diemTrungTuyen});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách yêu thích: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadUniversityData(JTable universityTable) {
        String sql = "SELECT * FROM truongdaihoc";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            ArrayList<DaiHocLoadData> data = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) universityTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                String maTruong = rs.getString("maTruong");
                String tenTruong = rs.getString("tenTruong");
                double diemSan = rs.getDouble("diemSan");

                model.addRow(new Object[]{maTruong, tenTruong, diemSan});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading all data: " + e.getMessage());
        }
    }

    public void loadDetailData(String maTruong, JTable detailTable) throws SQLException {
        String query = "SELECT diem_thpt, diem_dgnl, diem_dgtd FROM majors WHERE ma_truong = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, maTruong);
        ResultSet resultSet = statement.executeQuery();

        DefaultTableModel model = (DefaultTableModel) detailTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        while (resultSet.next()) {
            String diemThpt = resultSet.getString("diem_thpt");
            String diemDgnl = resultSet.getString("diem_dgnl");
            String diemDgtd = resultSet.getString("diem_dgtd");
            model.addRow(new Object[]{diemThpt, diemDgnl, diemDgtd});
        }
    }

    public void searchUniversityData(String maTruong, String maNganh, String tenTruong, String tenNganh, String diemTrungTuyen, JTable universityTable) {
        StringBuilder query = new StringBuilder(
                "SELECT DISTINCT d.maTruong, d.tenTruong, dt.diemTrungTuyen, dt.tenNganh " +
                        "FROM truongdaihoc d " +
                        "INNER JOIN diemtrungtuyen dt ON d.maTruong = dt.maTruong " +
                        "WHERE 1=1"
        );

        if (!maTruong.isEmpty()) {
            query.append(" AND d.maTruong LIKE ?");
        }
        if (!maNganh.isEmpty()) {
            query.append(" AND dt.maNganh LIKE ?");
        }
        if (!tenTruong.isEmpty()) {
            query.append(" AND d.tenTruong LIKE ?");
        }
        if (!tenNganh.isEmpty()) {
            query.append(" AND dt.tenNganh LIKE ?");
        }
        if (!diemTrungTuyen.isEmpty()) {
            query.append(" AND dt.diemTrungTuyen <= ?");
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            int index = 1;
            if (!maTruong.isEmpty()) {
                stmt.setString(index++, "%" + maTruong + "%");
            }
            if (!maNganh.isEmpty()) {
                stmt.setString(index++, "%" + maNganh + "%");
            }
            if (!tenTruong.isEmpty()) {
                stmt.setString(index++, "%" + tenTruong + "%");
            }
            if (!tenNganh.isEmpty()) {
                stmt.setString(index++, "%" + tenNganh + "%");
            }
            if (!diemTrungTuyen.isEmpty()) {
                stmt.setDouble(index++, Double.parseDouble(diemTrungTuyen));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<DaiHocSearchResult> data = new ArrayList<>();
                while (rs.next()) {
                    DaiHocSearchResult daiHocSearchResult = new DaiHocSearchResult("", "", 0, "", 0);
                    daiHocSearchResult.setMaTruong(rs.getString("maTruong"));
                    daiHocSearchResult.setTenTruong(rs.getString("tenTruong"));
                    daiHocSearchResult.setDiemTrungTuyen(rs.getDouble("diemTrungTuyen"));
                    daiHocSearchResult.setTenNganh(rs.getString("tenNganh"));
                    data.add(daiHocSearchResult);
                }

                Object[][] dataArray = new Object[data.size()][4];
                for (int i = 0; i < data.size(); i++) {
                    DaiHocSearchResult result = data.get(i);
                    dataArray[i][0] = result.getMaTruong();
                    dataArray[i][1] = result.getTenTruong();
                    dataArray[i][2] = result.getDiemTrungTuyen();
                    dataArray[i][3] = result.getTenNganh();
                }

                NonEditableTableModel model = new NonEditableTableModel(dataArray, new String[]{"Mã Trường", "Tên Trường", "Điểm Trúng Tuyển", "Tên Ngành"});
                universityTable.setModel(model);

                // Sort the table
                TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<>(model);
                universityTable.setRowSorter(sorter);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void addToFavorites(JTable universityTable, DefaultListModel<DaiHocFavoriteData> favoriteListModel) {
        int selectedRow = universityTable.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy mã trường và tên ngành từ bảng
            String selectedMaTruong = (String) universityTable.getValueAt(selectedRow, 0);
            String selectedTenNganh = (String) universityTable.getValueAt(selectedRow, 3);

            // Kiểm tra nếu mã trường và tên ngành đã tồn tại trong danh sách yêu thích
            for (int i = 0; i < favoriteListModel.size(); i++) {
                DaiHocFavoriteData existingFavorite = favoriteListModel.getElementAt(i);
                if (existingFavorite.getMaTruong().equals(selectedMaTruong) &&
                        existingFavorite.getTenNganh().equals(selectedTenNganh)) {
                    JOptionPane.showMessageDialog(universityTable,
                            "Trường và ngành này đã có trong danh sách yêu thích.",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return; // Không thêm nếu đã tồn tại
                }
            }

            // Nếu không tồn tại, thêm vào danh sách yêu thích
            DaiHocFavoriteData daiHocFavoriteData = new DaiHocFavoriteData("", "", "", 0, "", 0,"");
            daiHocFavoriteData.setMaTruong(selectedMaTruong);
            daiHocFavoriteData.setTenTruong((String) universityTable.getValueAt(selectedRow, 1));
            daiHocFavoriteData.setTenNganh(selectedTenNganh);
            daiHocFavoriteData.setDiemTrungTuyen((double) universityTable.getValueAt(selectedRow, 2));
            daiHocFavoriteData.setUsername(loginView.getUsername());

            // Thêm vào mô hình danh sách yêu thích
            favoriteListModel.addElement(daiHocFavoriteData);

            // Lưu dữ liệu vào cơ sở dữ liệu
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO dsyeuthich (maTruong, tenTruong, tenNganh, diemTrungTuyen, username) " +
                        "VALUES (?, ?, ?, ?,?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, selectedMaTruong);
                    preparedStatement.setString(2, (String) universityTable.getValueAt(selectedRow, 1));
                    preparedStatement.setString(3, selectedTenNganh);
                    preparedStatement.setDouble(4, (double) universityTable.getValueAt(selectedRow, 2));
                    preparedStatement.setString(5, loginView.getUsername());

                    // Thực thi câu truy vấn
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(universityTable,
                        "Có lỗi xảy ra trong quá trình thêm vào cơ sở dữ liệu!",
                        "Thông báo lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(universityTable,
                    "Vui lòng chọn một trường và ngành để thêm vào danh sách yêu thích.",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    public void exportFavoritesToCSV(DefaultListModel<DaiHocFavoriteData> favoriteListModel) {
        // Mở hộp thoại để người dùng chọn vị trí lưu file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu danh sách yêu thích");
        fileChooser.setSelectedFile(new File("DanhSachYeuThich.csv")); // Đặt tên file mặc định

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File csvFile = fileChooser.getSelectedFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                // Ghi tiêu đề cho các cột
                writer.write("Mã Trường,Tên Trường,Tên Ngành,Điểm Trúng Tuyển");
                writer.newLine();

                // Ghi dữ liệu từ danh sách yêu thích vào file
                for (int i = 0; i < favoriteListModel.size(); i++) {
                    DaiHocFavoriteData favoriteData = favoriteListModel.getElementAt(i);
                    writer.write(favoriteData.getMaTruong() + "," +
                            favoriteData.getTenTruong() + "," +
                            favoriteData.getTenNganh() + "," +
                            favoriteData.getDiemTrungTuyen());
                    writer.newLine();
                }

                JOptionPane.showMessageDialog(null, "Đã xuất danh sách yêu thích thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất danh sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
