package Version2.src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignUpView extends JFrame {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repasswordField;
    private JTextField emailField;
    private JButton registerButton;
    private JLabel statusLabel;

    public SignUpView() {
        frame = new JFrame("Đăng ký");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordField = new JPasswordField(20);
        JLabel repasswordLabel = new JLabel("Nhập lại mật khẩu:");
        repasswordField = new JPasswordField(20);
        JLabel email = new JLabel("Email:");
        emailField = new JTextField(20);
        registerButton = new JButton("Đăng ký");
        statusLabel = new JLabel("");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(repasswordLabel);
        panel.add(repasswordField);
        panel.add(email);
        panel.add(emailField);
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

    public String getEmail() {
        return emailField.getText();
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
