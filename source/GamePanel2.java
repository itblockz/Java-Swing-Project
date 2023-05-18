package source;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

public class GamePanel2 extends JPanel {
    private JPanel panel;
    private boolean isPlayerVisible;
    private boolean isCircleVisible;
    private boolean isScoreVisible;
    private boolean isStarted;
    private boolean isGameOver;
    private Image background;
    private Image gameOver;
    private Image sizeControl; 
    private Player player;
    private List<Enemy> enemies;
    private int score;
    private int lastDraw;
    private List<Enemy> bonus;

    GamePanel2(Game game) {
        player = game.getPlayer();
        enemies = game.getEnemies();
        score = game.getScore();
        lastDraw = game.getLastDraw();
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(background, 0, 0, null);
                if (isPlayerVisible) {
                    player.draw(g);
                }
                if (isCircleVisible) {
                    for (Enemy c : enemies) {
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
                    if (lastDraw < 50) {
                        repaint(); // fix image missing
                    }
                    lastDraw++;
                }
            }
        };
    }

    private void drawScore(Graphics g) {
        g.setFont(panel.getFont().deriveFont(20.0f));
        g.setColor(Color.YELLOW);
        g.drawString("Score " + score, 20, 50);
    }

    private void drawBonus(Graphics g) {
        for (Enemy e : bonus) {
            e.drawText(g);
        }
    }

    private void drawPrepareGame(Graphics g) {
        g.setFont(panel.getFont().deriveFont(60.0f));
        g.setColor(Color.PINK);
        g.drawString("SIZE", 220, 270);
        g.drawString("CONTROL", 140, 350);

        g.drawImage(sizeControl, 0, 0, null);
        
        g.setFont(panel.getFont().deriveFont(20.0f));
        g.setColor(Color.YELLOW);
        g.drawString("Press Enter to Start", 200, 500);
    }
    
    private void drawGameOver(Graphics g) {
        g.setFont(panel.getFont().deriveFont(70.0f));
        g.setColor(Color.PINK);
        g.drawString("Game Over", 120, 350);
        
        g.drawImage(gameOver, 0, 0, null);

        g.setFont(panel.getFont().deriveFont(20.0f));
        g.setColor(Color.YELLOW);
        g.drawString("Press Enter to Restart", 200, 500);
    }

    public JPanel getPanel() {
        return panel;
    }
}
