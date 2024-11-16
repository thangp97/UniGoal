//package Version2.src.Controller;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//import java.util.ArrayList;
//
//public class UniversitySearchUI {
//    private JFrame frame;
//    private JTable table;
//    private Connection connection;
//    private int pageNumber = 1;
//    private int pageSize = 5;
//
//    // Các trường tìm kiếm
//    private JTextField maTruongField;
//    private JTextField maNganhField;
//    private JTextField tenTruongField;
//    private JTextField tenNganhField;
//    private JTextField diemSanField;
//
//    // Danh sách yêu thích
//    private DefaultListModel<String> favoriteListModel;
//    private JList<String> favoriteList;
//
//    public static void main(String[] args) {
//        new UniversitySearchUI().init();
//    }
//
//    // Khởi tạo giao diện
//    public void init() {
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/unigoal", "root", "29102004");
//            frame = new JFrame("University Management");
//            frame.setSize(800, 600);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setLayout(new BorderLayout());
//
//            JPanel panel = new JPanel();
//            panel.setLayout(new BorderLayout());
//
//            // Tạo panel tìm kiếm
//            JPanel searchPanel = new JPanel();
//            searchPanel.setLayout(new GridLayout(6, 2));
//            searchPanel.add(new JLabel("Mã Trường:"));
//            maTruongField = new JTextField();
//            searchPanel.add(maTruongField);
//            searchPanel.add(new JLabel("Mã Ngành:"));
//            maNganhField = new JTextField();
//            searchPanel.add(maNganhField);
//            searchPanel.add(new JLabel("Tên Trường:"));
//            tenTruongField = new JTextField();
//            searchPanel.add(tenTruongField);
//            searchPanel.add(new JLabel("Tên Ngành:"));
//            tenNganhField = new JTextField();
//            searchPanel.add(tenNganhField);
//            searchPanel.add(new JLabel("Điểm Sàn:"));
//            diemSanField = new JTextField();
//            searchPanel.add(diemSanField);
//
//            // Nút tìm kiếm
//            JButton searchButton = new JButton("Tìm Kiếm");
//            searchButton.addActionListener(e -> searchUniversityData());
//            searchPanel.add(searchButton);
//
//            panel.add(searchPanel, BorderLayout.NORTH);
//
//            // Hiển thị bảng các trường đại học
//            table = new JTable();
////            loadUniversityData();
//            JScrollPane scrollPane = new JScrollPane(table);
//            panel.add(scrollPane, BorderLayout.CENTER);
//
//            // Nút thêm vào danh sách yêu thích
//            JButton addToFavoritesButton = new JButton("Thêm vào yêu thích");
//            addToFavoritesButton.addActionListener(e -> addToFavorites());
//            panel.add(addToFavoritesButton, BorderLayout.SOUTH);
//
//            // Danh sách yêu thích
//            favoriteListModel = new DefaultListModel<>();
//            favoriteList = new JList<>(favoriteListModel);
//            JScrollPane favoriteScrollPane = new JScrollPane(favoriteList);
//            panel.add(favoriteScrollPane, BorderLayout.EAST);
//
//            frame.add(panel);
//            frame.setVisible(true);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
//        }
//    }
//
//
//    // Tìm kiếm trường đại học theo các tiêu chí
//    public void searchUniversityData() {
//        try {
//            StringBuilder query = new StringBuilder("SELECT * FROM daihoc WHERE 1=1");
//
//            // Thêm điều kiện tìm kiếm cho từng trường hợp
//            if (!maTruongField.getText().isEmpty()) {
//                query.append(" AND maTruong LIKE ?");
//            }
//            if (!maNganhField.getText().isEmpty()) {
//                query.append(" AND maNganh LIKE ?");
//            }
//            if (!tenTruongField.getText().isEmpty()) {
//                query.append(" AND tenTruong LIKE ?");
//            }
//            if (!tenNganhField.getText().isEmpty()) {
//                query.append(" AND tenNganh LIKE ?");
//            }
//            if (!diemSanField.getText().isEmpty()) {
//                query.append(" AND diemSan >= ?");
//            }
//
//            PreparedStatement stmt = connection.prepareStatement(query.toString());
//
//            int index = 1;
//            if (!maTruongField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + maTruongField.getText() + "%");
//            }
//            if (!maNganhField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + maNganhField.getText() + "%");
//            }
//            if (!tenTruongField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + tenTruongField.getText() + "%");
//            }
//            if (!tenNganhField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + tenNganhField.getText() + "%");
//            }
//            if (!diemSanField.getText().isEmpty()) {
//                stmt.setDouble(index++, Double.parseDouble(diemSanField.getText()));
//            }
//
//            ResultSet rs = stmt.executeQuery();
//            ArrayList<Object[]> data = new ArrayList<>();
//            while (rs.next()) {
//                String maTruong = rs.getString("maTruong");
//                String tenTruong = rs.getString("tenTruong");
//                double diemSan = rs.getDouble("diemSan");
//                data.add(new Object[]{maTruong, tenTruong, diemSan});
//            }
//
//            Object[][] dataArray = new Object[data.size()][3];
//            for (int i = 0; i < data.size(); i++) {
//                dataArray[i] = data.get(i);
//            }
//            table.setModel(new javax.swing.table.DefaultTableModel(
//                    dataArray, new String[]{"Mã Trường", "Tên Trường", "Điểm Sàn"}
//            ));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
//        }
//    }
//
//    // Thêm trường đại học vào danh sách yêu thích
//    private void addToFavorites() {
//        int selectedRow = table.getSelectedRow();
//        if (selectedRow != -1) {
//            String maTruong = (String) table.getValueAt(selectedRow, 0);
//            String tenTruong = (String) table.getValueAt(selectedRow, 1);
//            favoriteListModel.addElement(maTruong + " - " + tenTruong);
//        } else {
//            JOptionPane.showMessageDialog(frame, "Hãy chọn một trường để thêm vào yêu thích.");
//        }
//    }
//}

