package source;

@FunctionalInterface
interface Winnable {
    void win();
}

public class Enemy extends Circle implements Winnable{
    public Enemy(int x, int y, int radius) {
        super(x, y, radius);
        setSpeed(1);
        setFace(0);
    }

    @Override
    public void win() {
        setFace(1);
    }

    @Override
    public void setColor() {
        setColor(0.5f);
    }
}
