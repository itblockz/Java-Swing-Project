package source;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.event.KeyAdapter;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {
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
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Enemy> toRemove = new ArrayList<>();
    private Map<Enemy, Integer> bonus = new HashMap<>();
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

    public Game() {
        f = new JFrame("Size Control");
        f.setSize(600, 700);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailComponents();
        f.setVisible(true);
    }

    private void detailComponents() {
        spawnTimer = new Timer(250, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = random.nextInt(580) + 10;
                int radius = (random.nextInt(5)+2)*10;
                enemies.add(new Enemy(x, 0, radius));
            }
        }); // spawnTimer
        circleTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Enemy c : enemies) {
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
                for (Enemy c : enemies) {
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
                enemies.removeAll(toRemove);
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
            + File.separator + "gameOver.png"
        );
        sizeControl = Toolkit.getDefaultToolkit().createImage(
            System.getProperty("user.dir")
            + File.separator + "source"
            + File.separator + "images"
            + File.separator + "sizeControl.png"
        );
        GamePanel graphic = new GamePanel(this);
        JPanel p = graphic.getPanel();
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
        enemies.clear();
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

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getScore() {
        return score;
    }

    public int getLastDraw() {
        return lastDraw;
    }

    public List<Enemy> getBonus() {
        return bonus.keySet().stream().collect(Collectors.toList());
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