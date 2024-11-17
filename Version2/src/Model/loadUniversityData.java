//package Version2.src.Model;
//
//import javax.swing.*;
//import javax.swing.table.TableRowSorter;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class loadUniversityData {
//    public void loadUniversityData(boolean isSearchMode) {
//        isSearchMode = false; // Quay lại chế độ hiển thị toàn bộ
//        try {
//            String query = "SELECT * FROM daihoc LIMIT ?, ?";
//            PreparedStatement stmt = connection.prepareStatement(query);
//            stmt.setInt(1, (pageNumber - 1) * pageSize);
//            stmt.setInt(2, pageSize);
//            ResultSet rs = stmt.executeQuery();
//            ArrayList<Object[]> data = new ArrayList<>();
//            while (rs.next()) {
//                String maTruong = rs.getString("maTruong");
//                String tenTruong = rs.getString("tenTruong");
//                double diemSan = rs.getDouble("diemSan");
//                data.add(new Object[]{maTruong, tenTruong, diemSan});
//            }
//            Object[][] dataArray = new Object[data.size()][3];
//            for (int i = 0; i < data.size(); i++) {
//                dataArray[i] = data.get(i);
//            }
//            NonEditableTableModel model = new NonEditableTableModel(
//                    dataArray, new String[]{"Mã Trường", "Tên Trường", "Điểm Sàn"}
//            );
//            table.setModel(model);
//
//            // Thêm sắp xếp cho bảng
//            TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<>(model);
//            table.setRowSorter(sorter);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
//        }
//    }
//}
