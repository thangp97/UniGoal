package Version2.src;


import Version2.src.Controller.UniversitySearch;
import Version2.src.Utils.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        // Khởi chạy UI
        javax.swing.SwingUtilities.invokeLater(() -> new UniversitySearch());

        // Đảm bảo đóng kết nối khi thoát ứng dụng
        Runtime.getRuntime().addShutdownHook(new Thread(() -> DatabaseConnection.closeConnection()));
    }
}
