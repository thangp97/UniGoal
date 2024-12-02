package Version2.src.View;

import Version2.src.Controller.RecommendTHPTController;
import Version2.src.Model.THPTScoreResult;
import Version2.src.Utils.NonEditableTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static Version2.src.Utils.Constants.SEARCH_ICON_PATH;

public class RecommendTHPTView extends JPanel {
    private final JTextField textNhapDiem1;
    private final JTextField textNhapDiem2;
    private final JTextField textNhapDiem3;
    private final JLabel DiemXetTuyen = new JLabel("Điểm Xét Tuyển:");
    private final JLabel labelMon1 = new JLabel("Môn 1:");
    private final JLabel labelMon2 = new JLabel("Môn 2:");
    private final JLabel labelMon3 = new JLabel("Môn 3:");
    private final JComboBox<String> comboBoxChonNganh;
    private final JTable bangGoiY;
    private final JButton goiYTruongDaiHoc;
    private final RecommendTHPTController controller;
    private final JComboBox<String> comboBoxChonToHopMon;
    private final JComboBox<String> comboBoxKhuVuc;
    private final JComboBox<String> comboBoxDoiTuongUuTien;

    public RecommendTHPTView() throws Exception {
        controller = new RecommendTHPTController();
        setLayout(new BorderLayout());

        // Khởi tạo giao diện
        textNhapDiem1 = new JTextField(10);
        textNhapDiem2 = new JTextField(10);
        textNhapDiem3 = new JTextField(10);
        comboBoxChonNganh = new JComboBox<>();
        comboBoxChonToHopMon = new JComboBox<>(new String[]{"Tổ Hợp Mặc Định"});
        comboBoxKhuVuc = new JComboBox<>(new String[]{"KV1", "KV2-NT", "KV2", "KV3"});
        comboBoxDoiTuongUuTien = new JComboBox<>(new String[]{
                "Không có ưu tiên",
                "Đối tượng 1",
                "Đối tượng 2",
                "Đối tượng 3",
                "Đối tượng 4",
                "Đối tượng 5",
                "Đối tượng 6",
                "Đối tượng 7"
        });
        goiYTruongDaiHoc = new JButton("Tính toán điểm xét tuyển và gợi ý trường đại học",new ImageIcon(SEARCH_ICON_PATH));
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

        bangGoiY = new JTable(new NonEditableTableModel(
                new Object[][]{}, // Dữ liệu mặc định trống
                new String[]{"Mã Trường", "Tên Trường", "Tên Ngành", "Tổ Hợp Môn", "Điểm THPT"} // Tiêu đề cột
        ));
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
        controller.loadToHopMonToComboBox(comboBoxChonToHopMon);
        controller.loadNganhData(comboBoxChonNganh);
        controller.loadAllUniversitySuggestions(bangGoiY);
    }

    private void setupUI() {
        JPanel topPanel = new JPanel(new GridLayout(4, 2));
        topPanel.add(new JLabel("Chọn tổ hợp môn:"));
        topPanel.add(comboBoxChonToHopMon);

        topPanel.add(new JLabel("Đối tượng ưu tiên:")); // Thêm nhãn Đối tượng ưu tiên
        topPanel.add(comboBoxDoiTuongUuTien); // Thêm ComboBox chọn đối tượng ưu tiên

        topPanel.add(labelMon1);
        topPanel.add(textNhapDiem1); // Thêm JTextField nhập điểm Môn 1

        topPanel.add(new JLabel("Khu vực:")); // Thêm nhãn Khu vực
        topPanel.add(comboBoxKhuVuc); // Thêm ComboBox chọn khu vực

        topPanel.add(labelMon2);
        topPanel.add(textNhapDiem2); // Thêm JTextField nhập điểm Môn 2

        topPanel.add(new JLabel("Chọn ngành:"));
        topPanel.add(comboBoxChonNganh);

        topPanel.add(labelMon3);
        topPanel.add(textNhapDiem3);

        topPanel.add(DiemXetTuyen);

        getPanel().setPreferredSize(new Dimension(1475, 675)); // Ví dụ kích thước

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(bangGoiY), BorderLayout.CENTER);
        add(goiYTruongDaiHoc, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        comboBoxChonToHopMon.addActionListener(e -> {
            String selectedItem = (String) comboBoxChonToHopMon.getSelectedItem();
            if (selectedItem != null && !selectedItem.equals("Chọn tổ hợp môn")) {
                String[] parts = selectedItem.split(" - ");
                if (parts.length > 1) {
                    String[] subjects = parts[1].split(", ");
                    labelMon1.setText("Nhập điểm thi môn : " + (subjects.length > 0 ? subjects[0] : ""));
                    labelMon2.setText("Nhập điểm thi môn : " + (subjects.length > 1 ? subjects[1] : ""));
                    labelMon3.setText("Nhập điểm thi môn : " + (subjects.length > 2 ? subjects[2] : ""));

                    // Reset các trường nhập điểm
                    textNhapDiem1.setText("");
                    textNhapDiem2.setText("");
                    textNhapDiem3.setText("");
                    controller.loadAllUniversitySuggestions(bangGoiY);
                    DiemXetTuyen.setText("Điểm xét tuyển:");

                }
            } else {
                // Reset giao diện khi chọn mặc định
                labelMon1.setText("Môn 1:");
                labelMon2.setText("Môn 2:");
                labelMon3.setText("Môn 3:");
                textNhapDiem1.setText("");
                textNhapDiem2.setText("");
                textNhapDiem3.setText("");
            }
        });
        goiYTruongDaiHoc.addActionListener(e -> {
            try {
                // Lấy thông tin từ giao diện
                double diemMon1 = Double.parseDouble(textNhapDiem1.getText().trim());
                double diemMon2 = Double.parseDouble(textNhapDiem2.getText().trim());
                double diemMon3 = Double.parseDouble(textNhapDiem3.getText().trim());
                String khuVuc = (String) comboBoxKhuVuc.getSelectedItem();
                String doiTuong = (String) comboBoxDoiTuongUuTien.getSelectedItem();
                String selectedNganh = (String) comboBoxChonNganh.getSelectedItem();
                String selectedToHopMon = (String) comboBoxChonToHopMon.getSelectedItem();

                // Mã tổ hợp môn (lấy từ comboBox)
                String maToHop = selectedToHopMon.split(" - ")[0]; // Giả định mã tổ hợp môn là phần đầu

                // Tạo đối tượng TinhDiemTHPT
                THPTScoreResult tinhDiem = new THPTScoreResult(
                        maToHop,
                        diemMon1,
                        diemMon2,
                        diemMon3,
                        0, // Điểm ưu tiên mặc định là 0, có thể tính thêm nếu cần
                        khuVuc,
                        doiTuong
                );

                // Tính điểm xét tuyển
                double diemXetTuyen = tinhDiem.tinhDiemXetTuyen();
                DiemXetTuyen.setText(
                        "<html>Điểm xét tuyển: <span style='font-family:Arial; font-weight:bold; font-size:16px;'>"
                                + diemXetTuyen
                                + "</span></html>"
                );


                // Gửi truy vấn đến cơ sở dữ liệu để lấy các ngành phù hợp
                controller.getUniversitySuggestionsByTHPT(diemXetTuyen, selectedNganh, maToHop, bangGoiY);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ và chính xác điểm số.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Đã xảy ra lỗi: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    public JPanel getPanel() {
        return this;
    }
}