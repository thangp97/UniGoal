package Version2.src.Controller;

import Version2.src.Model.FavoriteItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class UniversitySearchUI {
    private JFrame frame;
    private JTable table;
    private Connection connection;
    private int pageNumber = 1;
    private int pageSize = 10;

    // Các trường tìm kiếm
    private JTextField maTruongField;
    private JTextField maNganhField;
    private JTextField tenTruongField;
    private JTextField tenNganhField;
    private JTextField diemSanField;

    // Danh sách yêu thích
    private DefaultListModel<FavoriteItem> favoriteListModel;
    private JList<FavoriteItem> favoriteList;

    private boolean isSearchMode = false; // Chế độ tìm kiếm hay không
    private int searchPageNumber = 1; // Trang hiện tại trong chế độ tìm kiếm
    private int searchPageSize = 10; // Kích thước trang trong chế độ tìm kiếm




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UniversitySearchUI().init());
    }

    private static class NonEditableTableModel extends javax.swing.table.DefaultTableModel {
        public NonEditableTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép chỉnh sửa bất kỳ ô nào
        }
    }

    public void init() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/unigoal", "root", "29102004");

            // Thiết lập frame chính
            frame = new JFrame("University Management");
            frame.setSize(900, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Panel tìm kiếm
            JPanel searchPanel = new JPanel(new GridLayout(6, 2, 5, 5));
            searchPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1),
                    "Tìm Kiếm",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Arial", Font.BOLD, 14),
                    Color.BLUE
            ));

            // Các trường tìm kiếm
            maTruongField = new JTextField();
            maNganhField = new JTextField();
            tenTruongField = new JTextField();
            tenNganhField = new JTextField();
            diemSanField = new JTextField();

            searchPanel.add(new JLabel("Mã Trường:"));
            searchPanel.add(maTruongField);
            searchPanel.add(new JLabel("Mã Ngành:"));
            searchPanel.add(maNganhField);
            searchPanel.add(new JLabel("Tên Trường:"));
            searchPanel.add(tenTruongField);
            searchPanel.add(new JLabel("Tên Ngành:"));
            searchPanel.add(tenNganhField);
            searchPanel.add(new JLabel("Điểm Sàn:"));
            searchPanel.add(diemSanField);

            // Nút tìm kiếm
            JButton searchButton = new JButton("Tìm Kiếm", new ImageIcon("Version2/src/Icons/icons8-search-15.png"));
            searchButton.setBackground(Color.BLUE);
            searchButton.setForeground(Color.WHITE);
            searchButton.setFont(new Font("Arial", Font.BOLD, 14));
            searchButton.addActionListener(e -> searchUniversityData());
            searchPanel.add(new JLabel());
            searchPanel.add(searchButton);

            mainPanel.add(searchPanel, BorderLayout.NORTH);

            // Hiển thị bảng
            table = new JTable();
            table.setRowHeight(25);
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
            table.getTableHeader().setBackground(Color.LIGHT_GRAY);
            table.setSelectionBackground(Color.YELLOW);
            table.setSelectionForeground(Color.BLACK);

            // Căn giữa dữ liệu trong bảng
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            table.setDefaultRenderer(Object.class, centerRenderer);

            loadUniversityData();
            JScrollPane tableScrollPane = new JScrollPane(table);
            tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1),
                    "Danh Sách Trường Đại Học",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Arial", Font.BOLD, 14),
                    Color.BLUE
            ));
            mainPanel.add(tableScrollPane, BorderLayout.CENTER);

            // Panel phân trang và yêu thích
            JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

            // Panel phân trang
            JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 3));
            JButton prevButton = new JButton(new ImageIcon("Version2/src/Icons/icons8-chevron-left-25.png"));
            JButton nextButton = new JButton(new ImageIcon("Version2/src/Icons/icons8-chevron-right-25.png"));

            prevButton.addActionListener(e -> prevPage());
            nextButton.addActionListener(e -> nextPage());

            paginationPanel.add(prevButton);
            paginationPanel.add(nextButton);

            // Nút thêm vào yêu thích
            JButton addToFavoritesButton = new JButton("Thêm vào yêu thích", new ImageIcon("favorite_icon.png"));
            addToFavoritesButton.setFont(new Font("Arial", Font.BOLD, 12));
            addToFavoritesButton.setBackground(Color.ORANGE);
            addToFavoritesButton.setForeground(Color.WHITE);
            addToFavoritesButton.addActionListener(e -> addToFavorites());

            bottomPanel.add(paginationPanel, BorderLayout.NORTH);
            bottomPanel.add(addToFavoritesButton, BorderLayout.SOUTH);

            // Danh sách yêu thích
