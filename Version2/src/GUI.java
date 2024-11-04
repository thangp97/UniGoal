package Version2.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JPanel gradPanel;
    private static JPanel admissionPanel;

    public static void main(String[] args) {
        // Khởi tạo JFrame chính
        JFrame frame = new JFrame("UniGoal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Thêm icon cho JFrame
        ImageIcon icon = new ImageIcon("D:/UniGOAL (1).png");
        frame.setIconImage(icon.getImage());

        // Tạo JTabbedPane cho các tab
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tạo một instance của GUI để sử dụng
        GUI gui = new GUI();
        gui.gradPanel = new JPanel();
        gui.gradPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các ô

        // Tạo JComboBox để chọn tổ hợp môn học
        String[] subjectsOptions = {"Chọn tổ hợp", "KHTN", "KHXH"};
        JComboBox<String> subjectsComboBox = new JComboBox<>(subjectsOptions);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Chiếm cả hai cột
        gui.gradPanel.add(subjectsComboBox, gbc);

        // Thêm ActionListener để hiển thị các trường nhập liệu tương ứng
        subjectsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) subjectsComboBox.getSelectedItem();
                gui.showInputFields(selectedOption);
            }
        });

        // Tab 2: Tính toán điểm xét tuyển đại học
        gui.admissionPanel = new JPanel();
        gui.admissionPanel.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        admissionPanel.add(new JLabel("Điểm môn 1:"), gbc);
        gbc.gridx = 1; // Cột thứ hai
        JTextField subject1Field = new JTextField(15); // Đặt kích thước
        admissionPanel.add(subject1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1; // Hàng thứ hai
        admissionPanel.add(new JLabel("Phương thức xét tuyển:"), gbc);

        gbc.gridx = 1; // Cột thứ hai
        JComboBox<String> methodComboBox = new JComboBox<>(new String[]{"Xét THPT", "Xét học bạ", "Chứng chỉ"});
        admissionPanel.add(methodComboBox, gbc);

        JButton findSuggestionButton = new JButton("Tìm nguyện vọng phù hợp");
        gbc.gridx = 0;
        gbc.gridy = 2; // Hàng thứ ba
        gbc.gridwidth = 2; // Chiếm cả hai cột
        admissionPanel.add(findSuggestionButton, gbc);

        JTextArea suggestionsArea = new JTextArea(5, 20);
        suggestionsArea.setEditable(false);
        gbc.gridy = 3; // Hàng thứ tư
        admissionPanel.add(new JScrollPane(suggestionsArea), gbc);

        // Thêm các tab vào JTabbedPane
        tabbedPane.add("Tính điểm tốt nghiệp THPT", gui.gradPanel);
        tabbedPane.add("Tính điểm xét tuyển đại học", admissionPanel);

        // Thêm JTabbedPane vào JFrame chính
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    // Phương thức để hiển thị các trường nhập liệu dựa trên tổ hợp đã chọn
    private void showInputFields(String selectedOption) {
        // Xóa tất cả các thành phần hiện tại trong gradPanel
        gradPanel.removeAll();

        // Tạo lại GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0; // Cho phép trường mở rộng
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Thêm trường chọn tổ hợp môn
        gradPanel.add(new JComboBox<>(new String[]{"Chọn tổ hợp", "KHTN", "KHXH"}), gbc);

        // Thêm trường nhập khu vực
        gbc.gridx = 0;
        gbc.gridy++;
        gradPanel.add(new JLabel("Khu vực:"), gbc);
        gbc.gridx = 1;
        String[] khuVucOptions = {"Chọn khu vực", "KV1", "KV2", "KV2-NT", "KV3"};
        JComboBox<String> khuVucComboBox = new JComboBox<>(khuVucOptions);
        gradPanel.add(khuVucComboBox, gbc);

        // Hiển thị các trường nhập liệu tương ứng
        if ("KHTN".equals(selectedOption)) {
            gbc.gridwidth = 1; // Đặt lại gridwidth cho các trường nhập liệu

            // Thêm trường nhập liệu cho các môn học
            gbc.gridy++;
            gbc.gridx = 0;
            gradPanel.add(new JLabel("Toán:"), gbc);
            gbc.gridx = 1;
            JTextField toanField = new JTextField(15); // Khởi tạo trường nhập liệu Toán
            gradPanel.add(toanField, gbc); // Thêm vào panel

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Văn:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Tiếng Anh:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Vật Lý:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Hóa Học:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Sinh Học:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Điểm TB lớp 12:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Điểm khuyến khích:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc);
        } else if ("KHXH".equals(selectedOption)) {
            // Tương tự cho KHXH
            gbc.gridwidth = 1; // Đặt lại gridwidth cho các trường nhập liệu
            gbc.gridy++;
            gbc.gridx = 0;
            gradPanel.add(new JLabel("Toán:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc); // Thêm trường nhập liệu cho môn Toán

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Văn:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc); // Thêm trường nhập liệu cho môn Văn

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Tiếng Anh:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc); // Thêm trường nhập liệu cho môn Tiếng Anh

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Lịch Sử:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc); // Thêm trường nhập liệu cho môn Lịch Sử

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Địa Lý:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc); // Thêm trường nhập liệu cho môn Địa Lý

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Giáo Dục Công Dân:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc); // Thêm trường nhập liệu cho môn Giáo Dục Công Dân

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Điểm TB lớp 12:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc); // Thêm trường nhập liệu cho điểm TB lớp 12

            gbc.gridx = 0;
            gbc.gridy++;
            gradPanel.add(new JLabel("Điểm khuyến khích:"), gbc);
            gbc.gridx = 1;
            gradPanel.add(new JTextField(15), gbc); // Thêm trường nhập liệu cho điểm khuyến khích
        }

