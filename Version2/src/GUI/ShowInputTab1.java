package Version2.src.GUI;

import Version2.src.Controller.GraduateScoreResult;

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
        calcGradScoreButton.addActionListener(e -> calculateGrade());
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
            // Lấy điểm Toán, Văn, Anh
            if (subjectTextFields.length < 3) {
                throw new IllegalArgumentException("Cần nhập đủ điểm Toán, Văn, Anh và các môn tổ hợp");
            }

            double mathScore = Double.parseDouble(subjectTextFields[0].getText());
            double literatureScore = Double.parseDouble(subjectTextFields[1].getText());
            double englishScore = Double.parseDouble(subjectTextFields[2].getText());

            if (mathScore < 0 || mathScore > 10 || literatureScore < 0 || literatureScore > 10 || englishScore < 0 || englishScore > 10) {
                throw new IllegalArgumentException("Điểm Toán, Văn, Anh phải trong khoảng từ 0 đến 10");
            }

            // Lấy điểm trung bình bài thi tổ hợp
            double totalCombinationScore = 0;
            int combinationCount = 0;
            for (int i = 3; i < subjectTextFields.length; i++) {
                String text = subjectTextFields[i].getText();
                if (text.isEmpty()) {
                    throw new NumberFormatException("Điểm bài thi tổ hợp không được để trống");
                }
                double score = Double.parseDouble(text);
                if (score < 0 || score > 10) {
                    throw new IllegalArgumentException("Điểm các môn tổ hợp phải trong khoảng từ 0 đến 10");
                }
                totalCombinationScore += score;
                combinationCount++;
            }

            if (combinationCount == 0) {
                throw new IllegalArgumentException("Cần nhập ít nhất một môn tổ hợp");
            }

            double avgCombinationScore = totalCombinationScore / combinationCount;

            // Lấy điểm bổ sung
            double tb12 = Double.parseDouble(tb12Field.getText());
            double khuyenKhich = Double.parseDouble(khuyenKhichField.getText());
            double doiTuong = Double.parseDouble(doiTuongField.getText());

            if (tb12 < 0 || tb12 > 10) {
                throw new IllegalArgumentException("Điểm trung bình lớp 12 phải trong khoảng từ 0 đến 10");
            }
            if (khuyenKhich < 0 || khuyenKhich > 5) {
                throw new IllegalArgumentException("Điểm khuyến khích phải trong khoảng từ 0 đến 5");
            }
            if (doiTuong < 0 || doiTuong > 10) {
                throw new IllegalArgumentException("Điểm ưu tiên phải trong khoảng từ 0 đến 10");
            }

            // Tính điểm tốt nghiệp
//            double totalScore = mathScore + literatureScore + englishScore + avgCombinationScore;
//            double avgScore = (totalScore + khuyenKhich) / 4;
//            double finalScore = ((avgScore * 7) + (tb12 * 3)) / 10 + doiTuong;

            // Hiển thị kết quả
            JOptionPane.showMessageDialog(tab1, "Điểm tốt nghiệp: " + String.format("%.2f", GraduateScoreResult.calculateGraduationScore(mathScore, literatureScore,englishScore,avgCombinationScore,tb12,khuyenKhich,doiTuong)));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(tab1, "Vui lòng nhập đúng định dạng điểm! " + e.getMessage());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(tab1, "Lỗi: " + e.getMessage());
        }
    }
}