package Version2.src.Controller;



//package Version2.src.Controller;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.event.*;
//import java.sql.*;
//import java.util.ArrayList;
//
//public class UniversitySearchUI {
//    private JFrame frame;
//    private JTable table;
//    private Connection connection;
//    private int pageNumber = 1;
//    private int pageSize = 5;
//
//    // Các trường tìm kiếm
//    private JTextField maTruongField;
//    private JTextField maNganhField;
//    private JTextField tenTruongField;
//    private JTextField tenNganhField;
//    private JTextField diemSanField;
//
//    // Danh sách yêu thích
//    private DefaultListModel<String> favoriteListModel;
//    private JList<String> favoriteList;
//
//    public static void main(String[] args) {
//        new UniversitySearchUI().init();
//    }
//
//    // Khởi tạo giao diện
//    public void init() {
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/unigoal", "root", "29102004");
//            frame = new JFrame("University Management");
//            frame.setSize(800, 600);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setLayout(new BorderLayout());
//
//            JPanel panel = new JPanel();
//            panel.setLayout(new BorderLayout());
//
//            // Tạo panel tìm kiếm
//            JPanel searchPanel = new JPanel();
//            searchPanel.setLayout(new GridLayout(6, 2));
//            searchPanel.add(new JLabel("Mã Trường:"));
//            maTruongField = new JTextField();
//            searchPanel.add(maTruongField);
//            searchPanel.add(new JLabel("Mã Ngành:"));
//            maNganhField = new JTextField();
//            searchPanel.add(maNganhField);
//            searchPanel.add(new JLabel("Tên Trường:"));
//            tenTruongField = new JTextField();
//            searchPanel.add(tenTruongField);
//            searchPanel.add(new JLabel("Tên Ngành:"));
//            tenNganhField = new JTextField();
//            searchPanel.add(tenNganhField);
//            searchPanel.add(new JLabel("Điểm Sàn:"));
//            diemSanField = new JTextField();
//            searchPanel.add(diemSanField);
//
//            // Nút tìm kiếm
//            JButton searchButton = new JButton("Tìm Kiếm");
//            searchButton.addActionListener(e -> searchUniversityData());
//            searchPanel.add(searchButton);
//
//            panel.add(searchPanel, BorderLayout.NORTH);
//
//            // Hiển thị bảng các trường đại học
//            table = new JTable();
//            JScrollPane scrollPane = new JScrollPane(table);
//            panel.add(scrollPane, BorderLayout.CENTER);
//
//            // Nút thêm vào danh sách yêu thích
//            JButton addToFavoritesButton = new JButton("Thêm vào yêu thích");
//            addToFavoritesButton.addActionListener(e -> addToFavorites());
//            panel.add(addToFavoritesButton, BorderLayout.SOUTH);
//
//            // Danh sách yêu thích
//            favoriteListModel = new DefaultListModel<>();
//            favoriteList = new JList<>(favoriteListModel);
//            favoriteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//            JScrollPane favoriteScrollPane = new JScrollPane(favoriteList);
//            panel.add(favoriteScrollPane, BorderLayout.EAST);
//
//            frame.add(panel);
//            frame.setVisible(true);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
//        }
//    }
//
//    // Tìm kiếm trường đại học theo các tiêu chí
//    public void searchUniversityData() {
//        try {
//            // Sử dụng DISTINCT để tránh lặp lại các trường đại học
//            StringBuilder query = new StringBuilder("SELECT DISTINCT d.maTruong, c.maNganh, d.tenTruong, c.tenNganh, d.diemSan " +
//                    "FROM daihoc d " +
//                    "INNER JOIN chuyennganh c ON d.maTruong = c.maTruong " +
//                    "WHERE 1=1");
//
//            // Thêm điều kiện tìm kiếm cho từng trường hợp
//            if (!maTruongField.getText().isEmpty()) {
//                query.append(" AND d.maTruong LIKE ?");
//            }
//            if (!maNganhField.getText().isEmpty()) {
//                query.append(" AND c.maNganh LIKE ?");
//            }
//            if (!tenTruongField.getText().isEmpty()) {
//                query.append(" AND d.tenTruong LIKE ?");
//            }
//            if (!tenNganhField.getText().isEmpty()) {
//                query.append(" AND c.tenNganh LIKE ?");
//            }
//            if (!diemSanField.getText().isEmpty()) {
//                query.append(" AND d.diemSan >= ?");
//            }
//
//            PreparedStatement stmt = connection.prepareStatement(query.toString());
//
//            int index = 1;
//            if (!maTruongField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + maTruongField.getText() + "%");
//            }
//            if (!maNganhField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + maNganhField.getText() + "%");
//            }
//            if (!tenTruongField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + tenTruongField.getText() + "%");
//            }
//            if (!tenNganhField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + tenNganhField.getText() + "%");
//            }
//            if (!diemSanField.getText().isEmpty()) {
//                stmt.setDouble(index++, Double.parseDouble(diemSanField.getText()));
//            }
//
//            ResultSet rs = stmt.executeQuery();
//            ArrayList<Object[]> data = new ArrayList<>();
//            while (rs.next()) {
//                String maTruong = rs.getString("maTruong");
//                String tenTruong = rs.getString("tenTruong");
//                double diemSan = rs.getDouble("diemSan");
//                data.add(new Object[]{maTruong, tenTruong, diemSan});
//            }
//
//            Object[][] dataArray = new Object[data.size()][3];
//            for (int i = 0; i < data.size(); i++) {
//                dataArray[i] = data.get(i);
//            }
//
//            // Cập nhật bảng với dữ liệu mới
//            table.setModel(new javax.swing.table.DefaultTableModel(
//                    dataArray, new String[]{"Mã Trường", "Tên Trường", "Điểm Sàn"}
//            ));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
//        }
//    }
//
//
//    // Thêm trường đại học vào danh sách yêu thích
//    private void addToFavorites() {
//        int selectedRow = table.getSelectedRow();
//        if (selectedRow != -1) {
//            String maTruong = (String) table.getValueAt(selectedRow, 0);
//            String tenTruong = (String) table.getValueAt(selectedRow, 1);
//            favoriteListModel.addElement(maTruong + " - " + tenTruong);
//        } else {
//            JOptionPane.showMessageDialog(frame, "Hãy chọn một trường để thêm vào yêu thích.");
//        }
//    }
//}
//
//

