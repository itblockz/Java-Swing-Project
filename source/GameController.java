package source;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

public class GameController {
    private Player player;
    private List<Enemy> enemies;
    private Timer spawnTimer;
    private Random random;
    private int seed;

    GameController() {
        seed = new Random().nextInt();
        random = new Random(seed);
        setSpawnTimer(250);
    }

    private void setSpawnTimer(int delay) {
        spawnTimer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spawnEnemy();
            }
        });
    }
    
    private void spawnEnemy() {
        int x = random.nextInt(560) + 20;
        int radius = (random.nextInt(5)+2)*10;
        enemies.add(new Enemy(x, 0, radius));
    }
}
