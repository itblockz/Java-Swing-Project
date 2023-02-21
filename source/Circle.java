package source;

import java.awt.Color;

public class Circle {
    private int x;
    private int y;
    private int radius;
    private int speed;
    private Color color;

    public Circle(int x, int y, int radius, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;
        this.color = color;
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
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        if (speed > 1) {
            this.speed = speed;
        } else {
            speed = 1;
        }
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
}