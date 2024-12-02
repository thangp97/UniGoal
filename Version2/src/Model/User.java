package Version2.src.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    private Connection connection;

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

    public boolean register(String username, String password) throws Exception {
        // Kiểm tra xem tài khoản đã tồn tại chưa
        String checkSql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement checkStmt = connection.prepareStatement(checkSql);
        checkStmt.setString(1, username);

        ResultSet rs = checkStmt.executeQuery();
        if (rs.next()) {
            return false; // Tài khoản đã tồn tại
        }

        // Thêm tài khoản mới
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);

        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0; // Trả về true nếu thêm thành công
    }
}
