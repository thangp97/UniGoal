package Version2.src.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UniversityCaculateTab {
    private JPanel tab2;

    private static final Map<String, Double> khuVucPoints = new HashMap<>();
    static {
        khuVucPoints.put("KV1", 1.0); // Khu vực 1 cộng 1 điểm
        khuVucPoints.put("KV2", 0.5); // Khu vực 2 cộng 0.5 điểm
        khuVucPoints.put("KV2-NT", 0.5); // Khu vực 2-NT cộng 0.5 điểm
        khuVucPoints.put("KV3", 0.0); // Khu vực 3 không cộng điểm
    }

    // Map lưu danh sách các khối và các môn học tương ứng
    private static final Map<String, String[]> khoiSubjects = new HashMap<>();

    static {
        // Khối A
        khoiSubjects.put("A00", new String[]{"Toán", "Vật Lý", "Hóa Học"});
        khoiSubjects.put("A01", new String[]{"Toán", "Vật Lý", "Tiếng Anh"});
        khoiSubjects.put("A02", new String[]{"Toán", "Vật Lý", "Sinh Học"});
        khoiSubjects.put("A03", new String[]{"Toán", "Vật Lý", "Lịch Sử"});
        khoiSubjects.put("A04", new String[]{"Toán", "Vật Lý", "Địa Lý"});
        khoiSubjects.put("A05", new String[]{"Toán", "Hóa Học", "Lịch Sử"});
        khoiSubjects.put("A06", new String[]{"Toán", "Hóa Học", "Địa Lý"});
        khoiSubjects.put("A07", new String[]{"Toán", "Lịch Sử", "Địa Lý"});
        khoiSubjects.put("A08", new String[]{"Toán", "Lịch Sử", "GDCD"});
        khoiSubjects.put("A09", new String[]{"Toán", "Địa Lý", "GDCD"});
        khoiSubjects.put("A10", new String[]{"Toán", "Vật Lý", "GDCD"});
        khoiSubjects.put("A11", new String[]{"Toán", "Hóa Học", "GDCD"});

        // Khối B
        khoiSubjects.put("B00", new String[]{"Toán", "Hóa Học", "Sinh Học"});
        khoiSubjects.put("B01", new String[]{"Toán", "Sinh Học", "Lịch Sử"});
        khoiSubjects.put("B02", new String[]{"Toán", "Sinh Học", "Địa Lý"});
        khoiSubjects.put("B03", new String[]{"Toán", "Sinh Học", "Văn"});
        khoiSubjects.put("B04", new String[]{"Toán", "Sinh Học", "GDCD"});
        khoiSubjects.put("B05", new String[]{"Toán", "Sinh Học", "KHXH"});
        khoiSubjects.put("B08", new String[]{"Toán", "Sinh Học", "Tiếng Anh"});

        // Khối C
        khoiSubjects.put("C00", new String[]{"Văn", "Lịch Sử", "Địa Lý"});
        khoiSubjects.put("C01", new String[]{"Văn", "Toán", "Vật Lý"});
        khoiSubjects.put("C02", new String[]{"Văn", "Toán", "Hóa Học"});
        khoiSubjects.put("C03", new String[]{"Văn", "Toán", "Lịch Sử"});
        khoiSubjects.put("C04", new String[]{"Văn", "Toán", "Địa Lý"});
        khoiSubjects.put("C05", new String[]{"Văn", "Vật Lý", "Hóa Học"});
        khoiSubjects.put("C06", new String[]{"Văn", "Vật Lý", "Sinh Học"});
        khoiSubjects.put("C07", new String[]{"Văn", "Vật Lý", "Lịch Sử"});
        khoiSubjects.put("C08", new String[]{"Văn", "Hóa Học", "Sinh Học"});
        khoiSubjects.put("C09", new String[]{"Văn", "Vật Lý", "Địa Lý"});
        khoiSubjects.put("C10", new String[]{"Văn", "Hóa Học", "Lịch Sử"});
        khoiSubjects.put("C12", new String[]{"Văn", "Sinh Học", "Lịch Sử"});
        khoiSubjects.put("C13", new String[]{"Văn", "Sinh Học", "Địa Lý"});
        khoiSubjects.put("C14", new String[]{"Văn", "Toán", "GDCD"});
        khoiSubjects.put("C15", new String[]{"Văn", "Toán", "KHXH"});
        khoiSubjects.put("C16", new String[]{"Văn", "Vật Lý", "GDCD"});
        khoiSubjects.put("C17", new String[]{"Văn", "Hóa Học", "GDCD"});
        khoiSubjects.put("C18", new String[]{"Văn", "Sinh Học", "GDCD"});
        khoiSubjects.put("C19", new String[]{"Văn", "Lịch Sử", "GDCD"});
        khoiSubjects.put("C20", new String[]{"Văn", "Địa Lý", "GDCD"});

        // Khối D
        khoiSubjects.put("D01", new String[]{"Văn", "Toán", "Tiếng Anh"});
        khoiSubjects.put("D02", new String[]{"Văn", "Toán", "Tiếng Nga"});
        khoiSubjects.put("D03", new String[]{"Văn", "Toán", "Tiếng Pháp"});
        khoiSubjects.put("D04", new String[]{"Văn", "Toán", "Tiếng Trung"});
        khoiSubjects.put("D05", new String[]{"Văn", "Toán", "Tiếng Đức"});
        khoiSubjects.put("D06", new String[]{"Văn", "Toán", "Tiếng Nhật"});
        khoiSubjects.put("D07", new String[]{"Toán", "Hóa Học", "Tiếng Anh"});
        khoiSubjects.put("D08", new String[]{"Toán", "Sinh Học", "Tiếng Anh"});
        khoiSubjects.put("D09", new String[]{"Toán", "Lịch Sử", "Tiếng Anh"});
        khoiSubjects.put("D10", new String[]{"Toán", "Địa Lý", "Tiếng Anh"});
        khoiSubjects.put("D11", new String[]{"Văn", "Vật Lý", "Tiếng Anh"});
        khoiSubjects.put("D12", new String[]{"Văn", "Hóa Học", "Tiếng Anh"});
        khoiSubjects.put("D13", new String[]{"Văn", "Sinh Học", "Tiếng Anh"});
        khoiSubjects.put("D14", new String[]{"Văn", "Lịch Sử", "Tiếng Anh"});
        khoiSubjects.put("D15", new String[]{"Văn", "Địa Lý", "Tiếng Anh"});
    }


    public UniversityCaculateTab() {
        tab2 = new JPanel();
        tab2.setLayout(new GridBagLayout());

        GridBagConstraints gbcAdmission = new GridBagConstraints();
        gbcAdmission.fill = GridBagConstraints.HORIZONTAL;
        gbcAdmission.insets = new Insets(5, 5, 5, 5);

        String[] Method = {"Chọn phương thức", "Xét THPT", "Xét DGNL", "Xét DGTD"};
        JComboBox<String> MethodComboBox = new JComboBox<>(Method);
        gbcAdmission.gridx = 0;
        gbcAdmission.gridy = 0;
        gbcAdmission.gridwidth = 2;
        tab2.add(MethodComboBox, gbcAdmission);

        JPanel dynamictab2 = new JPanel();
        dynamictab2.setLayout(new GridBagLayout());
        GridBagConstraints gbcDynamic = new GridBagConstraints();
        gbcDynamic.fill = GridBagConstraints.HORIZONTAL;
        gbcDynamic.insets = new Insets(5, 5, 5, 5);
        gbcDynamic.gridx = 1;
        gbcDynamic.gridy = 1;
        gbcDynamic.gridwidth = 2;
        tab2.add(dynamictab2, gbcDynamic);

        MethodComboBox.addActionListener(e -> {
            String selectedMethod = (String) MethodComboBox.getSelectedItem();
            try {
                showInputTab2(selectedMethod, dynamictab2);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public JPanel getPanel() {
        return tab2;
    }
    private void showInputTab2(String selectedMethod, JPanel dynamicPanel) throws Exception {
        dynamicPanel.removeAll();
        dynamicPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbcDynamic = new GridBagConstraints();
        gbcDynamic.insets = new Insets(5, 5, 5, 5);

        if ("Xét THPT".equals(selectedMethod)) {
            RecommendTHPTView recommendTHPTView = new RecommendTHPTView();
            dynamicPanel.add(recommendTHPTView.getPanel(), gbcDynamic);

            dynamicPanel.revalidate();
            dynamicPanel.repaint();
        } if ("Xét DGNL".equals(selectedMethod)) {
            RecommendDGNLView recommendDGNLView = new RecommendDGNLView();
            dynamicPanel.add(recommendDGNLView.getPanel(), gbcDynamic);

            dynamicPanel.revalidate();
            dynamicPanel.repaint();
        } if ("Xét DGTD".equals(selectedMethod)) {
            RecommendDGTDView recommendDGTDView = new RecommendDGTDView();
            dynamicPanel.add(recommendDGTDView.getPanel(), gbcDynamic);

        }

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }
    private void showKhoiFields(String selectedKhoi, JPanel khoiFieldsPanel) {
        khoiFieldsPanel.removeAll(); // Xóa toàn bộ nội dung cũ

        // Sử dụng GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Tạo bảng danh sách ngành học
        JTable resultTable = new JTable(new DefaultTableModel(
                new Object[]{"Tên ngành", "Điểm chuẩn", "Trường"}, 0
        ));
        JScrollPane resultScrollPane = new JScrollPane(resultTable);

        if (!"Chọn khối".equals(selectedKhoi)) {
            // Thêm các trường nhập liệu cho các môn trong khối
            String[] subjects = khoiSubjects.get(selectedKhoi);
            if (subjects != null) {
                for (String subject : subjects) {
                    gbc.gridx = 0;
                    gbc.gridy++;
                    khoiFieldsPanel.add(new JLabel(subject + ":"), gbc);
                    gbc.gridx = 1;
                    JTextField subjectField = new JTextField(15);
                    subjectField.setPreferredSize(new Dimension(200, 25)); // Đảm bảo kích thước
                    khoiFieldsPanel.add(subjectField, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                }
            }

            // Thêm trường chọn khu vực
            khoiFieldsPanel.add(new JLabel("Khu vực:"), gbc);
            gbc.gridx = 1;

            String[] khuVucOptions = {"KV1", "KV2", "KV2-NT", "KV3"};
            JComboBox<String> khuVucComboBox = new JComboBox<>(khuVucOptions);
            khoiFieldsPanel.add(khuVucComboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy++;

            // Thêm các trường nhập liệu khác (ví dụ: điểm ưu tiên, điểm khuyến khích)
            khoiFieldsPanel.add(new JLabel("Điểm ưu tiên:"), gbc);
            gbc.gridx = 1;
            JTextField priorityField = new JTextField(15);
            priorityField.setPreferredSize(new Dimension(200, 25));
            khoiFieldsPanel.add(priorityField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            khoiFieldsPanel.add(new JLabel("Điểm khuyến khích:"), gbc);
            gbc.gridx = 1;
            JTextField bonusField = new JTextField(15);
            bonusField.setPreferredSize(new Dimension(200, 25));
            khoiFieldsPanel.add(bonusField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            khoiFieldsPanel.add(new JLabel("Tên ngành học:"), gbc);
            gbc.gridx = 1;
            JTextField field_of_study = new JTextField(15);
            bonusField.setPreferredSize(new Dimension(200, 25));
            khoiFieldsPanel.add(field_of_study, gbc);

            // Thêm nút "Tính điểm tốt nghiệp"
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            JButton calcadmissionButton = new JButton("Tính điểm xét tuyển và gợi ý ngành học phù hợp");
            calcadmissionButton.setBackground(new Color(0, 123, 255)); // Màu nền cho nút
            calcadmissionButton.setForeground(Color.WHITE); // Màu chữ cho nút
            calcadmissionButton.setPreferredSize(new Dimension(200, 40));
            calcadmissionButton.setFocusPainted(false);
            calcadmissionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Hiệu ứng hover cho nút
            calcadmissionButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    calcadmissionButton.setBackground(new Color(0, 105, 217));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    calcadmissionButton.setBackground(new Color(0, 123, 255));
                }
            });

            // Thêm nút vào `khoiFieldsPanel`
            khoiFieldsPanel.add(calcadmissionButton, gbc);

            // Đẩy các trường nhập liệu lên trên cùng
            gbc.gridy++;
            gbc.weighty = 1.0; // Trường nhập liệu chiếm không gian ưu tiên
            gbc.fill = GridBagConstraints.BOTH; // Đảm bảo danh sách ngành học chiếm toàn bộ chiều rộng
            gbc.weighty = 5.0; // Phần danh sách chiếm nhiều không gian hơn
            khoiFieldsPanel.add(resultScrollPane, gbc);

            // Xử lý sự kiện nhấn nút "Tính điểm tốt nghiệp"
            calcadmissionButton.addActionListener(e -> {
                // Lấy thông tin từ các trường nhập liệu
                String khuVuc = (String) khuVucComboBox.getSelectedItem();
                double priorityPoints = 0;
                double bonusPoints = 0;

                try {
                    priorityPoints = Double.parseDouble(priorityField.getText());
                    bonusPoints = Double.parseDouble(bonusField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(khoiFieldsPanel, "Vui lòng nhập đúng định dạng điểm.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double totalPoints = priorityPoints + bonusPoints;
                System.out.println("Điểm tổng: " + totalPoints);

                // Cập nhật danh sách ngành học (chỉ là ví dụ)
                DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ
                model.addRow(new Object[]{"Kỹ thuật phần mềm", "25.5", "Đại học ABC"});
                model.addRow(new Object[]{"Công nghệ thông tin", "24.0", "Đại học XYZ"});
                model.addRow(new Object[]{"Hệ thống thông tin", "23.0", "Đại học DEF"});
            });
        }

        // Cập nhật giao diện
        khoiFieldsPanel.revalidate();
        khoiFieldsPanel.repaint();
    }

    private void addKhoiFields(JPanel panel, GridBagConstraints gbc, String[] subjects) {
        for (String subject : subjects) {
            panel.add(new JLabel(subject + ":"), gbc);
            gbc.gridx = 1;
            panel.add(new JTextField(15), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
        }

        panel.add(new JLabel("Điểm ưu tiên:"), gbc);
        gbc.gridx = 1;
        panel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Điểm khuyến khích:"), gbc);
        gbc.gridx = 1;
        panel.add(new JTextField(15), gbc);

        JButton calcAdmissionButton = new JButton("Tính điểm");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(calcAdmissionButton, gbc);
    }
}
