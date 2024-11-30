package Version2.src.View;

import Version2.src.Controller.RecommendDGTDController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static Version2.src.Utils.Constants.SEARCH_ICON_PATH;

public class RecommendDGTDView extends JPanel {
    private final JTextField textNhapDiem;
    private final JComboBox<String> comboBoxChonNganh;
    private final JTable bangGoiY;
    private final JButton goiYTruongDaiHoc;
    private final RecommendDGTDController controller;
    private final JComboBox<String> comboBoxChonToHopMon;

    public RecommendDGTDView() throws Exception {
        controller = new RecommendDGTDController();
        setLayout(new BorderLayout());

        // Khởi tạo giao diện
        textNhapDiem = new JTextField(10);
        comboBoxChonNganh = new JComboBox<>();
        comboBoxChonToHopMon = new JComboBox<>(new String[]{"Tổ Hợp Mặc Định", "Tổ Hợp K00"});
        goiYTruongDaiHoc = new JButton("Gợi ý trường đại học",new ImageIcon(SEARCH_ICON_PATH));
        goiYTruongDaiHoc.setBackground(new Color(0,123,255));
        goiYTruongDaiHoc.setForeground(Color.WHITE);
        goiYTruongDaiHoc.setPreferredSize(new Dimension(200,40));
        goiYTruongDaiHoc.setFocusPainted(false);
        goiYTruongDaiHoc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        goiYTruongDaiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                goiYTruongDaiHoc.setBackground(new Color(0, 105, 217));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                goiYTruongDaiHoc.setBackground(new Color(0, 123, 255));
            }
        });

        bangGoiY = new JTable(new DefaultTableModel(
                new String[]{"Mã Trường", "Tên Trường", "Tên Ngành", "Tổ Hợp Môn", "Điểm Đánh Giá Tư Duy"}, 0));
        bangGoiY.setRowHeight(25);
        bangGoiY.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        bangGoiY.getTableHeader().setBackground(Color.LIGHT_GRAY);
        bangGoiY.setSelectionBackground(Color.YELLOW);
        bangGoiY.setSelectionForeground(Color.BLACK);
        bangGoiY.setAutoCreateRowSorter(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        bangGoiY.setDefaultRenderer(Object.class, centerRenderer);
        setupUI();
        setupEvents();

        // Tải dữ liệu ngành
        controller.loadNganhData(comboBoxChonNganh);

        // Hiển thị toàn bộ trường khi mở ứng dụng
        controller.loadAllUniversitySuggestions(bangGoiY);
    }

    private void setupUI() {
        JPanel topPanel = new JPanel(new GridLayout(3, 2));
        topPanel.add(new JLabel("Nhập điểm:"));
        topPanel.add(textNhapDiem);
        topPanel.add(new JLabel("Chọn ngành:"));
        topPanel.add(comboBoxChonNganh);
        topPanel.add(new JLabel("Chọn tổ hợp môn:"));
        topPanel.add(comboBoxChonToHopMon);
        getPanel().setPreferredSize(new Dimension(1500, 750)); // Ví dụ kích thước

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(bangGoiY), BorderLayout.CENTER);
        add(goiYTruongDaiHoc, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        goiYTruongDaiHoc.addActionListener(e -> {
            try {
                int diem = Integer.parseInt(textNhapDiem.getText());
                String selectedNganh = (String) comboBoxChonNganh.getSelectedItem();
                String selectedToHopMon = comboBoxChonToHopMon.getSelectedItem().equals("Tổ Hợp Mặc Định") ? "0" : "K00";

                // Gọi controller để lấy dữ liệu
                controller.getUniversitySuggestionsByDGTD(diem, selectedNganh, selectedToHopMon, bangGoiY);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ!");
            }
        });
    }

    public JPanel getPanel() {
        return this;
    }
}
