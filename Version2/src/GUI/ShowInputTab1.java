package Version2.src.GUI;

import Version2.src.Controller.GraduateScoreResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ShowInputTab1 {
    private JPanel tab1;
    private JTextField[] subjectTextFields;
    private JTextField tb12Field, khuyenKhichField, uuTienField;
    private String khuVuc;

    // Tạo bản đồ điểm cộng cho các khu vực
    private static final Map<String, Double> khuVucPoints = new HashMap<>();
    static {
        khuVucPoints.put("KV1", 0.75); // Khu vực 1 cộng 0.75 điểm
        khuVucPoints.put("KV2", 0.25); // Khu vực 2 cộng 0.25 điểm
        khuVucPoints.put("KV2-NT", 0.5); // Khu vực 2-NT cộng 0.5 điểm
        khuVucPoints.put("KV3", 0.0); // Khu vực 3 không cộng điểm
    }

    public ShowInputTab1() {
        tab1 = new JPanel();
        tab1.setLayout(new GridBagLayout());
        tab1.setBackground(new Color(255, 255, 255)); // Màu nền trắng cho tab1

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] subjectsOptions = {"Chọn tổ hợp", "KHTN", "KHXH"};
        JComboBox<String> subjectsComboBox = new JComboBox<>(subjectsOptions);
        subjectsComboBox.setBackground(new Color(240, 240, 240)); // Màu nền cho combo box
        subjectsComboBox.setForeground(new Color(50, 50, 50)); // Màu chữ cho combo box
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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        String[] subjectsOptions = {"Chọn tổ hợp", "KHTN", "KHXH"};
        JComboBox<String> newSubjectsComboBox = new JComboBox<>(subjectsOptions);
        newSubjectsComboBox.setSelectedItem(selectedOption);
        newSubjectsComboBox.setPreferredSize(new Dimension(200, 30));
        newSubjectsComboBox.setBackground(new Color(240, 240, 240)); // Màu nền cho combo box
        newSubjectsComboBox.setForeground(new Color(50, 50, 50)); // Màu chữ cho combo box
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

        gbc.gridy++;
        tab1.add(new JLabel("Khu vực:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] khuVucOptions = {"Chọn khu vực", "KV1", "KV2", "KV2-NT", "KV3"};
        JComboBox<String> khuVucComboBox = new JComboBox<>(khuVucOptions);
        khuVucComboBox.setPreferredSize(new Dimension(200, 30));
        khuVucComboBox.setBackground(new Color(240, 240, 240)); // Màu nền cho combo box khu vực
        khuVucComboBox.setForeground(new Color(50, 50, 50)); // Màu chữ cho combo box khu vực
        tab1.add(khuVucComboBox, gbc);

        if ("KHTN".equals(selectedOption)) {
            addSubjectFields(tab1, gbc, new String[]{"Toán", "Văn", "Tiếng Anh", "Vật Lý", "Hóa Học", "Sinh Học"});
        } else if ("KHXH".equals(selectedOption)) {
            addSubjectFields(tab1, gbc, new String[]{"Toán", "Văn", "Tiếng Anh", "Lịch Sử", "Địa Lý", "Giáo Dục Công Dân"});
        }

        addAdditionalFields(tab1, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        JButton calcGradScoreButton = new JButton("Tính điểm tốt nghiệp");
        calcGradScoreButton.setBackground(new Color(0, 123, 255)); // Màu nền cho nút
        calcGradScoreButton.setForeground(Color.WHITE); // Màu chữ cho nút
        calcGradScoreButton.setPreferredSize(new Dimension(200, 40));
        calcGradScoreButton.setFocusPainted(false);
        calcGradScoreButton.addActionListener(e -> calculateGrade());
        calcGradScoreButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover cho nút
        calcGradScoreButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                calcGradScoreButton.setBackground(new Color(0, 105, 217));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                calcGradScoreButton.setBackground(new Color(0, 123, 255));
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
            JLabel subjectLabel = new JLabel(subjects[i] + ":");
            subjectLabel.setForeground(new Color(50, 50, 50)); // Màu chữ của các nhãn môn học
            panel.add(subjectLabel, gbc);
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            subjectTextFields[i] = new JTextField(20);
            subjectTextFields[i].setPreferredSize(new Dimension(200, 30));
            subjectTextFields[i].setBackground(new Color(240, 240, 240)); // Màu nền cho các trường nhập
            subjectTextFields[i].setForeground(new Color(50, 50, 50)); // Màu chữ cho các trường nhập
            panel.add(subjectTextFields[i], gbc);
        }
    }

    private void addAdditionalFields(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Điểm TB lớp 12:"), gbc);
        gbc.gridx = 1;
        tb12Field = new JTextField(20);
        tb12Field.setPreferredSize(new Dimension(200, 30));
        tb12Field.setBackground(new Color(240, 240, 240)); // Màu nền cho trường nhập điểm lớp 12
        panel.add(tb12Field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Điểm khuyến khích:"), gbc);
        gbc.gridx = 1;
        khuyenKhichField = new JTextField(20);
        khuyenKhichField.setPreferredSize(new Dimension(200, 30));
        khuyenKhichField.setBackground(new Color(240, 240, 240)); // Màu nền cho trường nhập điểm khuyến khích
        panel.add(khuyenKhichField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Điểm ưu tiên:"), gbc);
        gbc.gridx = 1;
        uuTienField = new JTextField(20);
        uuTienField.setPreferredSize(new Dimension(200, 30));
        uuTienField.setBackground(new Color(240, 240, 240)); // Màu nền cho trường nhập điểm đối tượng
        panel.add(uuTienField, gbc);
    }

    private void calculateGrade() {
        try {

            // Lấy tổ hợp được chọn
            JComboBox<String> comboBox = (JComboBox<String>) tab1.getComponent(0); // Giả định comboBox là thành phần đầu tiên
            String selectedOption = (String) comboBox.getSelectedItem();

            if ("Chọn tổ hợp".equals(selectedOption)) {
                throw new IllegalArgumentException("Vui lòng chọn tổ hợp môn!");
            }

            JComboBox<String> khuVucComboBox = null;
            for (Component comp : tab1.getComponents()) {
                if (comp instanceof JComboBox && !comp.equals(comboBox)) {
                    khuVucComboBox = (JComboBox<String>) comp;
                    break;
                }
            }
            if (khuVucComboBox != null) {
                khuVuc = (String) khuVucComboBox.getSelectedItem();
                if ("Chọn khu vực".equals(khuVuc)) {
                    throw new IllegalArgumentException("Vui lòng chọn khu vực!");
                }
            }

            // Kiểm tra các trường nhập liệu
            for (int i = 0; i < subjectTextFields.length; i++) {
                if (subjectTextFields[i].getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Vui lòng nhập điểm cho môn: " + (i == 0 ? "Toán" :
                                                        i == 1 ? "Văn" :
                                                        i == 2 ? "Tiếng Anh" : "Môn tổ hợp " + (i - 2)));
                }
            }

            if (tb12Field.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Vui lòng nhập điểm trung bình lớp 12!");
            }

            // Lấy điểm Toán, Văn, Anh
            double mathScore = Double.parseDouble(subjectTextFields[0].getText());
            double literatureScore = Double.parseDouble(subjectTextFields[1].getText());
            double englishScore = Double.parseDouble(subjectTextFields[2].getText());
            double areaBonus = khuVucPoints.getOrDefault(khuVuc, 0.0);


            if (mathScore < 0 || mathScore > 10 || literatureScore < 0 || literatureScore > 10 || englishScore < 0 || englishScore > 10) {
                throw new IllegalArgumentException("Điểm Toán, Văn, Anh phải trong khoảng từ 0 đến 10");
            }

            // Lấy điểm trung bình bài thi tổ hợp
            double totalCombinationScore = 0;
            int combinationCount = 0;
            for (int i = 3; i < subjectTextFields.length; i++) {
                double score = Double.parseDouble(subjectTextFields[i].getText());
                if (score < 0 || score > 10) {
                    throw new IllegalArgumentException("Điểm các môn tổ hợp phải trong khoảng từ 0 đến 10");
                }
                totalCombinationScore += score;
                combinationCount++;
            }

            double avgCombinationScore = totalCombinationScore / combinationCount;

            // Lấy điểm bổ sung
            double tb12 = Double.parseDouble(tb12Field.getText());
            double khuyenKhich = Double.parseDouble(khuyenKhichField.getText());
            double uuTien = Double.parseDouble(uuTienField.getText()) + areaBonus;

            if (tb12 < 0 || tb12 > 10) {
                throw new IllegalArgumentException("Điểm trung bình lớp 12 phải trong khoảng từ 0 đến 10");
            }
            if (khuyenKhich < 0 || khuyenKhich > 5) {
                throw new IllegalArgumentException("Điểm khuyến khích phải trong khoảng từ 0 đến 5");
            }
            if (uuTien < 0 || uuTien > 10) {
                throw new IllegalArgumentException("Điểm ưu tiên phải trong khoảng từ 0 đến 10");
            }

            // Tính điểm tốt nghiệp
            double result = GraduateScoreResult.calculateGraduationScore(
                    mathScore, literatureScore, englishScore, avgCombinationScore, tb12, khuyenKhich, uuTien);

            // Hiển thị kết quả
            JOptionPane.showMessageDialog(tab1, "Điểm tốt nghiệp: " + String.format("%.2f", result));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(tab1, "Vui lòng nhập đúng định dạng điểm!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(tab1, e.getMessage());
        }
    }

}



