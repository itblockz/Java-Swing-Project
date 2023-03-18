package source;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private Timer spawnTimer;
    private Timer scoreTimer;
    private Timer playerTimer;
    private Timer circleTimer;
    private Timer increaseSpeedTimer;
    private Random rand;
    private int seed;
    private int score;
    private Image img;
    private int speed;
    private AllKeyListener akl;
    private EnterKeyListener ekl;

    public App() {
        f = new JFrame("Game");
        f.setSize(600, 700);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailComponents();
        f.setVisible(true);
    }

    private void detailComponents() {
        akl = new AllKeyListener();
        ekl = new EnterKeyListener();
        toRemove = new ArrayList<>();
        p = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
            }
        };
        f.add(p);
        newGame();
        f.addKeyListener(ekl);
    }

    private void newGame() {
        // speed = 3;
        seed = new Random().nextInt();
        player = new Circle(300, 600, 10, Color.getHSBColor(0.5f, 1, 1));
        rand = new Random(seed);
        list = new ArrayList<>();
        spawnTimer = new Timer(250, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = rand.nextInt(581) + 10;
                int radius = (rand.nextInt(5)+1)*10;
                Color color = Color.getHSBColor((6-radius/10)*0.1f, 1, 0.5f);
                list.add(new Circle(x, 0, radius, color));
            }
        }); // spawnTimer
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
                    c.translate(0, (6-c.getRadius()/10)+5);
                }
           } 
        }); // circleTimer
        // increaseSpeedTimer = new Timer(10000, new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         if (speed < 8) {
        //             speed++;
        //             System.out.println(speed);
        //         }
        //     }
        // }); 
        img = Toolkit.getDefaultToolkit().createImage(
            System.getProperty("user.dir") + File.separator + "source" + File.separator + "CS.png"
        );
        // increaseSpeedTimer.start();
        p = new JPanel() {
            boolean isGameOver = false;
                 
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                setBackground(Color.BLACK);
                g.drawImage(img, 300, 300, 256, 256, null); // img
                if (!isGameOver) {
                    play(g);
                } else {
                    gameover(g);
                }
            }

            private void gameover(Graphics g) {
                f.removeKeyListener(akl);
                spawnTimer.stop();
                scoreTimer.stop();
                playerTimer.stop();
                circleTimer.stop();
                // increaseSpeedTimer.stop();
                draw(player, g);
                for (Circle c : list) {
                    draw(c, g);
                }
                // Game Over Graphic
                g.setFont(getFont().deriveFont(70.0f));
                g.setColor(Color.PINK);
                g.drawString("Game Over", 120, getHeight()/2);
                // Score Graphic
                g.setFont(getFont().deriveFont(20.0f));
                g.setColor(Color.YELLOW);
                g.drawString("Score " + score, 20, 50);
                // Restart Graphic
                g.setFont(getFont().deriveFont(20.0f));
                g.setColor(Color.LIGHT_GRAY);
                g.drawString("Press Enter to Restart", 200, 400);
                f.addKeyListener(ekl);
            }

            private void play(Graphics g) {
                draw(player, g);
                toRemove.clear();
                for (Circle c : list) {
                    draw(c, g);
                    if (c.getY() - c.getRadius() > getHeight()){
                        toRemove.add(c);
                    }
                }
                list.removeAll(toRemove);
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

            private void draw(Circle c, Graphics g) {
                g.setColor(c.getColor());
                g.fillOval(c.getX() - c.getRadius(), c.getY() - c.getRadius(),
                        c.getRadius() * 2, c.getRadius() * 2);
            }
        }; // JPanel
        f.add(p);
        f.addKeyListener(akl);
        spawnTimer.start();
        scoreTimer.start();
        playerTimer.start();
        circleTimer.start();
    }
    private class EnterKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // nothing
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // nothing
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                System.out.println("restart");
                p = new JPanel() {
                    @Override
                    public void paint(Graphics g) {
                        super.paint(g);
                        setBackground(Color.GRAY);
                    }
                };
                f.add(p);
            }
        }

    }
    private class AllKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // nothing
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_RIGHT) {
                player.setSpeed(5-player.getRadius()/10);
            } else if (key == KeyEvent.VK_LEFT) {
                player.setSpeed(-(5-player.getRadius()/10));
            }else if (key == KeyEvent.VK_UP) {
                if (player.getRadius() < 40) {
                    player.setRadius(player.getRadius()+10);
                    player.setColor(Color.getHSBColor((60-player.getRadius())*0.01f, 1, 1));
                    if (player.getSpeed() < 0) {
                        player.setSpeed(player.getSpeed()+1);
                    } else if (player.getSpeed() > 0) {
                        player.setSpeed(player.getSpeed()-1);
                    }
                }
            } else if (key == KeyEvent.VK_DOWN) {
                if (player.getRadius() > 10) {
                    player.setRadius(player.getRadius()-10);
                    player.setColor(Color.getHSBColor((60-player.getRadius())*0.01f, 1, 1));
                    if (player.getSpeed() < 0) {
                        player.setSpeed(player.getSpeed()-1);
                    } else if (player.getSpeed() > 0) {
                        player.setSpeed(player.getSpeed()+1);
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_RIGHT){
                player.setSpeed(0);
            }
            if(key == KeyEvent.VK_LEFT){
                player.setSpeed(0);
            }
        }
    } // AllKeyListener

} // App
