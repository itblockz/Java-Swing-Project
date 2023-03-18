package source;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.event.KeyAdapter;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class App {
    private JFrame f;
    private JPanel p;
    private boolean isGameOver;
    private boolean isStarted;
    private Timer spawnTimer;
    private Timer circleTimer;
    private Timer playerTimer;
    private Timer scoreTimer;
    private Player player;
    private EnterKeyListener enter = new EnterKeyListener();
    private ArrowKeyListener arrow = new ArrowKeyListener();
    private ArrayList<Circle> list = new ArrayList<>();
    private ArrayList<Circle> toRemove = new ArrayList<>();
    private int seed;
    private Random random;
    private boolean isPlayerActive;
    private boolean isCircleActive;
    private boolean isScoreActive;
    private Image happyImage;
    private Image sadImage;
    private Image evilImage;
    private Image evilSmileImage;
    private Image background;
    private int score;

    public App() {
        f = new JFrame("Keeky");
        f.setSize(600, 700);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailComponents();
        f.setVisible(true);
    }

    private void detailComponents() {
        p = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                // g.drawImage(background, 0, 0, null);
                if (isPlayerActive) {
                    player.draw(g);
                }
                if (isCircleActive) {
                    for (Circle c : list) {
                        c.draw(g);
                    }
                }
                if (isScoreActive) {
                    drawScore(g);
                }
                if (!isStarted) {
                    drawPrepareGame(g);
                }
                if (!isGameOver) {
                    repaint();
                } else {
                    drawGameOver(g);
                }
            }
        }; // JPanel
        spawnTimer = new Timer(250, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = random.nextInt(580) + 10;
                int radius = (random.nextInt(5)+1)*10;
                list.add(new Circle(x, 0, radius, evilImage));
            }
        }); // spawnTimer
        circleTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Circle c : list) {
                    c.translate(0, c.getSpeed()+5);
                }
            } 
        }); // circleTimer
        scoreTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score++;
            }
        }); // scoreTimer
        playerTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getSpeed() < 0 && player.getX()-player.getRadius() > 0) {
                    player.translate(player.getSpeed(), 0);
                } else if (player.getSpeed() > 0 && player.getX()+player.getRadius() < p.getWidth()) {
                    player.translate(player.getSpeed(), 0);
                }
                toRemove.clear();
                for (Circle c : list) {
                    if ((player.isCollide(c) && player.compare(c) > 0)|| c.getY() - c.getRadius() > p.getHeight()) {
                        toRemove.add(c);
                    } else if (player.isCollide(c)) {
                        c.setImage(evilSmileImage);
                        gameover();
                        break;
                    }
                }
                list.removeAll(toRemove);
            }
        }); // playerTimer
        happyImage = Toolkit.getDefaultToolkit().createImage(
            System.getProperty("user.dir") + File.separator + "source" + File.separator + "happy.png"
        );
        sadImage = Toolkit.getDefaultToolkit().createImage(
            System.getProperty("user.dir") + File.separator + "source" + File.separator + "sad.png"
        );
        evilImage = Toolkit.getDefaultToolkit().createImage(
            System.getProperty("user.dir") + File.separator + "source" + File.separator + "evil.png"
        );
        background = Toolkit.getDefaultToolkit().createImage(
            System.getProperty("user.dir") + File.separator + "source" + File.separator + "background.jpg"
        );
        f.add(p);
        p.setBackground(Color.DARK_GRAY);
        newGame();
        isPlayerActive = true;
        f.addKeyListener(enter);
        playerTimer.start();
    } // detailComponents
    
    private void newGame() {
        seed = new Random().nextInt();
        random = new Random(seed);
        isGameOver = false;
        player = new Player(300, 600, 10, happyImage);
        score = 0;
        list.clear();
        f.addKeyListener(arrow);
    }

    private void play() {
        f.removeKeyListener(enter);
        isStarted = true;
        isPlayerActive = true;
        isCircleActive = true;
        isScoreActive = true;
        circleTimer.start();
        spawnTimer.start();
        playerTimer.start();
        scoreTimer.start();
    }

    private void gameover() {
        circleTimer.stop();
        spawnTimer.stop();
        playerTimer.stop();
        scoreTimer.stop();
        isGameOver = true;
        player.setImage(sadImage);
        f.addKeyListener(enter);
    }

    private void drawScore(Graphics g) {
        g.setFont(p.getFont().deriveFont(20.0f));
        g.setColor(Color.YELLOW);
        g.drawString("Score " + score, 20, 50);
    }

    private void drawPrepareGame(Graphics g) {
        g.setFont(p.getFont().deriveFont(70.0f));
        g.setColor(Color.PINK);
        g.drawString("KEEKY", 180, 300);

        g.setFont(p.getFont().deriveFont(20.0f));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Press Enter to Start", 200, 400);
    }
    
    private void drawGameOver(Graphics g) {
        g.setFont(p.getFont().deriveFont(70.0f));
        g.setColor(Color.PINK);
        g.drawString("Game Over", 120, 350);

        g.setFont(p.getFont().deriveFont(20.0f));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Press Enter to Restart", 200, 400);
    }

    private class ArrowKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                player.setSpeed(-1);
            } else if (key == KeyEvent.VK_RIGHT) {
                player.setSpeed(1);
            }
            if (key == KeyEvent.VK_UP) {
                if (player.getRadius() < 40) {
                    player.changeRadius(10);
                }
            } else if (key == KeyEvent.VK_DOWN) {
                if (player.getRadius() > 10) {
                    player.changeRadius(-10);
                }
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                player.setSpeed(0);
            }
        }
    }

    private class EnterKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                System.out.println("play");
                newGame();
                play();
                p.repaint();
            }
        }
    }
} // App2