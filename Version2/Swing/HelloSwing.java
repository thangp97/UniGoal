package Version2.Swing;

import javax.swing.*;

public class HelloSwing {
    public HelloSwing() {
        JFrame frame = new JFrame("hello");
        frame.setSize(300, 300);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new HelloSwing();
    }
}
