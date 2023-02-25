package Part1;
//วาดภาพกราฟิกบนจอเกม

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener{
    Timer loop;
    Player player;
    public Game(){
        loop = new Timer(10, this);
        loop.start();
        player = new Player(100, 100);
        addKeyListener(new KeyInput(player));
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        player.draw(g2d);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        repaint();        
    }

    
}
