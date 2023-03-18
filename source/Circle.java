package source;

import java.awt.Color;
import java.awt.Graphics;

public class Circle {
    private int x;
    private int y;
    private int radius;
    private Color color;
    private int speed;

    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        speed = 0;
    }

    public void draw(Graphics g) {
        g.fillOval(x, y, radius*2, radius*2);
    }
    
    public double getDistance(Circle c) {
        return Math.sqrt((x-c.getX())*(x-c.getX()) + (y-c.getY())*(y-c.getY()));
    }

    public void translate(int x, int y) {
        setX(this.x + x);
        setY(this.y + y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x > 0) {
            this.x = x;
        } else {
            x = 0;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y > 0) {
            this.y = y;
        } else {
            y = 0;
        }
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        if (radius > 1) {
            this.radius = radius;
        } else {
            radius = 1;
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    @Override
    public String toString() {
        return "Circle [x=" + x + ", y=" + y + ", radius=" + radius + ", color=" + color + "]";
    }

    
}