package Version2.src.View;

import Version2.src.Controller.UniversitySearchController;
import Version2.src.Model.DaiHocSearchResult;
import Version2.src.Model.FavoriteItem;
import Version2.src.Utils.JButtonConfig;
import Version2.src.Utils.NonEditableTableModel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import static Version2.src.Utils.Constants.ADD_TO_FAVORITES_ICON_PATH;
import static Version2.src.Utils.Constants.SEARCH_ICON_PATH;

public class UniversitySearchView extends JPanel {
    private final JTextField maTruongField, maNganhField, tenTruongField, tenNganhField, diemTrungTuyenField;
    private final JTable universityTable;
    private final JButton searchButton;
    private JButtonConfig addToFavoritesButton;
    private DefaultListModel<FavoriteItem> favoriteListModel;
    private JList<FavoriteItem> favoriteList;
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
        searchButton = new JButton("Tìm kiếm", new ImageIcon(SEARCH_ICON_PATH));

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
        universityTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        universityTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        // khởi tạo giao diện
        setupUI();
        setupEvents();

        // Tải dữ liệu trường đại học khi mở ứng dụng
        controller.loadUniversityData(universityTable);
    }

    private void setupUI() {
        // Tạo panel tìm kiếm
        JPanel searchPanel = new JPanel(new GridLayout(6, 2, 5, 5));
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
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình tìm kiếm!");
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
