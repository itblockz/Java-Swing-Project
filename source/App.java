package source;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class App {
    private JFrame f;
    private List<Circle> list;
    private List<Circle> toRemove;
    private Timer timer;
    private Circle player;
    private int speed;

    public App(){
        f = new JFrame("Game");
        f.setSize(600, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailComponents();
        f.setVisible(true);
    }

    private void detailComponents() {
        speed = 500;
        player = new Circle(100, 500, 60, Color.YELLOW); // can delete it's example
        list = new ArrayList<>();
        toRemove = new ArrayList<>();
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Random Here
                list.add(new Circle(300, 0, 50, Color.WHITE)); // can delete it's example
            } 
        });
        timer.start();
        f.add(new JPanel() {
            boolean isGameOver = false;
            Image img = Toolkit.getDefaultToolkit().createImage(
                System.getProperty("user.dir") + File.separator + "source" + File.separator + "CS.png"
            );
                 
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
                // g.drawImage(img, 300, 300, 256, 256, null); // img
                System.out.println(list.size());
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
                    if (c.getY() - c.getRadius() > getHeight()){
                        toRemove.add(c);
                    }
                }
                list.removeAll(toRemove);
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
                c.translate(0, 1);
                repaint();
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