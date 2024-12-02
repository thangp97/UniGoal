package Version2.src.Controller;

import Version2.src.Model.User;
import Version2.src.View.LoginView;
import Version2.src.View.SignUpView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private User model;
    private LoginView view;

    public LoginController(User model, LoginView view) {
        this.model = model;
        this.view = view;

        this.view.addLoginListener(new LoginListener());
        this.view.addRegisterListener(new RegisterListener());
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                view.showMessage("Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            try {
                if (model.login(username, password)) {
                    view.showMessage("Đăng nhập thành công!");
                    view.getFrame().dispose();
                    if (view.getLoginSuccessListener() != null) {
                        view.getLoginSuccessListener().onLoginSuccess(username);
                    }
                    view.setVisible(false);
                } else {
                    view.showMessage("Sai tên đăng nhập hoặc mật khẩu.");
                }
            } catch (Exception ex) {
                view.showMessage("Lỗi kết nối cơ sở dữ liệu.");
            }
        }
    }
    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SignUpView registerView = new SignUpView();
            new SignUpController(model, registerView);
            registerView.setVisible(true);
        }
    }
}
