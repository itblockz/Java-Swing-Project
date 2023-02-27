package source;

import java.awt.Color;
import java.awt.event.KeyEvent;


public class Circle {
    private int x;
    private int y;
    private int radius;
    private Color color;
    private int speedx;

    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        speedx = 0;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT){
            if(x + radius < 585){
                speedx = 5;
            }else{
                speedx = 0;
            }
        }
        if(key == KeyEvent.VK_LEFT){
            if(x - radius > 0){
                speedx = -5;
            }else{
                speedx = 0;
            }
        }
        if(key == KeyEvent.VK_UP){
            if(radius < 40){
                radius += 10;
            }
        }
        if(key == KeyEvent.VK_DOWN){
            if(radius > 10){
                radius -= 10;
            }
        }
        translate(speedx, 0);
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT){
            speedx = 0;
        }
        if(key == KeyEvent.VK_LEFT){
            speedx = 0;
        }
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

    @Override
    public String toString() {
        return "Circle [x=" + x + ", y=" + y + ", radius=" + radius + ", color=" + color + "]";
    }

    public int getSpeedx() {
        return speedx;
    }

    public void setSpeedx(int speedx) {
        this.speedx = speedx;
    }
    
}