package Version2.src.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShowInputTab2 {
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


    public ShowInputTab2() {
        tab2 = new JPanel();
        tab2.setLayout(new GridBagLayout());

        GridBagConstraints gbcAdmission = new GridBagConstraints();
        gbcAdmission.fill = GridBagConstraints.HORIZONTAL;
        gbcAdmission.insets = new Insets(5, 5, 5, 5);

        String[] Method = {"Chọn phương thức", "Xét THPT", "Xét học bạ", "Chứng chỉ"};
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
            showInputTab2(selectedMethod, dynamictab2);
        });
    }

    public JPanel getPanel() {
        return tab2;
    }

    private void showInputTab2(String selectedMethod, JPanel dynamicPanel) {
        dynamicPanel.removeAll();

        GridBagConstraints gbcDynamic = new GridBagConstraints();
        gbcDynamic.fill = GridBagConstraints.HORIZONTAL;
        gbcDynamic.insets = new Insets(5, 5, 5, 5);

        // Khu vực "Chọn khối"
        if ("Xét THPT".equals(selectedMethod)) {
            // Label "Chọn khối"
            gbcDynamic.gridx = 0;
            gbcDynamic.gridy = 0;
            gbcDynamic.anchor = GridBagConstraints.NORTH; // Đẩy lên trên
            dynamicPanel.add(new JLabel("Chọn khối:"), gbcDynamic);

            // JComboBox chọn khối
            gbcDynamic.gridx = 1;
            ArrayList<String> sortedKhoiList = new ArrayList<>(khoiSubjects.keySet());
            Collections.sort(sortedKhoiList);
            sortedKhoiList.add(0, "Chọn khối");

            JComboBox<String> khoiComboBox = new JComboBox<>(sortedKhoiList.toArray(new String[0]));
            dynamicPanel.add(khoiComboBox, gbcDynamic);

            // Khu vực các trường nhập liệu động
            gbcDynamic.gridx = 0;
            gbcDynamic.gridy = 1;
            gbcDynamic.gridwidth = 2;
            JPanel khoiFieldsPanel = new JPanel(new GridBagLayout());
            dynamicPanel.add(khoiFieldsPanel, gbcDynamic);

            khoiComboBox.addActionListener(e -> {
                String selectedKhoi = (String) khoiComboBox.getSelectedItem();
                showKhoiFields(selectedKhoi, khoiFieldsPanel);
            });

            // Ảnh chú thích
            gbcDynamic.gridy = 2; // Đẩy ảnh xuống dưới cùng
            gbcDynamic.weighty = 1.0; // Phần còn lại dành cho ảnh
            gbcDynamic.anchor = GridBagConstraints.SOUTH; // Gắn ảnh vào phần cuối cùng
            gbcDynamic.fill = GridBagConstraints.BOTH; // Ảnh chiếm toàn bộ phần còn lại

            JLabel chuThichLabel = new JLabel();
            chuThichLabel.setHorizontalAlignment(JLabel.CENTER); // Ảnh căn giữa
            dynamicPanel.add(chuThichLabel, gbcDynamic);

            // Tự động điều chỉnh kích thước ảnh
            dynamicPanel.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int panelWidth = dynamicPanel.getWidth();
                    int panelHeight = dynamicPanel.getHeight() - khoiFieldsPanel.getHeight() - 50; // Trừ chiều cao phần trên

                    ImageIcon originalIcon = new ImageIcon("D:/TableIilustration.png");
                    Image scaledImage = originalIcon.getImage().getScaledInstance(
                            panelWidth, // Đặt chiều rộng bằng panel
                            panelHeight > 0 ? panelHeight : -1, // Tự động điều chỉnh chiều cao nếu hợp lệ
                            Image.SCALE_SMOOTH
                    );
                    chuThichLabel.setIcon(new ImageIcon(scaledImage));
                }
            });
        }

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }



    private void showKhoiFields(String selectedKhoi, JPanel khoiFieldsPanel) {
        khoiFieldsPanel.removeAll();

        GridBagConstraints gbcKhoi = new GridBagConstraints();
        gbcKhoi.fill = GridBagConstraints.HORIZONTAL;
        gbcKhoi.insets = new Insets(5, 5, 5, 5);
        gbcKhoi.gridx = 0;
        gbcKhoi.gridy = 0;

        if (!"Chọn khối".equals(selectedKhoi)) {
            // Thêm các trường nhập liệu cho các môn trong khối
            String[] subjects = khoiSubjects.get(selectedKhoi);
            if (subjects != null) {
                for (String subject : subjects) {
                    khoiFieldsPanel.add(new JLabel(subject + ":"), gbcKhoi);
                    gbcKhoi.gridx = 1;
                    khoiFieldsPanel.add(new JTextField(15), gbcKhoi);

                    gbcKhoi.gridx = 0;
                    gbcKhoi.gridy++;
                }
            }

            // Thêm trường chọn khu vực
            khoiFieldsPanel.add(new JLabel("Chọn khu vực:"), gbcKhoi);
            gbcKhoi.gridx = 1;

            String[] khuVucOptions = {"KV1", "KV2", "KV2-NT", "KV3"};
            JComboBox<String> khuVucComboBox = new JComboBox<>(khuVucOptions);
            khoiFieldsPanel.add(khuVucComboBox, gbcKhoi);

            gbcKhoi.gridx = 0;
            gbcKhoi.gridy++;

            // Thêm các trường nhập liệu khác (ví dụ: điểm trung bình lớp 12, điểm khuyến khích)
            khoiFieldsPanel.add(new JLabel("Điểm TB lớp 12:"), gbcKhoi);
            gbcKhoi.gridx = 1;
            khoiFieldsPanel.add(new JTextField(15), gbcKhoi);

            gbcKhoi.gridx = 0;
            gbcKhoi.gridy++;
            khoiFieldsPanel.add(new JLabel("Điểm khuyến khích:"), gbcKhoi);
            gbcKhoi.gridx = 1;
            khoiFieldsPanel.add(new JTextField(15), gbcKhoi);

            gbcKhoi.gridx = 0;
            gbcKhoi.gridy++;
            JButton calcAdmissionButton = new JButton("Tính điểm");
            gbcKhoi.gridwidth = 2;
            khoiFieldsPanel.add(calcAdmissionButton, gbcKhoi);

            // Xử lý chọn khu vực nếu cần
            khuVucComboBox.addActionListener(e -> {
                String selectedKhuVuc = (String) khuVucComboBox.getSelectedItem();
                System.out.println("Khu vực đã chọn: " + selectedKhuVuc + " (Cộng điểm: " + khuVucPoints.get(selectedKhuVuc) + ")");
            });
        }

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