// Thêm nút tính điểm vào phương thức showInputFields
        JButton calcGradScoreButton = new JButton("Tính điểm");
        calcGradScoreButton.addActionListener(e -> calculateGradScore()); // Thêm ActionListener cho nút
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Chiếm cả hai cột
        gradPanel.add(calcGradScoreButton, gbc);


        gradPanel.revalidate(); // Cập nhật lại panel
        gradPanel.repaint(); // Vẽ lại panel
    }

    private void calculateGradScore() {
        // Lấy giá trị từ các trường nhập liệu
        String khuVuc = ((JComboBox<String>) gradPanel.getComponent(1)).getSelectedItem().toString();
        double diemTB12 = Double.parseDouble(((JTextField) gradPanel.getComponent(7)).getText());
        double diemKhuyenKhich = Double.parseDouble(((JTextField) gradPanel.getComponent(8)).getText());

        // Lấy điểm các môn từ các trường nhập liệu
        double[] diemThanhPhan = new double[5]; // Số môn học trong tổ hợp
        diemThanhPhan[0] = Double.parseDouble(((JTextField) gradPanel.getComponent(3)).getText()); // Toán
        diemThanhPhan[1] = Double.parseDouble(((JTextField) gradPanel.getComponent(5)).getText()); // Văn
        diemThanhPhan[2] = Double.parseDouble(((JTextField) gradPanel.getComponent(9)).getText()); // Tiếng Anh
        diemThanhPhan[3] = Double.parseDouble(((JTextField) gradPanel.getComponent(11)).getText()); // Vật Lý
        diemThanhPhan[4] = Double.parseDouble(((JTextField) gradPanel.getComponent(13)).getText()); // Hóa Học
        diemThanhPhan[5] = Double.parseDouble(((JTextField) gradPanel.getComponent(15)).getText()); // Sinh Học

        // Tạo đối tượng ThiSinh
        ThiSinh thiSinh = new ThiSinh(khuVuc, "UT1", diemTB12, diemKhuyenKhich, diemThanhPhan);

        // Tính toán kết quả
        KetQuaTotNghiep ketQuaTotNghiep = new KetQuaTotNghiep(thiSinh);

        // Hiển thị kết quả
        JOptionPane.showMessageDialog(gradPanel, "Kết quả: " + ketQuaTotNghiep.getKetQua());
    }

}
