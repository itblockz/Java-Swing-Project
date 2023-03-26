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
import java.util.HashMap;
import java.util.Random;

public class App {
    private JFrame f;
    private JPanel p;
    private boolean isGameOver;
    private boolean isStarted;
    private Timer spawnTimer;
    private Timer circleTimer;
    private Timer scoreTimer;
    private Timer speedUpTimer;
    private Timer playerTimer;
    private Timer bonusTimer;
    private Player player;
    private EnterKeyListener enter  = new EnterKeyListener();
    private ArrayList<Enemy> list = new ArrayList<>();
    private ArrayList<Enemy> toRemove = new ArrayList<>();
    private HashMap<Enemy, Integer> bonus = new HashMap<>();
    private int seed;
    private Random random;
    private boolean isPlayerVisible;
    private boolean isCircleVisible;
    private boolean isScoreVisible;
    private Image background;
    private Image gameOver;
    private Image sizeControl;
    private int score;
    private int delay;
    private int lastDraw;

    public App() {
        f = new JFrame("Size Control");
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
                g.drawImage(background, 0, 0, null);
                if (isPlayerVisible) {
                    player.draw(g);
                }
                if (isCircleVisible) {
                    for (Enemy c : list) {
                        c.draw(g);
                    }
                }
                if (isScoreVisible) {
                    drawScore(g);
                    drawBonus(g);
                }
                if (!isStarted) {
                    drawPrepareGame(g);
                }
                if (!isGameOver) {
                    repaint();
                } else {
                    drawGameOver(g);
                    if (lastDraw < 20) {
                        repaint();
                    }
                    lastDraw++;
                }
            }
        }; // JPanel
        spawnTimer = new Timer(250, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = random.nextInt(580) + 10;
                int radius = (random.nextInt(5)+2)*10;
                list.add(new Enemy(x, 0, radius));
            }
        }); // spawnTimer
        circleTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Enemy c : list) {
                    c.translate(0, c.getSpeed()+3);
                }
            } 
        }); // circleTimer
        scoreTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score++;
            }
        }); // scoreTimer
        speedUpTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (delay > 100) {
                    delay -= 50;
                    spawnTimer.setDelay(delay);
                }
            }
        });
        bonusTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toRemove.clear();
                for (var entry : bonus.entrySet()) {
                    entry.setValue(entry.getValue()+1);
                    if (entry.getValue() == 50) {
                        toRemove.add(entry.getKey());
                    }
                }
                for (Enemy c : toRemove) {
                    bonus.remove(c);
                }
            }
        });
        playerTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getSpeed() < 0 && player.getX()-player.getRadius() > 0) {
                    player.translate(player.getSpeed(), 0);
                } else if (player.getSpeed() > 0 && player.getX()+player.getRadius() < p.getWidth()) {
                    player.translate(player.getSpeed(), 0);
                }
                toRemove.clear();
                for (Enemy c : list) {
                    if (c.getY() - c.getRadius() > p.getHeight()) {
                        toRemove.add(c);
                    } else if ((player.isCollide(c) && player.compare(c) > 0)) {
                        toRemove.add(c);
                        score += c.getRadius();
                        bonus.put(c, 0);
                    } else if (player.isCollide(c)) {
                        player.lose();
                        c.win();
                        gameover();
                        break;
                    }
                }
                list.removeAll(toRemove);
            }
        }); // playerTimer
        background = Toolkit.getDefaultToolkit().createImage(
            System.getProperty("user.dir")
            + File.separator + "source"
            + File.separator + "images"
            + File.separator + "background.png"
        );
        gameOver = Toolkit.getDefaultToolkit().createImage(
            System.getProperty("user.dir")
            + File.separator + "source"
            + File.separator + "images"
            + File.separator + "gameOver3.png"
        );
        sizeControl = Toolkit.getDefaultToolkit().createImage(
            System.getProperty("user.dir")
            + File.separator + "source"
            + File.separator + "images"
            + File.separator + "sizeControl2.png"
        );
        f.add(p);
        p.setBackground(Color.DARK_GRAY);
        f.addKeyListener(enter);
        newGame(); 
        isCircleVisible = true;
        isScoreVisible = true;
        isPlayerVisible = true;
        playerTimer.start();
    } // detailComponents
    
    private void newGame() {
        seed = new Random().nextInt();
        random = new Random(seed);
        isGameOver = false;
        player = new Player(300, 600, 20);
        player.addKeyListenerBy(f);
        list.clear();
        score = 0;
        delay = 400;
        spawnTimer.setDelay(delay);
        lastDraw = 0;
    }
    
    private void play() {
        isStarted = true;
        circleTimer.start();
        spawnTimer.start();
        scoreTimer.start();
        speedUpTimer.start();
        bonusTimer.start();
        playerTimer.start();
    }

    private void gameover() {
        circleTimer.stop();
        spawnTimer.stop();
        scoreTimer.stop();
        speedUpTimer.stop();
        bonusTimer.stop();
        playerTimer.stop();
        isGameOver = true;
    }

    private void drawScore(Graphics g) {
        g.setFont(p.getFont().deriveFont(20.0f));
        g.setColor(Color.YELLOW);
        g.drawString("Score " + score, 20, 50);
    }

    private void drawBonus(Graphics g) {
        for (Enemy c : bonus.keySet()) {
            c.drawText(g);
        }
    }

    private void drawPrepareGame(Graphics g) {
        g.setFont(p.getFont().deriveFont(60.0f));
        g.setColor(Color.PINK);
        g.drawString("SIZE", 220, 270);
        g.drawString("CONTROL", 140, 350);

        // g.drawImage(sizeControl, 0, 0, null);
        
        g.setFont(p.getFont().deriveFont(20.0f));
        g.setColor(Color.YELLOW);
        g.drawString("Press Enter to Start", 200, 450);
    }
    
    private void drawGameOver(Graphics g) {
        g.setFont(p.getFont().deriveFont(70.0f));
        g.setColor(Color.PINK);
        g.drawString("Game Over", 120, 350);
        
        // g.drawImage(gameOver, 0, 0, null);

        g.setFont(p.getFont().deriveFont(20.0f));
        g.setColor(Color.YELLOW);
        g.drawString("Press Enter to Restart", 200, 450);
    }

    private class EnterKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (isStarted && !isGameOver) return;
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                newGame();
                play();
                p.repaint();
            }
        }
    }
} // App2