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
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class App{
    private JFrame f;
    private List<Circle> list;
    private List<Circle> toRemove;
    private Timer timer;
    private Timer scoreTimer;
    private Circle player;
    private Random rand;
    private int speed;
    private int seed;
    private JPanel p;
    private int score;

    public App(){
        f = new JFrame("Game");
        f.setSize(600, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailComponents();
        f.setVisible(true);
    }

    private void detailComponents() {
        speed = 250;
        seed = new Random().nextInt();
        rand = new Random(seed);
        player = new Circle(300, 600, 10, Color.YELLOW); // can delete it's example
        list = new ArrayList<>();
        toRemove = new ArrayList<>();
        timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ball = 1;
                int x = 0;
                int radius = 0;

                for (int p = 0; p < ball; p++) {
                    int random = rand.nextInt(5)+1; // สุ่มเพื่อหาเคส
                    int random2 = rand.nextInt(5)+1;

                    switch (random) {
                        case 1:
                            x = 100;
                            break;
                        case 2:
                            x = 200;
                            break;
                        case 3:
                            x = 300;
                            break;
                        case 4:
                            x = 400;
                            break;
                        case 5:
                            x = 500;
                    }

                    switch (random2) {
                        case 1:
                            radius = 10;
                            break;
                        case 2:
                            radius = 20;
                            break;
                        case 3:
                            radius = 30;
                            break;
                        case 4:
                            radius = 40;
                            break;
                        case 5:
                            radius = 50;
                    }
                    list.add(new Circle(x, 0, radius, Color.WHITE));
                }
            }
        });
        scoreTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score++;
            }
        });
        timer.start();
        scoreTimer.start();
        p = new JPanel() {
            boolean isGameOver = false;
            Image img = Toolkit.getDefaultToolkit().createImage(
                System.getProperty("user.dir") + File.separator + "source" + File.separator + "CS.png"
            );
                 
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
                // g.drawImage(img, 300, 300, 256, 256, null); // img
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
                g.drawString("Game Over", 120, getHeight() / 2);

                g.setFont(getFont().deriveFont(20.0f));
                g.setColor(Color.YELLOW);
                g.drawString("Score " + score, 20, 50);
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
                g.setFont(getFont().deriveFont(20.0f));
                g.setColor(Color.YELLOW);
                g.drawString("Score " + score, 20, 50);
                repaint();
            }

            private List<Circle> collider(Circle circle, List<Circle> list) {
                List<Circle> col = new ArrayList<>();
                for (Circle c : list) {
                    if (circle.getRadius() + c.getRadius() >= circle.getDistance(c)) {
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
                g.fillOval(c.getX() - c.getRadius(), c.getY() - c.getRadius(),
                        c.getRadius() * 2, c.getRadius() * 2);
            }

            private void sleep(int ms) {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }; // JPanel
        f.add(p);
        AllKeyListener kl = new AllKeyListener();
        f.addKeyListener(kl);
    }

    private class AllKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("typed");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
        
    }
    
}
