package source;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Circle {
    private int x;
    private int y;
    private int radius;
    private Color color;
    private int speed;
    private Image image;

    public Circle(int x, int y, int r, Image img) {
        this.x = x;
        this.y = y;
        radius = r;
        image = img;
        setColor();
        setSpeed(1);
    }

    public void drawText(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawString("+"+radius, x, y);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x-radius, y-radius, radius*2, radius*2);
        g.drawImage(image, x-radius, y-radius, radius*2, radius*2, null);
    }

    public int compare(Circle c) {
        return radius - c.radius;
    }

    public boolean isCollide(Circle c) {
        if (radius + c.radius >= getDistance(c)) {
            return true;
        }
        return false;
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

    public void setRadius(int r) {
        if (radius > 1) {
            radius = r;
        } else {
            radius = 1;
        }
        setColor();
    }

    public void changeRadius(int r) {
        if (radius + r > 0) {
            radius += r;
        }
        setColor();
        if (speed > 0) {
            setSpeed(1);
        } else if (speed < 0) {
            setSpeed(-1);
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor() {
        color = Color.getHSBColor((6-radius/10)*0.1f, 1, 0.8f);
    }

    public void setColor(float b) {
        color = Color.getHSBColor((6-radius/10)*0.1f, 1, b);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int spd) {
        if (spd > 0) {
            speed = 6 - radius/10;
        } else if (spd < 0) {
            speed = radius/10 - 6;
        } else {
            speed = 0;
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        image = img;
    }
    
    @Override
    public String toString() {
        return "Circle [x=" + x + ", y=" + y + ", radius=" + radius + ", color=" + color + "]";
    }  
}