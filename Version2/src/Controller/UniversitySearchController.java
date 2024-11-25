package Version2.src.Controller;

import Version2.src.Model.*;
import Version2.src.Utils.DatabaseConnection;
import Version2.src.Utils.NonEditableTableModel;
import Version2.src.View.UniversitySearchView;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class UniversitySearchController {
    private UniversitySearchView view;
    private Connection connection;
    private int pageNumber = 1;
    private int pageSize = 10;

    public UniversitySearchController(UniversitySearchView view){
        this.view = view;
        try {
        connection = DatabaseConnection.getConnection();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    initializeListeners();
    loadUniversityData();
}

private void initializeListeners() {
    view.addSearchButtonListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            searchUniversityData();
        }
    });

    view.addPrevButtonListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            prevPage();
        }
    });

    view.addNextButtonListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            nextPage();
        }
    });
}

private void loadUniversityData() {
    try {
        String query = "SELECT * FROM truongdaihoc LIMIT ?, ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, (pageNumber - 1) * pageSize);
        stmt.setInt(2, pageSize);
        ResultSet rs = stmt.executeQuery();
        ArrayList<DaiHocLoadData> data = new ArrayList<>();
        while (rs.next()) {
            DaiHocLoadData daiHocLoadData = new DaiHocLoadData(
                    rs.getString("maTruong"),
                    rs.getString("tenTruong"),
                    rs.getDouble("diemSan")
            );
            data.add(daiHocLoadData);
        }
        Object[][] dataArray = new Object[data.size()][3];
        for (int i = 0; i < data.size(); i++) {
            DaiHocLoadData daiHocLoadData = data.get(i);
            dataArray[i][0] = daiHocLoadData.getMaTruong();
            dataArray[i][1] = daiHocLoadData.getTenTruong();
            dataArray[i][2] = daiHocLoadData.getDiemSan();
        }
        NonEditableTableModel model = new NonEditableTableModel(
                dataArray, new String[]{"Mã Trường", "Tên Trường", "Điểm Sàn"}
        );
        view.getTable().setModel(model);
        TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<>(model);
        view.setTableModel(sorter);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(view.getTable(), "Error: " + e.getMessage());
    }
}

private void searchUniversityData() {
    try {
        StringBuilder query = new StringBuilder(
                "SELECT DISTINCT d.maTruong, d.tenTruong, dt.diemTrungTuyen, dt.tenNganh " +
                        "FROM truongdaihoc d " +
                        "INNER JOIN diemtrungtuyen dt ON d.maTruong = dt.maTruong " +
                        "WHERE 1=1"
        );

        if (!view.getMaTruongField().isEmpty()) {
            query.append(" AND d.maTruong LIKE ?");
        }
        if (!view.getMaNganhField().isEmpty()) {
            query.append(" AND dt.maNganh LIKE ?");
        }
        if (!view.getTenTruongField().isEmpty()) {
            query.append(" AND d.tenTruong LIKE ?");
        }
        if (!view.getTenNganhField().isEmpty()) {
            query.append(" AND dt.tenNganh LIKE ?");
        }
        if (!view.getDiemTrungTuyenField().isEmpty()) {
            query.append(" AND dt.diemTrungTuyen <= ?");
        }

        query.append(" LIMIT ?, ?");

        PreparedStatement stmt = connection.prepareStatement(query.toString());
        int index = 1;
        if (!view.getMaTruongField().isEmpty()) {
            stmt.setString(index++, "%" + view.getMaTruongField() + "%");
        }
        if (!view.getMaNganhField().isEmpty()) {
            stmt.setString(index++, "%" + view.getMaNganhField() + "%");
        }
        if (!view.getTenTruongField().isEmpty()) {
            stmt.setString(index++, "%" + view.getTenTruongField() + "%");
        }
        if (!view.getTenNganhField().isEmpty()) {
            stmt.setString(index++, "%" + view.getTenNganhField() + "%");
        }
        if (!view.getDiemTrungTuyenField().isEmpty()) {
            stmt.setDouble(index++, Double.parseDouble(view.getDiemTrungTuyenField()));
        }

        stmt.setInt(index++, (pageNumber - 1) * pageSize);
        stmt.setInt(index, pageSize);

        ResultSet rs = stmt.executeQuery();
        ArrayList<DaiHocSearchResult> data = new ArrayList<>();
        while (rs.next()) {
            DaiHocSearchResult daiHocSearchResult = new DaiHocSearchResult("", "",0,"", 0);
            daiHocSearchResult.setMaTruong(rs.getString("maTruong"));
            daiHocSearchResult.setTenTruong(rs.getString("tenTruong"));
            daiHocSearchResult.setDiemTrungTuyen(rs.getDouble("diemTrungTuyen"));
            daiHocSearchResult.setTenNganh(rs.getString("tenNganh"));
            data.add(daiHocSearchResult);
        }
        Object[][] dataArray = new Object[data.size()][4];
        for (int i = 0; i < data.size(); i++) {
            DaiHocSearchResult daiHocSearchResult = data.get(i);
            dataArray[i][0] = daiHocSearchResult.getMaTruong();
            dataArray[i][1] = daiHocSearchResult.getTenTruong();
            dataArray[i][2] = daiHocSearchResult.getDiemTrungTuyen();
            dataArray[i][3] = daiHocSearchResult.getTenNganh();
        }
        NonEditableTableModel model = new NonEditableTableModel(
                dataArray, new String[]{"Mã Trường", "Tên Trường", "Điểm Trúng Tuyển", "Tên Ngành"}
        );
        view.getTable().setModel(model);
        TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<>(model);
        view.setTableModel(sorter);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(view.getTable(), "Error: " + e.getMessage());
    }
}

private void prevPage() {
    if (pageNumber > 1) {
        pageNumber--;
        loadUniversityData();
    }
}

private void nextPage() {
    pageNumber++;
    loadUniversityData();
}

private void addToFavorites() {
    int selectedRow = view.getTable().getSelectedRow();
    if (selectedRow != -1) {
        String maTruong = (String) view.getTable().getValueAt(selectedRow, 0);
        String tenTruong = (String) view.getTable().getValueAt(selectedRow, 1);
        String tenNganh = (String) view.getTable().getValueAt(selectedRow, 3);
        double diemSan = (double) view.getTable().getValueAt(selectedRow, 2);

        FavoriteItem favoriteItem = new FavoriteItem(maTruong, tenTruong, tenNganh, diemSan);
        view.addToFavorites(favoriteItem);
    } else {
        JOptionPane.showMessageDialog(view.getTable(), "Vui lòng chọn một trường để thêm vào yêu thích.");
    }
}
}