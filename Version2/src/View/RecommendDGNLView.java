package Version2.src.View;

import Version2.src.Controller.RecommendDGNLController;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class RecommendDGNLView extends JPanel {
    private final JTextField textNhapDiem;
    private final JComboBox<String> comboBoxChonNganh;
    private final JTable bangGoiY;
    private final JButton goiYTruongDaiHoc;
    private final RecommendDGNLController controller;

    public RecommendDGNLView() throws Exception {
        controller = new RecommendDGNLController();
        setLayout(new BorderLayout());

        // Khởi tạo giao diện
        textNhapDiem = new JTextField(10);
        comboBoxChonNganh = new JComboBox<>();
        goiYTruongDaiHoc = new JButton("Gợi ý trường đại học");
        bangGoiY = new JTable(new DefaultTableModel(
                new String[]{"Mã Trường", "Tên Trường", "Tên Ngành", "Điểm Đánh Giá Năng Lực"}, 0));
        bangGoiY.setAutoCreateRowSorter(true);
        setupUI();
        setupEvents();

        // Tải dữ liệu ngành
        controller.loadNganhData(comboBoxChonNganh);

        // Hiển thị toàn bộ trường khi mở ứng dụng
        controller.loadAllUniversitySuggestions(bangGoiY);
    }

    private void setupUI() {
        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(new JLabel("Nhập điểm:"));
        topPanel.add(textNhapDiem);
        topPanel.add(new JLabel("Chọn ngành:"));
        topPanel.add(comboBoxChonNganh);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(bangGoiY), BorderLayout.CENTER);
        add(goiYTruongDaiHoc, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        goiYTruongDaiHoc.addActionListener(e -> {
            try {
                int diem = Integer.parseInt(textNhapDiem.getText());
                String selectedNganh = (String) comboBoxChonNganh.getSelectedItem();

                // Gọi controller để lấy dữ liệu gợi ý
                controller.getUniversitySuggestionsByDGNL(diem, selectedNganh, bangGoiY);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ!");
            }
        });
    }

    public JPanel getPanel() {
        return this;
    }
}









