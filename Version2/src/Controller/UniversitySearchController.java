package Version2.src.Controller;

import Version2.src.Main.Main;
import Version2.src.Model.DaiHocLoadData;
import Version2.src.Model.DaiHocSearchResult;
import Version2.src.Model.DaiHocFavoriteData;
import Version2.src.Utils.DatabaseConnection;
import Version2.src.Utils.NonEditableTableModel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class UniversitySearchController {
    private Connection connection;

    public UniversitySearchController() throws SQLException {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateTableWithFavorites(JTable table, ArrayList<Object[]> favoriteList) {
        // Lấy mô hình bảng hiện tại
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        // Thêm dữ liệu từ danh sách yêu thích vào bảng
        for (Object[] favorite : favoriteList) {
            model.addRow(favorite); // Thêm dòng vào bảng
        }
    }

    public static ArrayList<Object[]> getFavoritesByUsername(String username) {
        String query = "SELECT maTruong, tenTruong, tenNganh, diemTrungTuyen FROM dsyeuthich WHERE username = ?";
        ArrayList<Object[]> favoriteList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Lưu dữ liệu vào mảng Object[]
                Object[] favoriteData = new Object[] {
                        rs.getString("maTruong"),
                        rs.getString("tenTruong"),
                        rs.getString("tenNganh"),
                        rs.getDouble("diemTrungTuyen")
                };
                favoriteList.add(favoriteData); // Thêm vào danh sách
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách yêu thích: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return favoriteList;
    }


    public static void displayFavorites(JTable universityTable) {
        try {
            ArrayList<Object[]> favorites = getFavoritesByUsername(Main.getCurrentUsername());
            if (favorites.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không có mục nào trong danh sách yêu thích.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                updateTableWithFavorites(universityTable, favorites); // Cập nhật bảng

                // Tạo một JScrollPane chứa bảng
                JScrollPane scrollPane = new JScrollPane(universityTable);
                scrollPane.setPreferredSize(new Dimension(900, 800)); // Thay đổi kích thước của scrollPane

                // Tạo JDialog để chứa JScrollPane
                JDialog dialog = new JDialog();
                dialog.setTitle("Danh sách yêu thích");
                dialog.setSize(900, 800);  // Tăng kích thước của cửa sổ pop-up
                dialog.setLocationRelativeTo(null);  // Đặt cửa sổ ở giữa màn hình
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Đóng cửa sổ khi bấm nút x

                // Thêm JScrollPane vào dialog
                dialog.add(scrollPane);

                // Hiển thị dialog
                dialog.setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi hiển thị danh sách yêu thích: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            DaiHocFavoriteData daiHocFavoriteData = new DaiHocFavoriteData("", "", "", 0, "", 0);
            daiHocFavoriteData.setMaTruong(selectedMaTruong);
            daiHocFavoriteData.setTenTruong((String) universityTable.getValueAt(selectedRow, 1));
            daiHocFavoriteData.setTenNganh(selectedTenNganh);
            daiHocFavoriteData.setDiemTrungTuyen((double) universityTable.getValueAt(selectedRow, 2));

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
                    preparedStatement.setString(5, Main.getCurrentUsername());

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
