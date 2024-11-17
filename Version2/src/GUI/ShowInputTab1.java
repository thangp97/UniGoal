package Version2.src.GUI;

import javax.swing.*;
import java.awt.*;

public class ShowInputTab1 {
    private JPanel tab1;

    public ShowInputTab1() {
        tab1 = new JPanel();
        tab1.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] subjectsOptions = {"Chọn tổ hợp", "KHTN", "KHXH"};
        JComboBox<String> subjectsComboBox = new JComboBox<>(subjectsOptions);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        tab1.add(subjectsComboBox, gbc);

        subjectsComboBox.addActionListener(e -> {
            String selectedOption = (String) subjectsComboBox.getSelectedItem();
            showInputTab1(selectedOption);
        });
    }

    public JPanel getPanel() {
        return tab1;
    }

    private void showInputTab1(String selectedOption) {
        tab1.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        String[] subjectsOptions = {"Chọn tổ hợp", "KHTN", "KHXH"};
        JComboBox<String> newSubjectsComboBox = new JComboBox<>(subjectsOptions);
        newSubjectsComboBox.setSelectedItem(selectedOption);
        newSubjectsComboBox.setPreferredSize(new Dimension(150, 25));
        tab1.add(newSubjectsComboBox, gbc);

        newSubjectsComboBox.addActionListener(e -> {
            String selectedSubOption = (String) newSubjectsComboBox.getSelectedItem();
            showInputTab1(selectedSubOption);
        });

        if ("Chọn tổ hợp".equals(selectedOption)) {
            tab1.revalidate();
            tab1.repaint();
            return;
        }

        gbc.gridx = 0;
        gbc.gridy++;
        tab1.add(new JLabel("Khu vực:"), gbc);
        gbc.gridx = 1;
        String[] khuVucOptions = {"Chọn khu vực", "KV1", "KV2", "KV2-NT", "KV3"};
        JComboBox<String> khuVucComboBox = new JComboBox<>(khuVucOptions);
        tab1.add(khuVucComboBox, gbc);

        if ("KHTN".equals(selectedOption)) {
            addSubjectFields(tab1, gbc, new String[]{"Toán", "Văn", "Tiếng Anh", "Vật Lý", "Hóa Học", "Sinh Học"});
        } else if ("KHXH".equals(selectedOption)) {
            addSubjectFields(tab1, gbc, new String[]{"Toán", "Văn", "Tiếng Anh", "Lịch Sử", "Địa Lý", "Giáo Dục Công Dân"});
        }

        addAdditionalFields(tab1, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton calcGradScoreButton = new JButton("Tính điểm");
        tab1.add(calcGradScoreButton, gbc);

        tab1.revalidate();
        tab1.repaint();
    }

    private void addSubjectFields(JPanel panel, GridBagConstraints gbc, String[] subjects) {
        gbc.gridwidth = 1;
        for (String subject : subjects) {
            gbc.gridx = 0;
            gbc.gridy++;
            panel.add(new JLabel(subject + ":"), gbc);
            gbc.gridx = 1;
            panel.add(new JTextField(15), gbc);
        }
    }

    private void addAdditionalFields(JPanel panel, GridBagConstraints gbc) {
        String[] additionalLabels = {"Điểm TB lớp 12:", "Điểm khuyến khích:", "Điểm đối tượng:"};
        for (String label : additionalLabels) {
            gbc.gridx = 0;
            gbc.gridy++;
            panel.add(new JLabel(label), gbc);
            gbc.gridx = 1;
            panel.add(new JTextField(15), gbc);
        }
    }
}

