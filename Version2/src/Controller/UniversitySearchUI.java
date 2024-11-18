package Version2.src.Controller;

import Version2.src.Model.*;
import Version2.src.Utils.DatabaseConnection;
import Version2.src.Utils.JButtonConfig;
import Version2.src.Utils.NonEditableTableModel;

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

    private JTextField maTruongField, maNganhField, tenTruongField, tenNganhField, diemSanField;
    private DefaultListModel<FavoriteItem> favoriteListModel;
    private JList<FavoriteItem> favoriteList;

    private boolean isSearchMode = false;
    private int searchPageNumber = 1;
    private int searchPageSize = 10;

    public void init() {
        try {
            connection = DatabaseConnection.getConnection();

            frame = new JFrame("University Management");
            frame.setSize(900, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel mainPanel = createMainPanel();
            frame.add(mainPanel);
            frame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(createSearchPanel(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "Tìm Kiếm",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                Color.BLUE
        ));

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
        JButton searchButton = new JButton("Tìm kiếm", new ImageIcon("Version2/src/Icons/icons8-search-15.png"));
        searchButton.addActionListener(e -> searchUniversityData());
        searchPanel.add(new JLabel());
        searchPanel.add(searchButton);

        return searchPanel;
    }

    private JScrollPane createTablePanel() {
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

        // load du lieu cac truong dai hoc
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
        return tableScrollPane;
    }

    // Panel phân trang và yêu thích
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        // Pagination
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 3));
        JButton prevButton = new JButton(new ImageIcon("Version2/src/Icons/icons8-chevron-left-25.png"));
        JButton nextButton = new JButton(new ImageIcon("Version2/src/Icons/icons8-chevron-right-25.png"));

        prevButton.addActionListener(e -> prevPage());
        nextButton.addActionListener(e -> nextPage());

        paginationPanel.add(prevButton);
        paginationPanel.add(nextButton);

        // Nút thêm vào yêu thích
        JButtonConfig addToFavoritesButton = new JButtonConfig("Thêm vào yêu thích", new ImageIcon("Version2/src/Icons/icons8-add-properties-15.png"));
        addToFavoritesButton.addActionListener(e -> addToFavorites());

        bottomPanel.add(paginationPanel, BorderLayout.NORTH);
        bottomPanel.add(addToFavoritesButton, BorderLayout.SOUTH);

        // Favorite List
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

        return bottomPanel;
    }

    private void loadUniversityData() {
        isSearchMode = false; // Quay lại chế độ hiển thị toàn bộ
        try {
            String query = "SELECT * FROM daihoc LIMIT ?, ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, (pageNumber - 1) * pageSize);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();
            ArrayList<DaiHocLoadData> data = new ArrayList<>();
            while (rs.next()) {
                DaiHocLoadData daiHocLoadData = new DaiHocLoadData("", "",0);
                daiHocLoadData.setMaTruong(rs.getString("maTruong"));
                daiHocLoadData.setTenTruong(rs.getString("tenTruong"));
                daiHocLoadData.setDiemSan(rs.getDouble("diemSan"));
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
            table.setModel(model);

            // Thêm sắp xếp cho bảng
            TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }

    private void searchUniversityData() {
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
            ArrayList<DaiHocSearchResult> data = new ArrayList<>();
            while (rs.next()) {
                DaiHocSearchResult daiHocSearchResult = new DaiHocSearchResult("", "",0,"");
                daiHocSearchResult.setMaTruong(rs.getString("maTruong"));
                daiHocSearchResult.setTenTruong(rs.getString("tenTruong"));
                daiHocSearchResult.setDiemSan(rs.getDouble("diemSan"));
                daiHocSearchResult.setTenNganh(rs.getString("tenNganh"));
                data.add(daiHocSearchResult);
            }
            Object[][] dataArray = new Object[data.size()][4];
            for (int i = 0; i < data.size(); i++) {
                DaiHocSearchResult daiHocSearchResult = data.get(i);
                dataArray[i][0] = daiHocSearchResult.getMaTruong();
                dataArray[i][1] = daiHocSearchResult.getTenTruong();
                dataArray[i][2] = daiHocSearchResult.getDiemSan();
                dataArray[i][3] = daiHocSearchResult.getTenNganh();
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


