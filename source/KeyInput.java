package source;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter {
    Circle player;
    public KeyInput(Circle player){
        this.player = player;
        System.out.println("keyinput");
    }

    @Override
    public void keyPressed(KeyEvent e){
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e){
        player.keyReleased(e);
    }

}
