package Version2.src.Controller;

import Version2.src.Model.User;
import Version2.src.View.SignUpView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpController {
    private User model;
    private SignUpView view;

    public SignUpController(User model, SignUpView view) {
        this.model = model;
        this.view = view;

        this.view.addRegisterListener(new RegisterListener());
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                view.showMessage("Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            try {
                if (model.register(username, password)) {
                    view.showMessage("Đăng ký thành công!");
                    view.setVisible(false); // Đóng form sau khi đăng ký
                } else {
                    view.showMessage("Tên đăng nhập đã tồn tại.");
                }
            } catch (Exception ex) {
                view.showMessage("Lỗi khi kết nối cơ sở dữ liệu.");
            }
        }
    }
}
