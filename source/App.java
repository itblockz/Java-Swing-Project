package source;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;

public class App {
    private JFrame f;
    public App(){
        f = new JFrame("Game");
        f.setSize(600, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailComponents();
        f.setVisible(true);
    }

    private void detailComponents(){
        f.add(new JPanel() {
            int xPos = 20;
            int yPos = 50;
            int radius = 10;

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
                g.setColor(Color.YELLOW);
                g.fillOval(xPos, yPos, radius * 2, radius * 2);
            }
        });
    }
}

