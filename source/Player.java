package source;

import java.awt.Color;
import java.awt.Image;

public class Player extends Circle {

    public Player(int x, int y, int radius, Image img) {
        super(x, y, radius, img);
        speed = 0;
    }

    @Override
    public void setColor() {
        color = Color.getHSBColor((6-radius/10)*0.1f, 1, 1);
    }
}
