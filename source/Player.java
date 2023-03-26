package source;

@FunctionalInterface
interface Losable {
    public void lose();
}

public class Player extends Circle implements Losable{

    public Player(int x, int y, int radius) {
        super(x, y, radius);
        setSpeed(0);
        setFace(2);
    }

    @Override
    public void lose() {
        setFace(3);
    }
}