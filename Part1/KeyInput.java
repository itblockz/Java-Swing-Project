package Part1;
//จัดการ Event Keyboard

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    Player p;
    public KeyInput(Player p){
        this.p = p;
    }

    //กดปุ่มค้าง
    @Override
    public void keyPressed(KeyEvent e) {
        p.keyPressed(e);
    }

    //ปล่อยปุ่ม
    @Override
    public void keyReleased(KeyEvent e) {
        p.keyReleased(e);
    }
}
