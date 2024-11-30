package Version2.src.View;

import Version2.src.Controller.UniversitySearchController;
import Version2.src.Model.DaiHocFavoriteData;
import Version2.src.Utils.JButtonConfig;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import static Version2.src.Utils.Constants.ADD_TO_FAVORITES_ICON_PATH;
import static Version2.src.Utils.Constants.EXPORT_ICON_PATH;
import static Version2.src.Utils.Constants.SEARCH_ICON_PATH;

public class UniversitySearchView extends JPanel {
    private final JTextField maTruongField, maNganhField, tenTruongField, tenNganhField, diemTrungTuyenField;
    private final JTable universityTable;
    private JButton searchButton, exportFavoritesButton;
    private JButtonConfig addToFavoritesButton;
    private DefaultListModel<DaiHocFavoriteData> favoriteListModel;
    private JList<DaiHocFavoriteData> favoriteList;
    private final UniversitySearchController controller;

    public UniversitySearchView() throws SQLException {
        controller = new UniversitySearchController();
        setLayout(new BorderLayout());

        // Khởi tạo giao diện
        maTruongField = new JTextField(10);
        maNganhField = new JTextField(10);
        tenTruongField = new JTextField(10);
        tenNganhField = new JTextField(10);
        diemTrungTuyenField = new JTextField(10);

        universityTable = new JTable(new DefaultTableModel(
                new String[]{"Mã Trường", "Tên Trường", "Điểm Sàn"}, 0));
        universityTable.setRowHeight(25);
        universityTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        universityTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        universityTable.setSelectionBackground(Color.YELLOW);
        universityTable.setSelectionForeground(Color.BLACK);
        universityTable.setAutoCreateRowSorter(true);

        // Căn giữa dữ liệu trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        universityTable.setDefaultRenderer(Object.class, centerRenderer);

        // Khởi tạo giao diện
        setupUI();
        setupEvents();

        // Tải dữ liệu trường đại học khi mở ứng dụng
        controller.loadUniversityData(universityTable);
    }

    private void setupUI() {
        // Tạo panel tìm kiếm
//        JPanel searchPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JPanel searchPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "Tìm Kiếm Trường Đại Học",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                Color.BLUE
        ));

        searchPanel.add(new JLabel("Mã Trường:"));
        searchPanel.add(maTruongField);
        searchPanel.add(new JLabel("Mã Ngành:"));
        searchPanel.add(maNganhField);
        searchPanel.add(new JLabel("Tên Trường:"));
        searchPanel.add(tenTruongField);
        searchPanel.add(new JLabel("Tên Ngành:"));
        searchPanel.add(tenNganhField);
        searchPanel.add(new JLabel("Điểm Trúng Tuyển:"));
        searchPanel.add(diemTrungTuyenField);
        searchButton = new JButton("Tìm kiếm", new ImageIcon(SEARCH_ICON_PATH));
        searchButton.setBackground(new Color(0, 123, 255));
        searchButton.setForeground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(200, 40));
        searchButton.setFocusPainted(false);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchPanel.add(new JLabel());
        searchPanel.add(searchButton);

        // Bảng kết quả tìm kiếm
        JScrollPane tableScrollPane = new JScrollPane(universityTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "Danh Sách Trường Đại Học",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                Color.BLUE
        ));

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        addToFavoritesButton = new JButtonConfig("Thêm vào yêu thích", new ImageIcon(ADD_TO_FAVORITES_ICON_PATH));
        exportFavoritesButton = new JButton("Tải xuống danh sách yêu thích", new ImageIcon(EXPORT_ICON_PATH));
        exportFavoritesButton.setBackground(new Color(40, 167, 69));
        exportFavoritesButton.setForeground(Color.WHITE);
        exportFavoritesButton.setFocusPainted(false);
        exportFavoritesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        bottomPanel.add(addToFavoritesButton, BorderLayout.NORTH);
        bottomPanel.add(exportFavoritesButton, BorderLayout.SOUTH);

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

        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        searchButton.addActionListener(e -> {
            try {
                controller.searchUniversityData(maTruongField.getText(), maNganhField.getText(),
                        tenTruongField.getText(), tenNganhField.getText(), diemTrungTuyenField.getText(), universityTable);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình tìm kiếm!");
            }
        });

        addToFavoritesButton.addActionListener(e -> {
            try {
                controller.addToFavorites(universityTable, favoriteListModel);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình thêm vào yêu thích!");
            }
        });

        exportFavoritesButton.addActionListener(e -> {
            try {
                controller.exportFavoritesToCSV(favoriteListModel);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình xuất danh sách yêu thích!");
            }
        });
    }

    public JPanel getPanel() {
        return this;
    }

    public UniversitySearchController getController() {
        return controller;
    }
}
