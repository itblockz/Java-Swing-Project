package source;

import java.awt.Color;
import java.awt.Graphics;

public class Point {
    private int x;
    private int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawString(Graphics g, String str, Color c) {
        g.setColor(c);
        g.drawString(str, x, y);
    }
}
