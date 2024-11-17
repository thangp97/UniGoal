package Version2.src.GUI;

import javax.swing.*;
import java.awt.*;

public class ShowInputTab2 {
    private JPanel tab2;

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
        gbcDynamic.weightx = 1.0;
        gbcDynamic.gridx = 0;
        gbcDynamic.gridy = 0;

        if ("Xét THPT".equals(selectedMethod)) {
            dynamicPanel.add(new JLabel("Chọn khối:"), gbcDynamic);

            gbcDynamic.gridx = 1;
            String[] khoiOptions = {"Chọn khối", "KHTN", "KHXH"};
            JComboBox<String> khoiComboBox = new JComboBox<>(khoiOptions);
            dynamicPanel.add(khoiComboBox, gbcDynamic);

            gbcDynamic.gridx = 0;
            gbcDynamic.gridy++;
            gbcDynamic.gridwidth = 2;

            JPanel khoiFieldsPanel = new JPanel(new GridBagLayout());
            dynamicPanel.add(khoiFieldsPanel, gbcDynamic);

            khoiComboBox.addActionListener(e -> {
                String selectedKhoi = (String) khoiComboBox.getSelectedItem();
                showKhoiFields(selectedKhoi, khoiFieldsPanel);
            });

        } else if ("Xét học bạ".equals(selectedMethod)) {
            gbcDynamic.gridx = 0;
            gbcDynamic.gridy++;
            dynamicPanel.add(new JLabel("Điểm trung bình lớp 10:"), gbcDynamic);
            gbcDynamic.gridx = 1;
            dynamicPanel.add(new JTextField(15), gbcDynamic);

            gbcDynamic.gridx = 0;
            gbcDynamic.gridy++;
            dynamicPanel.add(new JLabel("Điểm trung bình lớp 11:"), gbcDynamic);
            gbcDynamic.gridx = 1;
            dynamicPanel.add(new JTextField(15), gbcDynamic);

            gbcDynamic.gridx = 0;
            gbcDynamic.gridy++;
            dynamicPanel.add(new JLabel("Điểm trung bình lớp 12:"), gbcDynamic);
            gbcDynamic.gridx = 1;
            dynamicPanel.add(new JTextField(15), gbcDynamic);

            JButton calcAdmissionButton = new JButton("Tính điểm");
            gbcDynamic.gridx = 0;
            gbcDynamic.gridy++;
            gbcDynamic.gridwidth = 2;
            dynamicPanel.add(calcAdmissionButton, gbcDynamic);

        } else if ("Chứng chỉ".equals(selectedMethod)) {
            gbcDynamic.gridx = 0;
            gbcDynamic.gridy++;
            dynamicPanel.add(new JLabel("Loại chứng chỉ:"), gbcDynamic);
            gbcDynamic.gridx = 1;
            dynamicPanel.add(new JTextField(15), gbcDynamic);

            gbcDynamic.gridx = 0;
            gbcDynamic.gridy++;
            dynamicPanel.add(new JLabel("Điểm chứng chỉ:"), gbcDynamic);
            gbcDynamic.gridx = 1;
            dynamicPanel.add(new JTextField(15), gbcDynamic);

            JButton calcAdmissionButton = new JButton("Tính điểm");
            gbcDynamic.gridx = 0;
            gbcDynamic.gridy++;
            gbcDynamic.gridwidth = 2;
            dynamicPanel.add(calcAdmissionButton, gbcDynamic);
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

        if ("KHTN".equals(selectedKhoi)) {
            addKhoiFields(khoiFieldsPanel, gbcKhoi, new String[]{"Toán", "Văn", "Tiếng Anh", "Vật Lý", "Hóa Học", "Sinh Học"});
        } else if ("KHXH".equals(selectedKhoi)) {
            addKhoiFields(khoiFieldsPanel, gbcKhoi, new String[]{"Toán", "Văn", "Tiếng Anh", "Lịch Sử", "Địa Lý", "Giáo Dục Công Dân"});
        }
    }

    private void addKhoiFields(JPanel panel, GridBagConstraints gbc, String[] subjects) {
        for (String subject : subjects) {
            panel.add(new JLabel(subject + ":"), gbc);
            gbc.gridx = 1;
            panel.add(new JTextField(15), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
        }

        panel.add(new JLabel("Điểm TB lớp 12:"), gbc);
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
