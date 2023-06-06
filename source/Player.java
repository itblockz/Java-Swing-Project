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
        f.addKeyListener(new ControlKeyListener());
    }

    @Override
    public void lose() {
        isLose = true;
        setFace(3);
    }

    private class ControlKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (isLose) return;
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                setSpeed(-1);
            } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                setSpeed(1);
            }
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                if (getRadius() < MAX - 10) {
                    changeRadius(10);
                }
            } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                if (getRadius() > MAX - 40) {
                    changeRadius(-10);
                }
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            if (isLose) return;
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
                setSpeed(0);
            }
        }
    }
}