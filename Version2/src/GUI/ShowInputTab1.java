package Version2.src.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowInputTab1 {
    private JPanel tab1;
    private JTextField[] subjectTextFields;
    private JTextField tb12Field, khuyenKhichField, doiTuongField;

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
        calcGradScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateGrade();
            }
        });
        tab1.add(calcGradScoreButton, gbc);

        tab1.revalidate();
        tab1.repaint();
    }

    private void addSubjectFields(JPanel panel, GridBagConstraints gbc, String[] subjects) {
        subjectTextFields = new JTextField[subjects.length];
        gbc.gridwidth = 1;
        for (int i = 0; i < subjects.length; i++) {
            gbc.gridx = 0;
            gbc.gridy++;
            panel.add(new JLabel(subjects[i] + ":"), gbc);
            gbc.gridx = 1;
            subjectTextFields[i] = new JTextField(15);
            panel.add(subjectTextFields[i], gbc);
        }
    }

    private void addAdditionalFields(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Điểm TB lớp 12:"), gbc);
        gbc.gridx = 1;
        tb12Field = new JTextField(15);
        panel.add(tb12Field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Điểm khuyến khích:"), gbc);
        gbc.gridx = 1;
        khuyenKhichField = new JTextField(15);
        panel.add(khuyenKhichField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Điểm đối tượng:"), gbc);
        gbc.gridx = 1;
        doiTuongField = new JTextField(15);
        panel.add(doiTuongField, gbc);
    }

    private void calculateGrade() {
        try {
            // Lấy điểm môn học
            double totalScore = 0;
            for (JTextField subjectField : subjectTextFields) {
                String text = subjectField.getText();
                if (text.isEmpty()) {
                    throw new NumberFormatException("Điểm môn không được để trống");
                }
                totalScore += Double.parseDouble(text);
            }

            // Lấy điểm bổ sung
            String tb12Text = tb12Field.getText();
            String khuyenKhichText = khuyenKhichField.getText();
            String doiTuongText = doiTuongField.getText();

            if (tb12Text.isEmpty() || khuyenKhichText.isEmpty() || doiTuongText.isEmpty()) {
                throw new NumberFormatException("Điểm bổ sung không được để trống");
            }

            double tb12 = Double.parseDouble(tb12Text);
            double khuyenKhich = Double.parseDouble(khuyenKhichText);
            double doiTuong = Double.parseDouble(doiTuongText);

            // Tính điểm tổng
            double finalScore = totalScore + tb12 + khuyenKhich + doiTuong;

            // Hiển thị kết quả
            JOptionPane.showMessageDialog(tab1, "Điểm tổng: " + finalScore);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(tab1, "Vui lòng nhập đúng định dạng điểm! " + e.getMessage());
        }
    }
}
