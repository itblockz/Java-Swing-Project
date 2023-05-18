package source;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class SpawnTimer extends Timer {
    private ActionListener al;
    
    SpawnTimer(int delay) {
        super(delay, al);
        al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnEnemy();
            }
        }
    }

    private void spawnEnemy() {
        int x = random.nextInt(560) + 20;
        int radius = (random.nextInt(5)+2)*10;
        enemies.add(new Enemy(x, 0, radius));
    }
}
