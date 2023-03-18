package source;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    private JFrame f;
    private JPanel p;
    private List<Circle> list;
    private List<Circle> toRemove;
    private Circle player;
    private Timer dropTimer;
    private Timer scoreTimer;
    private Timer playerTimer;
    private Timer circleTimer;
    private Random rand;
    private int seed;
    private int score;
    private Image img;
    private boolean gameStart = false;

    public App() {
        f = new JFrame("Game");
        f.setSize(600, 700);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailComponents();
        f.setVisible(true);
    }

    private void detailComponents() {
        seed = new Random().nextInt();
        player = new Circle(300, 600, 10, Color.getHSBColor(0.5f, 1, 1));
        rand = new Random(seed);
        list = new ArrayList<>();
        toRemove = new ArrayList<>();
        dropTimer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ball = 2;
                int x = 0;
                int radius = 0;
                Color color;

                for (int p = 0; p < ball; p++) {
                    int random = rand.nextInt(5) + 1; // สุ่มเพื่อหาเคส
                    switch (random) {
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
                    x = rand.nextInt(581) + 10;
                    color = Color.getHSBColor((6 - random) * 0.1f, 1, 0.5f);
                    list.add(new Circle(x, 0, radius, color));
                }
            }
        }); // dropTimer
        scoreTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score++;
            }
        }); // scoreTimer
        playerTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getSpeed() < 0 && player.getX() - player.getRadius() > 0) {
                    player.translate(player.getSpeed(), 0);
                } else if (player.getSpeed() > 0 && player.getX() + player.getRadius() < p.getWidth()) {
                    player.translate(player.getSpeed(), 0);
                }
            }
        }); // playerTimer
        circleTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Circle c : list) {
                    c.translate(0, (50 / c.getRadius()) + 4);
                }
            }
        }); // circleTimer
        img = Toolkit.getDefaultToolkit().createImage(
                System.getProperty("user.dir") + File.separator + "source" + File.separator + "CS.png");

        playerTimer.start();

        f.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    dropTimer.start();
                    scoreTimer.start();
                    circleTimer.start();
                    gameStart = true;
                    System.out.println("hi");
                    // Spacebar was pressed
                    // Add your code here
                }
            }
        });

        // Set the panel as focusable so it can receive keyboard events
        f.setFocusable(true);

        p = new JPanel() {
            boolean isGameOver = false;

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
                g.drawImage(img, 300, 300, 256, 256, null); // img
                if (!gameStart) {
                    idle(g);
                } 

                else if (!isGameOver) {
                    play(g);
                }

                else {
                    gameover(g);
                }
            }

            private void idle(Graphics g) {
                g.setFont(getFont().deriveFont(20.0f));
                g.setColor(Color.WHITE);
                g.drawString("Press Spacebar to Start ", 180, 250);
                repaint();
            }

            private void gameover(Graphics g) {
                dropTimer.stop();
                scoreTimer.stop();
                playerTimer.stop();
                circleTimer.stop();
                draw(player, g);
                for (Circle c : list) {
                    draw(c, g);
                }
                // Game Over Graphic
                g.setFont(getFont().deriveFont(70.0f));
                g.setColor(Color.PINK);
                g.drawString("Game Over", 120, getHeight() / 2);
                // Score Graphic
                g.setFont(getFont().deriveFont(20.0f));
                g.setColor(Color.YELLOW);
                g.drawString("Score " + score, 20, 50);
            }

            private void play(Graphics g) {
                draw(player, g);
                for (Circle c : list) {
                    draw(c, g);
                    if (c.getY() - c.getRadius() > getHeight()) {
                        toRemove.add(c);
                    }
                }
                list.removeAll(toRemove);
                for (Circle c : collider(player, list)) {
                    if (player.getRadius() > c.getRadius()) {
                        list.remove(c);
                    } else {
                        isGameOver = true;
                    }
                }
                g.setFont(getFont().deriveFont(20.0f));
                g.setColor(Color.YELLOW);
                g.drawString("Score " + score, 20, 50);
                g.setFont(getFont().deriveFont(20.0f));
                g.setColor(Color.YELLOW);

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

            private void draw(Circle c, Graphics g) {
                g.setColor(c.getColor());
                g.fillOval(c.getX() - c.getRadius(), c.getY() - c.getRadius(),
                        c.getRadius() * 2, c.getRadius() * 2);
            }
        }; // JPanel
        f.add(p);
        AllKeyListener kl = new AllKeyListener();
        f.addKeyListener(kl);
    } // detailComponent

    private class AllKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // nothing
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_RIGHT) {
                player.setSpeed(5 - player.getRadius() / 10);
            }
            if (key == KeyEvent.VK_LEFT) {
                player.setSpeed(-(5 - player.getRadius() / 10));
            }
            if (key == KeyEvent.VK_UP) {
                if (player.getRadius() < 40) {
                    player.setRadius(player.getRadius() + 10);
                    player.setColor(Color.getHSBColor((60 - player.getRadius()) * 0.01f, 1, 1));
                    if (player.getSpeed() < 0) {
                        player.setSpeed(player.getSpeed() + 1);
                    } else if (player.getSpeed() > 0) {
                        player.setSpeed(player.getSpeed() - 1);
                    }
                }
            }
            if (key == KeyEvent.VK_DOWN) {
                if (player.getRadius() > 10) {
                    player.setRadius(player.getRadius() - 10);
                    player.setColor(Color.getHSBColor((60 - player.getRadius()) * 0.01f, 1, 1));
                    if (player.getSpeed() < 0) {
                        player.setSpeed(player.getSpeed() - 1);
                    } else if (player.getSpeed() > 0) {
                        player.setSpeed(player.getSpeed() + 1);
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_RIGHT) {
                player.setSpeed(0);
            }
            if (key == KeyEvent.VK_LEFT) {
                player.setSpeed(0);
            }
        }
    } // AllKeyListener
} // App
