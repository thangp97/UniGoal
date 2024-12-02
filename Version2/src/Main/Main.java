package Version2.src.Main;

import Version2.src.Controller.*;
import Version2.src.Model.User;
import Version2.src.Utils.DatabaseConnection;
import Version2.src.View.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class Main {

    private static JPanel mainPanel;
    private static String currentUsername = null;

    private static void openChangePasswordDialog() {
        if (currentUsername == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng đăng nhập trước khi đổi mật khẩu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                // Khởi tạo View
                ChangePasswordView changePasswordView = new ChangePasswordView(currentUsername);

                // Khởi tạo Model
                User userModel = new User(Version2.src.Utils.DatabaseConnection.getConnection());

                // Khởi tạo Controller
                new ChangePasswordController(userModel, changePasswordView);

                // Hiển thị View
                changePasswordView.setVisible(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void openFavoriteListPanel() throws SQLException {
        // Tạo một bảng mới để hiển thị danh sách yêu thích
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Mã Trường", "Tên Trường", "Tên Ngành", "Điểm Trúng Tuyển"}, 0);
        JTable favoriteTable = new JTable(model);

        // Tạo một cuộn bảng để dễ dàng xem dữ liệu
        JScrollPane scrollPane = new JScrollPane(favoriteTable);
        favoriteTable.setFillsViewportHeight(true);  // Làm cho bảng tự động điều chỉnh kích thước

        // Tạo một panel để chứa bảng
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Hiển thị dialog hoặc frame mới để hiển thị danh sách yêu thích
        JOptionPane.showMessageDialog(null, panel, "Danh sách yêu thích", JOptionPane.INFORMATION_MESSAGE);

        // Gọi phương thức để tải danh sách yêu thích từ cơ sở dữ liệu
        UniversitySearchController controller = new UniversitySearchController();
        controller.loadFavoritesFromDatabase(favoriteTable, currentUsername);
    }

    private static JPopupMenu createUserMenu(JPanel navigationPanel) {
        JPopupMenu userMenu = new JPopupMenu();

        // Tùy chọn 1: Đổi mật khẩu
        JMenuItem changePasswordItem = new JMenuItem("Đổi mật khẩu");
        changePasswordItem.addActionListener(e -> openChangePasswordDialog());

        // Tùy chọn 2: Kiểm tra điểm thí sinh
        JMenuItem checkScoresItem = new JMenuItem("Kiểm tra điểm thí sinh");
        // checkScoresItem.addActionListener(e -> openCheckScoresPanel());

        // Tùy chọn 3: Kiểm tra danh sách yêu thích
        JMenuItem favoriteListItem = new JMenuItem("Danh sách yêu thích");
        favoriteListItem.addActionListener(e -> {
            try {
                openFavoriteListPanel();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Tùy chọn 4: Đăng xuất
        JMenuItem logoutItem = new JMenuItem("Đăng xuất");
        logoutItem.addActionListener(e -> logoutUser(navigationPanel));

        // Thêm các mục vào menu
        userMenu.add(changePasswordItem);
        userMenu.add(checkScoresItem);
        userMenu.add(favoriteListItem);
        userMenu.addSeparator(); // Thêm đường phân cách
        userMenu.add(logoutItem);

        return userMenu;
    }
    private static void openLoginDialog(JFrame parentFrame, JPanel navigationPanel) throws SQLException {
        LoginView loginView = new LoginView();
        User model = new User(DatabaseConnection.getConnection());
        LoginController controller = new LoginController(model, loginView);

        loginView.setLoginSuccessListener(username -> {
            currentUsername = username;
            updateLoginButton(navigationPanel); // Truyền navigationPanel
        });


        loginView.setVisible(true);
    }
    private static void logoutUser(JPanel navigationPanel) {
        // Clear the current username
        currentUsername =    null;
        System.out.println("Logout initiated."); // Debug statement
        // Update the login button
        updateLoginButton(navigationPanel);
    }
    private static void updateLoginButton(JPanel navigationPanel) {
        for (Component component : navigationPanel.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals("Đăng nhập")) {
                JButton loginButton = (JButton) component;

                if (currentUsername == null) {
                    // Set back to "Đăng nhập"
                    loginButton.setText("Đăng nhập");
                    // Remove any mouse listeners
                    for (java.awt.event.MouseListener ml : loginButton.getMouseListeners()) {
                        loginButton.removeMouseListener(ml);
                    }
                } else {
                    // Update button to show username
                    loginButton.setText(currentUsername);
                    // Set up user menu
                    JPopupMenu userMenu = createUserMenu(navigationPanel);
                    // Remove old MouseListeners
                    for (java.awt.event.MouseListener ml : loginButton.getMouseListeners()) {
                        loginButton.removeMouseListener(ml);
                    }
                    // Add MouseListener to show user menu
                    loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mousePressed(java.awt.event.MouseEvent e) {
                            if (SwingUtilities.isLeftMouseButton(e)) {
                                userMenu.show(loginButton, e.getX(), e.getY());
                            }
                        }
                    });
                    break;
                }
            }
        }
        // Revalidate and repaint the navigation panel
        navigationPanel.revalidate();
        navigationPanel.repaint();
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
            calculateButton.setPreferredSize(new Dimension(190, 30));
            searchButton.setPreferredSize(new Dimension(170, 30));
            tabViewButton.setPreferredSize(new Dimension(165, 30));
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
                if (currentUsername == null) {
                    try {
                        openLoginDialog(frame, navigationPanel); // Truyền navigationPanel vào
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
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

    private static JPanel createAboutPanel() {
        JPanel aboutPanel = new JPanel(new BorderLayout());
        aboutPanel.setBackground(new Color(245, 245, 245));
        CountdownTimerController controller = new CountdownTimerController();
        EventScheduleController eventController = new EventScheduleController();

        aboutPanel.add(controller.getCountdownTimerPanel(), BorderLayout.NORTH);
        aboutPanel.add(eventController.getEventSchedulePanel(), BorderLayout.CENTER);

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