//package Version2.src.Controller;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//import java.util.ArrayList;
//
//public class UniversitySearchUI {
//    private JFrame frame;
//    private JTable table;
//    private Connection connection;
//    private int pageNumber = 1;
//    private int pageSize = 5;
//
//    // Các trường tìm kiếm
//    private JTextField maTruongField;
//    private JTextField maNganhField;
//    private JTextField tenTruongField;
//    private JTextField tenNganhField;
//    private JTextField diemSanField;
//
//    // Danh sách yêu thích
//    private DefaultListModel<String> favoriteListModel;
//    private JList<String> favoriteList;
//
//    public static void main(String[] args) {
//        new UniversitySearchUI().init();
//    }
//
//    // Khởi tạo giao diện
//    public void init() {
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/unigoal", "root", "29102004");
//            frame = new JFrame("University Management");
//            frame.setSize(800, 600);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setLayout(new BorderLayout());
//
//            JPanel panel = new JPanel();
//            panel.setLayout(new BorderLayout());
//
//            // Tạo panel tìm kiếm
//            JPanel searchPanel = new JPanel();
//            searchPanel.setLayout(new GridLayout(6, 2));
//            searchPanel.add(new JLabel("Mã Trường:"));
//            maTruongField = new JTextField();
//            searchPanel.add(maTruongField);
//            searchPanel.add(new JLabel("Mã Ngành:"));
//            maNganhField = new JTextField();
//            searchPanel.add(maNganhField);
//            searchPanel.add(new JLabel("Tên Trường:"));
//            tenTruongField = new JTextField();
//            searchPanel.add(tenTruongField);
//            searchPanel.add(new JLabel("Tên Ngành:"));
//            tenNganhField = new JTextField();
//            searchPanel.add(tenNganhField);
//            searchPanel.add(new JLabel("Điểm Sàn:"));
//            diemSanField = new JTextField();
//            searchPanel.add(diemSanField);
//
//            // Nút tìm kiếm
//            JButton searchButton = new JButton("Tìm Kiếm");
//            searchButton.addActionListener(e -> searchUniversityData());
//            searchPanel.add(searchButton);
//
//            panel.add(searchPanel, BorderLayout.NORTH);
//
//            // Hiển thị bảng các trường đại học
//            table = new JTable();
////            loadUniversityData();
//            JScrollPane scrollPane = new JScrollPane(table);
//            panel.add(scrollPane, BorderLayout.CENTER);
//
//            // Nút thêm vào danh sách yêu thích
//            JButton addToFavoritesButton = new JButton("Thêm vào yêu thích");
//            addToFavoritesButton.addActionListener(e -> addToFavorites());
//            panel.add(addToFavoritesButton, BorderLayout.SOUTH);
//
//            // Danh sách yêu thích
//            favoriteListModel = new DefaultListModel<>();
//            favoriteList = new JList<>(favoriteListModel);
//            JScrollPane favoriteScrollPane = new JScrollPane(favoriteList);
//            panel.add(favoriteScrollPane, BorderLayout.EAST);
//
//            frame.add(panel);
//            frame.setVisible(true);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
//        }
//    }
//
//
//
//    // Tìm kiếm trường đại học theo các tiêu chí
//    public void searchUniversityData() {
//        try {
//            StringBuilder query = new StringBuilder("SELECT * FROM daihoc WHERE 1=1");
//
//            // Thêm điều kiện tìm kiếm cho từng trường hợp
//            if (!maTruongField.getText().isEmpty()) {
//                query.append(" AND maTruong LIKE ?");
//            }
//            if (!maNganhField.getText().isEmpty()) {
//                query.append(" AND maNganh LIKE ?");
//            }
//            if (!tenTruongField.getText().isEmpty()) {
//                query.append(" AND tenTruong LIKE ?");
//            }
//            if (!tenNganhField.getText().isEmpty()) {
//                query.append(" AND tenNganh LIKE ?");
//            }
//            if (!diemSanField.getText().isEmpty()) {
//                query.append(" AND diemSan >= ?");
//            }
//
//            PreparedStatement stmt = connection.prepareStatement(query.toString());
//
//            int index = 1;
//            if (!maTruongField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + maTruongField.getText() + "%");
//            }
//            if (!maNganhField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + maNganhField.getText() + "%");
//            }
//            if (!tenTruongField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + tenTruongField.getText() + "%");
//            }
//            if (!tenNganhField.getText().isEmpty()) {
//                stmt.setString(index++, "%" + tenNganhField.getText() + "%");
//            }
//            if (!diemSanField.getText().isEmpty()) {
//                stmt.setDouble(index++, Double.parseDouble(diemSanField.getText()));
//            }
//
//            ResultSet rs = stmt.executeQuery();
//            ArrayList<Object[]> data = new ArrayList<>();
//            while (rs.next()) {
//                String maTruong = rs.getString("maTruong");
//                String tenTruong = rs.getString("tenTruong");
//                double diemSan = rs.getDouble("diemSan");
//                data.add(new Object[]{maTruong, tenTruong, diemSan});
//            }
//
//            Object[][] dataArray = new Object[data.size()][3];
//            for (int i = 0; i < data.size(); i++) {
//                dataArray[i] = data.get(i);
//            }
//            table.setModel(new javax.swing.table.DefaultTableModel(
//                    dataArray, new String[]{"Mã Trường", "Tên Trường", "Điểm Sàn"}
//            ));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
//        }
//    }
//
//    // Thêm trường đại học vào danh sách yêu thích
//    private void addToFavorites() {
//        int selectedRow = table.getSelectedRow();
//        if (selectedRow != -1) {
//            String maTruong = (String) table.getValueAt(selectedRow, 0);
//            String tenTruong = (String) table.getValueAt(selectedRow, 1);
//            favoriteListModel.addElement(maTruong + " - " + tenTruong);
//        } else {
//            JOptionPane.showMessageDialog(frame, "Hãy chọn một trường để thêm vào yêu thích.");
//        }
//    }
//}

