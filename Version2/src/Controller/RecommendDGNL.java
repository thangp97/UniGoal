package Version2.src.Controller;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.text.DecimalFormat;

public class RecommendDGNL extends JPanel {
    private Connection connection;
    private JTextField textNhapDiem;
    private JComboBox<String> comboBoxChonNganh;
    private JTable bangGoiY;
    private JButton goiYTruongDaiHoc;

    public RecommendDGNL(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());

        // Khởi tạo các thành phần giao diện
        textNhapDiem = new JTextField(10);
        comboBoxChonNganh = new JComboBox<>();
        goiYTruongDaiHoc = new JButton("Gợi ý trường đại học");
        bangGoiY = new JTable(new DefaultTableModel(new String[]{"Mã Trường", "Tên Trường", "Tên Ngành", "Điểm Đánh Giá Năng Lực"}, 0));

        // Panel cho đầu trang
        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(new JLabel("Nhập điểm:"));
        topPanel.add(textNhapDiem);
        topPanel.add(new JLabel("Chọn ngành:"));
        topPanel.add(comboBoxChonNganh);

        // Thêm các thành phần vào JPanel
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(bangGoiY), BorderLayout.CENTER);
        add(goiYTruongDaiHoc, BorderLayout.SOUTH);

        // Sự kiện nút gợi ý
        goiYTruongDaiHoc.addActionListener(e -> {
            try {
                int diem = Integer.parseInt(textNhapDiem.getText());
                String selectedNganh = (String) comboBoxChonNganh.getSelectedItem();
                getUniversitySuggestionsByDGNLDiem(diem, selectedNganh);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ!");
            }
        });

        loadNganhData();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) bangGoiY.getModel());
        bangGoiY.setRowSorter(sorter);  // Áp dụng sorter vào bảng
    }

    private void loadNganhData() {
        try {
            String sql = "SELECT DISTINCT c.tenNganh FROM chuyennganh c JOIN dgnl d ON c.maNganh = d.maNganh";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Xóa các item cũ trong comboBox
            comboBoxChonNganh.removeAllItems();

            while (resultSet.next()) {
                String tenNganh = resultSet.getString("tenNganh");
                comboBoxChonNganh.addItem(tenNganh);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading ngành data: " + e.getMessage());
        }
    }

    private void getUniversitySuggestionsByDGNLDiem(int diem, String selectedNganh) {
        try {
            String sql = "SELECT DISTINCT t.maTruong, t.tenTruong, c.tenNganh, d.diemChuan " +
                    "FROM truongdaihoc t " +
                    "JOIN dgnl d ON t.maTruong = d.maTruong " +
                    "JOIN chuyennganh c ON d.maNganh = c.maNganh " +
                    "WHERE c.tenNganh = ? AND d.diemChuan <= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedNganh);
            preparedStatement.setInt(2, diem);

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) bangGoiY.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            DecimalFormat df = new DecimalFormat("#.##"); // Định dạng số double (2 chữ số thập phân)

            boolean hasResults = false;
            while (resultSet.next()) {
                String maTruong = resultSet.getString("maTruong");
                String tenTruong = resultSet.getString("tenTruong");
                String tenNganh = resultSet.getString("tenNganh");
                double diemChuan = resultSet.getDouble("diemChuan");

                // Chỉ thêm trường vào bảng nếu điểm của người dùng >= điểm chuẩn
                if (diem >= diemChuan) {
                    model.addRow(new Object[]{
                            maTruong, tenTruong, tenNganh, df.format(diemChuan)
                    });
                    hasResults = true;
                }
            }

            if (!hasResults) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy trường phù hợp với điểm của bạn.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
}
