package Version2.src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChangePasswordView extends JFrame {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JButton submitButton;
    private String currentUsername;

    public ChangePasswordView(String username) {
        this.currentUsername = username;

        setTitle("Đổi mật khẩu");
        setSize(500, 170);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        // Nhãn và trường nhập liệu
        add(new JLabel("Mật khẩu cũ:"));
        oldPasswordField = new JPasswordField();
        add(oldPasswordField);

        add(new JLabel("Mật khẩu mới:"));
        newPasswordField = new JPasswordField();
        add(newPasswordField);

        add(new JLabel(" "));

        // Nút xác nhận
        submitButton = new JButton("Xác nhận");
        add(submitButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void setSubmitButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public String getOldPassword() {
        return new String(oldPasswordField.getPassword());
    }

    public String getNewPassword() {
        return new String(newPasswordField.getPassword());
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public void close() {
        dispose();
    }
}
