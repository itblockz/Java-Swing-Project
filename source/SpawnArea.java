package source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpawnArea extends App {

    private int x;

    public SpawnArea (int x) {
        this.x = x;
    }

    public void spawnArea() {
        List<SpawnArea> list = new ArrayList<>();

        SpawnArea a = new SpawnArea(100);
        SpawnArea b = new SpawnArea(200);
        SpawnArea c = new SpawnArea(300);
        SpawnArea d = new SpawnArea(400);
        SpawnArea e = new SpawnArea(500);

        // switch case



        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);

    }

    int y = (int)(Math.random()*5)+1; // สุ่มเพื่อหาเคส


}