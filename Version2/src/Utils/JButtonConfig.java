package Version2.src.Utils;

import javax.swing.*;
import java.awt.*;

public class JButtonConfig extends javax.swing.JButton{
    public JButtonConfig(String text, ImageIcon imgIcon){
        super(text, imgIcon);
        setBackground(Color.ORANGE);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 12));
    }
}
