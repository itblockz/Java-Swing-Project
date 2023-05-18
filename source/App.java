package source;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {
    private JFrame frame;
    private JPanel panel;

    public App() {
        frame = new JFrame("Size Control");
        frame.setSize(600, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailComponents();
        frame.setVisible(true);
    }

    private void detailComponents() {
        frame.add(panel);
    }
}