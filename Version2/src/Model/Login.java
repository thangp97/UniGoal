package Version2.src.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private Connection connection;

    public Login(Connection connection) {
        this.connection = connection;
    }

    // Kiểm tra tên đăng nhập và mật khẩu
    public boolean authenticate(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có dữ liệu trả về, tức là đăng nhập thành công
        }
    }
}
