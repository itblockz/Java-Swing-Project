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
        int speed = 500;
        Circle player = new Circle(100, 500, 60, Color.YELLOW);
        List<Circle> list = new ArrayList<>();
        Circle a = new Circle(80, 50, 70, Color.WHITE);
        Circle b = new Circle(180, 50, 20, Color.WHITE);
        list.add(a);
        list.add(b);
        f.add(new JPanel() {
            boolean gameover = false;

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
                draw(player, g);
                if (!gameover) {
                    for (Circle c : list) {
                        draw(c, g);
                        gravity(c, true);
                    }
                    sleep(1000/speed);
                    for(Circle c : collider(player, list)) {
                        if (player.getRadius() > c.getRadius()) {
                            list.remove(c);
                        } else {
                            gameover = true;
                        }
                    }
                } else {
                    for (Circle c : list) {
                        draw(c, g);
                        gravity(c, false);
                    }
                    System.out.println("Game Over");
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
                if (c.getY() < getHeight() - c.getRadius()) {
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

