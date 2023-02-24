package source;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class App {
    private JFrame f;
    public App(){
        f = new JFrame("Game");
        f.setSize(600, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailComponents();
        f.setVisible(true);
    }

    private void detailComponents() {
        int speed = 500;
        Circle player = new Circle(100, 500, 60, Color.YELLOW); // can delete it's example
        List<Circle> list = new ArrayList<>();
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Random Here
                list.add(new Circle(80, 0, 50, Color.WHITE)); // can delete it's example
            } 
         });
        timer.start();
        f.add(new JPanel() {
            boolean isGameOver = false;

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
                if (!isGameOver) {
                    play(g);
                } else {
                    gameover(g);
                }
            }

            private void gameover(Graphics g) {
                timer.stop();
                draw(player, g);
                for (Circle c : list) {
                    draw(c, g);
                }
                // Game Over Graphic
                g.setFont(getFont().deriveFont(70.0f));
                g.setColor(Color.PINK);
                g.drawString("Game Over", 120, getHeight()/2);
            }

            private void play(Graphics g) {
                draw(player, g);
                for (Circle c : list) {
                    draw(c, g);
                    gravity(c);
                }
                sleep(1000/speed);
                for(Circle c : collider(player, list)) {
                    if (player.getRadius() > c.getRadius()) {
                        list.remove(c);
                    } else {
                        isGameOver = true;
                    }
                }
                repaint();
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

            private void gravity(Circle c) {
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
        });
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

