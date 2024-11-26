package Version2.src.View;

import Version2.src.Model.FavoriteItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;

public class UniversitySearchView {
    private JTable table;
    private JTextField maTruongField, maNganhField, tenTruongField, tenNganhField, diemTrungTuyenField;
    private DefaultListModel<FavoriteItem> favoriteListModel;
    private JList<FavoriteItem> favoriteList;

    // Declare buttons as member variables
    private JButton searchButton;
    private JButton prevButton;
    private JButton nextButton;

    public UniversitySearchView() {
        table = new JTable();
        favoriteListModel = new DefaultListModel<>();
        favoriteList = new JList<>(favoriteListModel);
    }

    public JPanel getPanel() {
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
        diemTrungTuyenField = new JTextField();

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

        // Initialize the search button
        searchButton = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel());
        searchPanel.add(searchButton);

        return searchPanel;
    }

    private JScrollPane createTablePanel() {
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);
        table.setSelectionBackground(Color.YELLOW);
        table.setSelectionForeground(Color.BLACK);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

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

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 3));

        // Initialize the previous and next buttons
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        paginationPanel.add(prevButton);
        paginationPanel.add(nextButton);

        bottomPanel.add(paginationPanel, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(favoriteList), BorderLayout.CENTER);

        return bottomPanel;
    }

    public void setTableModel(TableRowSorter sorter) {
        table .setRowSorter(sorter);
    }

    public String getMaTruongField() {
        return maTruongField.getText();
    }

    public String getMaNganhField() {
        return maNganhField.getText();
    }

    public String getTenTruongField() {
        return tenTruongField.getText();
    }

    public String getTenNganhField() {
        return tenNganhField.getText();
    }

    public String getDiemTrungTuyenField() {
        return diemTrungTuyenField.getText();
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addPrevButtonListener(ActionListener listener) {
        prevButton.addActionListener(listener);
    }

    public void addNextButtonListener(ActionListener listener) {
        nextButton.addActionListener(listener);
    }

    public void addToFavorites(FavoriteItem item) {
        favoriteListModel.addElement(item);
    }

    public JTable getTable() {
        return table;
    }
}