package Version2.src.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private Connection connection;
    private String username;

    public User(Connection connection) {
        this.connection = connection;
    }

    public boolean login(String username, String password) throws Exception {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        return rs.next(); // Trả về true nếu tìm thấy tài khoản
    }

    public boolean register(String username, String password, String email) throws Exception {
        // Kiểm tra xem tài khoản đã tồn tại chưa
        String checkSql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement checkStmt = connection.prepareStatement(checkSql);
        checkStmt.setString(1, username);

        ResultSet rs = checkStmt.executeQuery();
        if (rs.next()) {
            return false; // Tài khoản đã tồn tại
        }

        // Thêm tài khoản mới
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, email);

        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0; // Trả về true nếu thêm thành công
    }
    public boolean changePassword(String oldPassword, String newPassword, String username) throws SQLException {
        String query = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String currentPassword = rs.getString("password");
                if (!currentPassword.equals(oldPassword)) {
                    return false; // Mật khẩu cũ không đúng
                }
            } else {
                throw new SQLException("Người dùng không tồn tại!");
            }
        }

        // Cập nhật mật khẩu mới
        String updateQuery = "UPDATE users SET password = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }

        return true;
    }

}
