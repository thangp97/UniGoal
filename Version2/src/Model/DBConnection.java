package Version2.src.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Thay đổi thông tin kết nối theo yêu cầu của bạn
    private static final String URL = "jdbc:mysql://localhost:3306/unigoal";
    private static final String USER = "root";
    private static final String PASSWORD = "29102004";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Tạo kết nối
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
