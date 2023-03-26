package source;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

@FunctionalInterface
interface Losable {
    public void lose();
}

public class Player extends Circle implements Losable{
    private boolean isLose;
    
    public Player(int x, int y, int radius) {
        super(x, y, radius);
        setSpeed(0);
        setFace(2);
        isLose = false;
    }

    public void addKeyListenerBy(JFrame f) {
        KeyListener[] l = f.getKeyListeners();
        for (int i = 1; i < l.length; i++) {
            f.removeKeyListener(l[i]);
        }
        f.addKeyListener(new ArrowKeyListener());
    }

    @Override
    public void lose() {
        isLose = true;
        setFace(3);
    }

    private class ArrowKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (isLose) return;
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                setSpeed(-1);
            } else if (key == KeyEvent.VK_RIGHT) {
                setSpeed(1);
            }
            if (key == KeyEvent.VK_UP) {
                if (getRadius() < MAX - 10) {
                    changeRadius(10);
                }
            } else if (key == KeyEvent.VK_DOWN) {
                if (getRadius() > MAX - 40) {
                    changeRadius(-10);
                }
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            if (isLose) return;
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                setSpeed(0);
            }
        }
    }
}