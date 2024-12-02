package Version2.src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton; // Nút đăng ký
    private JLabel statusLabel;

    public LoginSuccessListener getLoginSuccessListener() {
        return loginSuccessListener;
    }

    public interface LoginSuccessListener {
        void onLoginSuccess(String username);
    }

    private LoginSuccessListener loginSuccessListener;

    public void setLoginSuccessListener(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;
    }


    public LoginView() {
        frame = new JFrame("Đăng nhập");
        frame.setSize(500, 250);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 hàng: tài khoản, mật khẩu, đăng nhập, đăng ký
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Đăng nhập");
        registerButton = new JButton("Bạn chưa có tài khoản? Đăng ký ngay."); // Nút đăng ký
        registerButton.setFont(new Font("Arial", Font.ITALIC, 10));
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        statusLabel = new JLabel("");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(new JLabel());
        panel.add(registerButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        statusLabel.setText(message);
    }

    public JFrame getFrame() {
        return frame;
    }
}
