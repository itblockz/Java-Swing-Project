package source;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class App2 {
    private JFrame f;
    private JPanel p;
    private boolean isGameOver;
    private Timer spawnTimer;
    private Timer circleTimer;
    private Player player;
    private EnterKeyListener enter = new EnterKeyListener();
    private ArrayList<Circle> list = new ArrayList<>();
    private int seed;
    private Random random;
    private Graphics graphics;

    public App2() {
        f = new JFrame("Game");
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
                g = getGraphics();
                super.paint(g);
            }
        };
        spawnTimer = new Timer(250, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = random.nextInt(581) + 10;
                int radius = (random.nextInt(5)+1)*10;
                Color color = Color.getHSBColor((6-radius/10)*0.1f, 1, 0.5f);
                list.add(new Circle(x, 0, radius, color));
            }
        }); // spawnTimer
        circleTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Circle c : list) {
                    c.translate(0, (6-c.getRadius()/10)+5);
                    c.draw(graphics);
                    p.repaint();
                }
            } 
         }); // circleTimer
        f.add(p);
        f.addKeyListener(enter);
        newGame();
        play();
        p.getGraphics().fillOval(300, 300, 10, 10);
    }
    
    private void newGame() {
        seed = new Random().nextInt();
        random = new Random(seed);
        isGameOver = false;
        p.setBackground(Color.BLACK);
        player = new Player(300, 600, 10, Color.getHSBColor(0.5f, 1, 1));
    }

    private void play() {
        // circleTimer.start();
        // spawnTimer.start();
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
                p.setBackground(Color.GRAY);
                p.repaint();
            }
        }

    }
} // App2
