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
        Circle player = new Circle(100, 500, 50, 200, Color.YELLOW);
        List<Circle> list = new ArrayList<>();
        Circle a = new Circle(120, 50, 60, 500, Color.WHITE);
        Circle b = new Circle(170, 50, 20, 500, Color.WHITE);
        list.add(a);
        // list.add(b);
        f.add(new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
                draw(player, g);
                for (Circle c : list) {
                    draw(c, g);
                    gravity(c, true);
                }
                for(Circle c : collider(player, list)) {
                    if (player.getRadius() > c.getRadius()) {
                        list.remove(c);
                    } else gravity(c, false);
                }
            }

            private List<Circle> collider(Circle circle, List<Circle> list) {
                List<Circle> col = new ArrayList<>();
                for (Circle c : list) {
                    if (circle.getRadius()+c.getRadius() >= circle.getDistance(c)) {
                        col.add(c);
                    }
                }
                return col;
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
                g.fillOval(c.getX()-c.getRadius(), c.getY()-c.getRadius(),
                            c.getRadius() * 2, c.getRadius() * 2);
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

