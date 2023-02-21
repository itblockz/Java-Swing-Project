package Part1;
//ออกแบบ player

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Player {
    private int x;
    private int y;
    //กำหนดความเร็วการเคลื่อนที่ในแนว แกนx,y
    private int speedx = 0;
    private int speedy = 0;

    public Player(int x, int y){
        this.x = x;
        this.y = y;
    }
    //เมธอดจัดการการเคลื่อนที่ของผู้เล่น จะทำงานตลอดเวลา
    public void update(){
        x += speedx;
        y += speedy;
    }
    //วาดตัวละครผู้เล่น
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.darkGray);
        g2d.fillRect(x, y, 32, 32);
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT){
            speedx = 5;
        }
        if(key == KeyEvent.VK_LEFT){
            speedx = -5;
        }
        if(key == KeyEvent.VK_DOWN){
            speedy = 5;
        }
        if(key == KeyEvent.VK_UP){
            speedy = -5;
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT){
            speedx = 0;
        }
        if(key == KeyEvent.VK_LEFT){
            speedx = 0;
        }
        if(key == KeyEvent.VK_DOWN){
            speedy = 0;
        }
        if(key == KeyEvent.VK_UP){
            speedy = 0;
        }
    }
}
