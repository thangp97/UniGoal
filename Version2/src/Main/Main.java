package Version2.src.Main;

import Version2.src.Controller.CountdownTimerController;
import Version2.src.Controller.LoginController;
import Version2.src.Model.User;
import Version2.src.Utils.DatabaseConnection;
import Version2.src.View.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLException;

public class Main {

    private static JPanel mainPanel;
    private static void openLoginDialog(JFrame parentFrame) throws SQLException {
        // Mở cửa sổ đăng nhập
        LoginView loginView = new LoginView();
        User model = new User(DatabaseConnection.getConnection());
        LoginController controller = new LoginController(model, loginView);
        loginView.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Tạo cửa sổ chính
            JFrame frame = new JFrame("UniGoal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 800);
            frame.setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình

            // Thêm icon cho ứng dụng
            ImageIcon icon = new ImageIcon("Version2/src/Utils/Icons/UniGOAL (1).png");
            frame.setIconImage(icon.getImage());

            // Tạo thanh điều hướng
            JPanel navigationPanel = new JPanel();
            navigationPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Giảm khoảng cách giữa các nút
            navigationPanel.setBackground(new Color(100, 149, 237)); // Màu xanh nhẹ
            navigationPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Giảm padding của navigation panel

            // Thêm chữ "UNIGOAL" vào thanh điều hướng
            JButton logoButton = new JButton("UNIGOAL");
            logoButton.setFont(new Font("Arial", Font.BOLD, 18)); // Giảm kích thước font chữ
            logoButton.setForeground(Color.WHITE);
            logoButton.setBackground(new Color(100, 149, 237)); // Trùng màu nền
            logoButton.setBorderPainted(false);
            logoButton.setFocusPainted(false);
            logoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            logoButton.setPreferredSize(new Dimension(120, 30)); // Giảm kích thước nút
            logoButton.addActionListener(e -> showPanel(createAboutPanel()));

            // Thêm các nút điều hướng
            JButton aboutButton = createNavigationButton("Giới thiệu");
            JButton calculateButton = createNavigationButton("Tính điểm tốt nghiệp");
            JButton searchButton = createNavigationButton("Tìm kiếm thông tin");
            JButton tabViewButton = createNavigationButton("Xét tuyển Đại học");
            JButton loginButton = createNavigationButton("Đăng nhập");
            JButton exitButton = createNavigationButton("Thoát");

            // Điều chỉnh kích thước nút và font chữ
            aboutButton.setPreferredSize(new Dimension(120, 30));
            calculateButton.setPreferredSize(new Dimension(200, 30));
            searchButton.setPreferredSize(new Dimension(200, 30));
            tabViewButton.setPreferredSize(new Dimension(200, 30));
            loginButton.setPreferredSize(new Dimension(120, 30));
            exitButton.setPreferredSize(new Dimension(100, 30));


            // Thêm hành động cho các nút
            aboutButton.addActionListener(e -> showPanel(createAboutPanel()));
            calculateButton.addActionListener(e -> showPanel(createCalculatePanel()));
            searchButton.addActionListener(e -> {
                try {
                    showPanel(createSearchPanel());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            tabViewButton.addActionListener(e -> {
                try {
                    showPanel(createTabbedPane());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }); // Chuyển sang giao diện tab

            exitButton.addActionListener(e -> System.exit(0));

            loginButton.addActionListener(e -> {
                try {
                    openLoginDialog(frame);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });



            // Thêm các thành phần vào navigation panel
            navigationPanel.add(logoButton);
            navigationPanel.add(aboutButton);
            navigationPanel.add(calculateButton);
            navigationPanel.add(searchButton);
            navigationPanel.add(tabViewButton);
            navigationPanel.add(loginButton);
            navigationPanel.add(exitButton);

            // Tạo main panel để chứa nội dung
            mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); // Thêm khoảng cách cho panel
            mainPanel.setBackground(Color.WHITE); // Nền trắng cho nội dung

            // Thêm navigation panel và main panel vào frame
            frame.setLayout(new BorderLayout());
            frame.add(navigationPanel, BorderLayout.NORTH);
            frame.add(mainPanel, BorderLayout.CENTER);

            // Hiển thị panel Giới thiệu khi khởi động
            showPanel(createAboutPanel());
            frame.setVisible(true);
        });
    }


    private static JButton createNavigationButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(240, 238 , 255)); // Màu nhạt
        button.setForeground(Color.BLACK); // Chữ màu xám
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Thêm hiệu ứng con trỏ
        return button;
    }

    private static JPanel createEventSchedulePanel() {
        JPanel eventPanel = new JPanel(new BorderLayout());
        eventPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)), "Lịch sự kiện",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLUE));
        eventPanel.setBackground(new Color(245, 245, 245));

        DefaultListModel<String> eventListModel = new DefaultListModel<>();
        JList<String> eventList = new JList<>(eventListModel);
        eventList.setFont(new Font("Arial", Font.PLAIN, 14));
        eventPanel.add(new JScrollPane(eventList), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField eventNameField = new JTextField(15);
        JTextField startDateField = new JTextField(10);
        JTextField endDateField = new JTextField(10);
        JTextField descriptionField = new JTextField(20);
        JButton addEventButton = new JButton("Thêm sự kiện");

        addEventButton.addActionListener(e -> {
            String eventInfo = "Tên: " + eventNameField.getText() +
                    ", Bắt đầu: " + startDateField.getText() +
                    ", Kết thúc: " + endDateField.getText() +
                    ", Nội dung: " + descriptionField.getText();
            eventListModel.addElement(eventInfo);
            eventNameField.setText("");
            startDateField.setText("");
            endDateField.setText("");
            descriptionField.setText("");
        });

        controlPanel.add(new JLabel("Tên:"));
        controlPanel.add(eventNameField);
        controlPanel.add(new JLabel("Bắt đầu:"));
        controlPanel.add(startDateField);
        controlPanel.add(new JLabel("Kết thúc:"));
        controlPanel.add(endDateField);
        controlPanel.add(new JLabel("Nội dung:"));
        controlPanel.add(descriptionField);
        controlPanel.add(addEventButton);

        eventPanel.add(controlPanel, BorderLayout.SOUTH);
        return eventPanel;
    }


    private static JPanel createAboutPanel() {
        JPanel aboutPanel = new JPanel(new BorderLayout());
        JPanel eventSchedulePanel = createEventSchedulePanel();
        aboutPanel.setBackground(new Color(245, 245, 245));
        CountdownTimerController controller = new CountdownTimerController();

        aboutPanel.add(controller.getCountdownTimerPanel(), BorderLayout.NORTH);
        aboutPanel.add(eventSchedulePanel, BorderLayout.CENTER);

        return aboutPanel;
    }

    private static JPanel createCalculatePanel() {
        JPanel calculatePanel = new GraduateCaculateView().getPanel();
        return calculatePanel;
    }

    private static JPanel createSearchPanel() throws SQLException {
        JPanel searchPanel = new UniversitySearchView().getPanel();
        return searchPanel;
    }

    private static JPanel createTabbedPane() throws Exception {
        JTabbedPane tabbedPane = new JTabbedPane();
        RecommendTHPTView tab1 = new RecommendTHPTView();
        RecommendDGNLView tab2 = new RecommendDGNLView();
        RecommendDGTDView tab3 = new RecommendDGTDView();



        tabbedPane.addTab("Xét tuyển đại học theo điểm THPTQG", tab1.getPanel());
        tabbedPane.addTab("Xét tuyển đại học theo điểm ĐGNL", tab2.getPanel());
        tabbedPane.addTab("Xét tuyển đại học theo điểm ĐGTD", tab3.getPanel());

        JPanel tabbedPanePanel = new JPanel(new BorderLayout());
        tabbedPanePanel.add(tabbedPane, BorderLayout.CENTER);
        return tabbedPanePanel;
    }

    private static void showPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
