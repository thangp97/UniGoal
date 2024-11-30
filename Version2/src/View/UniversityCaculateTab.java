package Version2.src.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UniversityCaculateTab {
    private JPanel tab2;
    public UniversityCaculateTab() {
        tab2 = new JPanel();
        tab2.setLayout(new GridBagLayout());

        GridBagConstraints gbcAdmission = new GridBagConstraints();
        gbcAdmission.fill = GridBagConstraints.HORIZONTAL;
        gbcAdmission.insets = new Insets(5, 5, 5, 5);

        String[] Method = {"Chọn phương thức", "Xét THPT", "Xét DGNL", "Xét DGTD"};
        JComboBox<String> MethodComboBox = new JComboBox<>(Method);
        gbcAdmission.gridx = 0;
        gbcAdmission.gridy = 0;
        gbcAdmission.gridwidth = 2;
        tab2.add(MethodComboBox, gbcAdmission);

        JPanel dynamictab2 = new JPanel();
        dynamictab2.setLayout(new GridBagLayout());
        GridBagConstraints gbcDynamic = new GridBagConstraints();
        gbcDynamic.fill = GridBagConstraints.HORIZONTAL;
        gbcDynamic.insets = new Insets(5, 5, 5, 5);
        gbcDynamic.gridx = 1;
        gbcDynamic.gridy = 1;
        gbcDynamic.gridwidth = 2;
        tab2.add(dynamictab2, gbcDynamic);

        MethodComboBox.addActionListener(e -> {
            String selectedMethod = (String) MethodComboBox.getSelectedItem();
            try {
                showInputTab2(selectedMethod, dynamictab2);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public JPanel getPanel() {
        return tab2;
    }
    private void showInputTab2(String selectedMethod, JPanel dynamicPanel) throws Exception {
        dynamicPanel.removeAll();
        dynamicPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbcDynamic = new GridBagConstraints();
        gbcDynamic.insets = new Insets(5, 5, 5, 5);

        if ("Xét THPT".equals(selectedMethod)) {
            RecommendTHPTView recommendTHPTView = new RecommendTHPTView();
            dynamicPanel.add(recommendTHPTView.getPanel(), gbcDynamic);

            dynamicPanel.revalidate();
            dynamicPanel.repaint();
        } if ("Xét DGNL".equals(selectedMethod)) {
            RecommendDGNLView recommendDGNLView = new RecommendDGNLView();
            dynamicPanel.add(recommendDGNLView.getPanel(), gbcDynamic);

            dynamicPanel.revalidate();
            dynamicPanel.repaint();
        } if ("Xét DGTD".equals(selectedMethod)) {
            RecommendDGTDView recommendDGTDView = new RecommendDGTDView();
            dynamicPanel.add(recommendDGTDView.getPanel(), gbcDynamic);

        }
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }
}



