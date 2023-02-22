package source;


import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
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
        Circle player = new Circle(40, 600, 10, 200, Color.YELLOW);
        List<Circle> list = new ArrayList<>();
        Circle a = new Circle(60, 50, 50, 500, Color.WHITE);
        list.add(a);
        f.add(new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
                draw(player, g);
                draw(a, g);
                if (collision(player, list).size() == 0) {
                    gravity(a, true);
                }
                else {
                    gravity(a, false);
                }
            }

            private List<Circle> collision(Circle circle, List<Circle> list) {
                List<Circle> collider = new ArrayList<>();
                for (Circle c : list) {
                    if (circle.getRadius()+c.getRadius() >= circle.getDistance(c)) {
                        collider.add(c);
                    }
                }
                return collider;
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

