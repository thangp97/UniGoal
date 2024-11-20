package Version2.src.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UniversityCheckUI {

    private JPanel panelMain;
    private JLabel chonHinhThuc;
    private JRadioButton luaChon2;
    private JRadioButton luaChon1;
    private JLabel nhapDiem;
    private JTextField textNhapDiem;
    private JButton goiYTruongDaiHoc;
    private JTable bangGoiY;
    private JLabel chonNganh;
    private JComboBox comboBoxChonNganh;
    private JLabel textDSDaiHoc;

    public UniversityCheckUI() {
        goiYTruongDaiHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int diem = Integer.parseInt(textNhapDiem.getText());
//                if (luaChon1.isSelected()) {
//                    Tam.setText("DGNL " + diem + " do");
//                }
//                else {
//                    Tam.setText("DGTD " + diem + " truot");
//                }
            }
        });
    }

//    public static void main(String[] args) {
//        try {
//            UIManager.getLookAndFeel();
//        }catch (Exception e){
//            e.getStackTrace();
//        }
//
//        JFrame frame = new JFrame("University");
//        frame.setSize(600,400);
//        frame.setContentPane(new UniversityCheckUI().panelMain);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }
}
