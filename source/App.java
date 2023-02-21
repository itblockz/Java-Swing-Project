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
            Circle a = new Circle(20, 50, 10, 200, Color.WHITE);

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
                draw(a, g);
                if (a.getY() < 500) gravity(a, true);
                else gravity(a, false);
            }

            private void gravity(Circle c, boolean hasGravity) {
                if (!hasGravity) return;
                if (c.getY() < getHeight() - (c.getRadius() * 2) - 5) {
                    sleep(1000/c.getSpeed());
                    c.translate(0, 1);
                    repaint();
                }
            }

            private void draw(Circle c, Graphics g) {
                g.setColor(c.getColor());
                g.fillOval(c.getX(), c.getY(), c.getRadius() * 2, c.getRadius() * 2);
            }

            private void sleep(int ms) {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        });
    }
}

