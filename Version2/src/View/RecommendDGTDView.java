package Version2.src.View;

import Version2.src.Controller.RecommendDGTDController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RecommendDGTDView extends JPanel {
    private final JTextField textNhapDiem;
    private final JComboBox<String> comboBoxChonNganh;
    private final JTable bangGoiY;
    private final JButton goiYTruongDaiHoc;
    private final RecommendDGTDController controller;

    public RecommendDGTDView() throws Exception {
        controller = new RecommendDGTDController();
        setLayout(new BorderLayout());

        // Khởi tạo giao diện
        textNhapDiem = new JTextField(10);
        comboBoxChonNganh = new JComboBox<>();
        goiYTruongDaiHoc = new JButton("Gợi ý trường đại học");
        bangGoiY = new JTable(new DefaultTableModel(new String[]{"Mã Trường", "Tên Trường", "Tên Ngành", "Điểm Đánh Giá Tư Duy"}, 0));

        setupUI();
        setupEvents();

        // Tải dữ liệu ngành thông qua controller
        controller.loadNganhData(comboBoxChonNganh);
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

                // Gọi controller để lấy dữ liệu
                controller.getUniversitySuggestionsByDGTYDiem(diem, selectedNganh, bangGoiY);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ!");
            }
        });
    }

    public JPanel getPanel() {
        return this;
    }
}
