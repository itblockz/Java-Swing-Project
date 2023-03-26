package source;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

public class Circle {
    private int x;
    private int y;
    private int radius;
    private Color color;
    private int speed;
    private Image[] images;
    private Image[] costumes;
    private Image[] faces;

    public Circle(int x, int y, int r) {
        this.x = x;
        this.y = y;
        radius = r;
        setColor();
        costumes = new Image[5];
        costumes[0] = getImage("costume0.png"); // Mercury
        costumes[1] = getImage("costume1.png"); // Mar
        costumes[2] = getImage("costume2.png"); // Venus
        costumes[3] = getImage("costume3.png"); // Earth
        costumes[4] = getImage("costume4.png"); // Neptune
        faces = new Image[4];
        faces[0] = getImage("face0.png"); // Evil
        faces[1] = getImage("face1.png"); // Smiled Evil
        faces[2] = getImage("face2.png"); // Happy
        faces[3] = getImage("face3.png"); // Sad
        images = new Image[2];
        setCostume();
    }

    private Image getImage(String fileName) {
        return Toolkit.getDefaultToolkit().createImage(
            System.getProperty("user.dir")
            + File.separator + "source"
            + File.separator + "images"
            + File.separator + fileName
        );
    }

    protected void setFace(int n) {
        if (n >= 0 && n < faces.length) {
            images[1] = faces[n];
        }
    }

    private void setCostume() {
        images[0] = costumes[radius/10 - 1];
    }

    public void drawText(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawString("+"+radius, x, y);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x-radius, y-radius, radius*2, radius*2);
        for (Image img : images) {
            g.drawImage(img, x-radius, y-radius, radius*2, radius*2, null);
        }
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
    
    private double getDistance(Circle c) {
        return Math.sqrt((x-c.getX())*(x-c.getX()) + (y-c.getY())*(y-c.getY()));
    }

    public void translate(int x, int y) {
        setPosition(this.x+x, this.y+y);
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
        if (speed > 0) {
            setSpeed(1);
        } else if (speed < 0) {
            setSpeed(-1);
        }
        setCostume();
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
        setCostume();
    }

    public Color getColor() {
        return color;
    }

    protected void setColor() {
        color = Color.getHSBColor((6-radius/10)*0.1f, 1, 1);
    }

    protected void setColor(float b) {
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
    
    @Override
    public String toString() {
        return "Circle [x=" + x + ", y=" + y + ", radius=" + radius + ", color=" + color + "]";
    }  
}