//            favoriteListModel = new DefaultListModel<>();
//            favoriteList = new JList<>(favoriteListModel);
//            favoriteList.setBorder(BorderFactory.createTitledBorder(
//                    BorderFactory.createLineBorder(Color.GRAY, 1),
//                    "Danh Sách Yêu Thích",
//                    TitledBorder.DEFAULT_JUSTIFICATION,
//                    TitledBorder.DEFAULT_POSITION,
//                    new Font("Arial", Font.BOLD, 14),
//                    Color.BLUE
//            ));
//            bottomPanel.add(new JScrollPane(favoriteList), BorderLayout.CENTER);

            // Danh sách yêu thích sử dụng DefaultListModel<FavoriteItem>
            favoriteListModel = new DefaultListModel<>();
            favoriteList = new JList<>(favoriteListModel);
            favoriteList.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1),
                    "Danh Sách Yêu Thích",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Arial", Font.BOLD, 14),
                    Color.BLUE
            ));
            bottomPanel.add(new JScrollPane(favoriteList), BorderLayout.CENTER);


            mainPanel.add(bottomPanel, BorderLayout.SOUTH);

            frame.add(mainPanel);
            frame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }

    public void loadUniversityData() {
        isSearchMode = false; // Quay lại chế độ hiển thị toàn bộ
        try {
            String query = "SELECT * FROM daihoc LIMIT ?, ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, (pageNumber - 1) * pageSize);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Object[]> data = new ArrayList<>();
            while (rs.next()) {
                String maTruong = rs.getString("maTruong");
                String tenTruong = rs.getString("tenTruong");
                double diemSan = rs.getDouble("diemSan");
                data.add(new Object[]{maTruong, tenTruong, diemSan});
            }
            Object[][] dataArray = new Object[data.size()][3];
            for (int i = 0; i < data.size(); i++) {
                dataArray[i] = data.get(i);
            }
            NonEditableTableModel model = new NonEditableTableModel(
                    dataArray, new String[]{"Mã Trường", "Tên Trường", "Điểm Sàn"}
            );
            table.setModel(model);

            // Thêm sắp xếp cho bảng
            TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }



    public void searchUniversityData() {
        isSearchMode = true; // Chuyển sang chế độ tìm kiếm
        try {
            StringBuilder query = new StringBuilder(
                    "SELECT d.maTruong, d.tenTruong, d.diemSan, c.tenNganh " +
                            "FROM daihoc d " +
                            "INNER JOIN chuyennganh c ON d.maTruong = c.maTruong " +
                            "WHERE 1=1"
            );

            if (!maTruongField.getText().isEmpty()) {
                query.append(" AND d.maTruong LIKE ?");
            }
            if (!maNganhField.getText().isEmpty()) {
                query.append(" AND c.maNganh LIKE ?");
            }
            if (!tenTruongField.getText().isEmpty()) {
                query.append(" AND d.tenTruong LIKE ?");
            }
            if (!tenNganhField.getText().isEmpty()) {
                query.append(" AND c.tenNganh LIKE ?");
            }
            if (!diemSanField.getText().isEmpty()) {
                query.append(" AND d.diemSan >= ?");
            }

            query.append(" LIMIT ?, ?");

            PreparedStatement stmt = connection.prepareStatement(query.toString());
            int index = 1;
            if (!maTruongField.getText().isEmpty()) {
                stmt.setString(index++, "%" + maTruongField.getText() + "%");
            }
            if (!maNganhField.getText().isEmpty()) {
                stmt.setString(index++, "%" + maNganhField.getText() + "%");
            }
            if (!tenTruongField.getText().isEmpty()) {
                stmt.setString(index++, "%" + tenTruongField.getText() + "%");
            }
            if (!tenNganhField.getText().isEmpty()) {
                stmt.setString(index++, "%" + tenNganhField.getText() + "%");
            }
            if (!diemSanField.getText().isEmpty()) {
                stmt.setDouble(index++, Double.parseDouble(diemSanField.getText()));
            }

            stmt.setInt(index++, (searchPageNumber - 1) * searchPageSize);
            stmt.setInt(index, searchPageSize);

            ResultSet rs = stmt.executeQuery();
            ArrayList<Object[]> data = new ArrayList<>();
            while (rs.next()) {
                String maTruong = rs.getString("maTruong");
                String tenTruong = rs.getString("tenTruong");
                double diemSan = rs.getDouble("diemSan");
                String tenNganh = rs.getString("tenNganh");
                data.add(new Object[]{maTruong, tenTruong, diemSan, tenNganh});
            }
            Object[][] dataArray = new Object[data.size()][4];
            for (int i = 0; i < data.size(); i++) {
                dataArray[i] = data.get(i);
            }
            NonEditableTableModel model = new NonEditableTableModel(
                    dataArray, new String[]{"Mã Trường", "Tên Trường", "Điểm Sàn", "Tên Ngành"}
            );
            table.setModel(model);

            // Thêm sắp xếp cho bảng
            TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }

    // Chuyển trang trước
    private void prevPage() {
        if (isSearchMode) {
            if (searchPageNumber > 1) {
                searchPageNumber--;
                searchUniversityData();
            }
        } else {
            if (pageNumber > 1) {
                pageNumber--;
                loadUniversityData();
            }
        }
    }

    // Chuyển trang tiếp
    private void nextPage() {
        if (isSearchMode) {
            searchPageNumber++;
            searchUniversityData();
        } else {
            pageNumber++;
            loadUniversityData();
        }
    }

    private void addToFavorites() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maTruong = (String) table.getValueAt(selectedRow, 0); // Cột 0
            String tenTruong = (String) table.getValueAt(selectedRow, 1); // Cột 1
            String tenNganh = (String) table.getValueAt(selectedRow, 3);
            double diemSan = (double) table.getValueAt(selectedRow, 2);   // Cột 2

            // Nếu bạn cần thêm cột khác, hãy chắc chắn chỉ số cột hợp lệ.
            FavoriteItem favoriteItem = new FavoriteItem(maTruong, tenTruong, tenNganh, diemSan);
            favoriteListModel.addElement(favoriteItem);
        } else {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn một trường để thêm vào yêu thích.");
        }
    }

}
