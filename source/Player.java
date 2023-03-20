package source;

import java.awt.Image;

public class Player extends Circle {

    public Player(int x, int y, int radius, Image img) {
        super(x, y, radius, img);
        setSpeed(0);
    }

    @Override
    public void setColor() {
        setColor(1);
    }
}