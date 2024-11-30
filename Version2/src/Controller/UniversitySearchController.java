package Version2.src.Controller;

import Version2.src.Model.DaiHocLoadData;
import Version2.src.Model.DaiHocSearchResult;
import Version2.src.Model.FavoriteItem;
import Version2.src.Utils.DatabaseConnection;
import Version2.src.Utils.NonEditableTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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

    public void addToFavorites(JTable universityTable, DefaultListModel<FavoriteItem> favoriteListModel) {
        int selectedRow = universityTable.getSelectedRow();
        if (selectedRow != -1) {
            String maTruong = (String) universityTable.getValueAt(selectedRow, 0); // Cột 0
            String tenTruong = (String) universityTable.getValueAt(selectedRow, 1); // Cột 1
            String tenNganh = (String) universityTable.getValueAt(selectedRow, 3);
            double diemSan = (double) universityTable.getValueAt(selectedRow, 2);   // Cột 2

            FavoriteItem favoriteItem = new FavoriteItem(maTruong, tenTruong, tenNganh, diemSan);
            favoriteListModel.addElement(favoriteItem);
        } else {
            JOptionPane.showMessageDialog(universityTable, "Vui lòng chọn một trường để thêm vào yêu thích.");
        }
    }
}