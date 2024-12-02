package Version2.src.Controller;

import Version2.src.Model.User;
import Version2.src.View.ChangePasswordView;

import javax.swing.*;
import java.sql.SQLException;

public class ChangePasswordController {
    private User userModel;
    private ChangePasswordView view;

    public ChangePasswordController(User userModel, ChangePasswordView view) {
        this.userModel = userModel;
        this.view = view;

        // Gắn sự kiện cho nút "Xác nhận"
        this.view.setSubmitButtonListener(e -> handleChangePassword());
    }

    private void handleChangePassword() {
        try {
            String oldPassword = view.getOldPassword();
            String newPassword = view.getNewPassword();
            String username = view.getCurrentUsername();

            // Kiểm tra dữ liệu đầu vào
            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                view.showMessage("Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Gọi phương thức đổi mật khẩu trong model
            boolean isSuccess = userModel.changePassword(oldPassword, newPassword, username);

            if (isSuccess) {
                view.showMessage("Đổi mật khẩu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                view.close(); // Đóng cửa sổ
            } else {
                view.showMessage("Mật khẩu cũ không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            view.showMessage("Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
