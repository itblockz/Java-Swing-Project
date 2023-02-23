package source;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class App {

    private JFrame f;

    public App() {
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
        Circle b = new Circle(180, 300, 20, Color.WHITE);
        list.add(a);
        list.add(b);
    private void detailComponents() {
        Circle player = new Circle(100, 500, 50, 200, Color.YELLOW); // รายละเอียด player
        List<Circle> list = new ArrayList<>();

        int ball = 5;
        int x = 0;
        int radius = 0;

        for (int p = 0; p < ball; p++){
            int random = (int)(Math.random()*5)+1; // สุ่มเพื่อหาเคส
            int random2 = (int)(Math.random()*5)+1;

            switch(random) {
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

            switch(random2) {
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

            Circle a = new Circle(x, 100, radius, 500, Color.WHITE);
            System.out.println(x);
            list.add(a);

        }

        // list.add(b);
        // list.add(c);
        // list.add(d);
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
                draw(player, g);
                    for (Circle c : list) {
                        draw(c, g);
                    }
                    System.out.println("Game Over");
                    // Game Over Graphic
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
                if (c.getY() < getHeight() - c.getRadius()) {
                    c.translate(0, 1);
                    repaint();
                }
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

        });
    }
}
