import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Block {
    int i;
    int j;
    int width;
    private Tetris tetris;
    boolean flip = false;


    protected boolean setPos(int i, int j) {
        return false;
    }

    public Block(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public boolean canSetBlock() {
        ArrayList<ArrayList<Pixel>> temp = Utility.copy(tetris.getGrid());
        for (Pixel p :
                pixels) {
            if (temp.get(p.getI()).get(p.getJ()).getValue() != 0) {
                return false;
            }
        }
        return true;
    }

    public void setBlock() {
        ArrayList<ArrayList<Pixel>> temp = Utility.copy(tetris.getGrid());
        for (Pixel p :
                pixels) {
            if (temp.get(p.getI()).get(p.getJ()).getValue() != 0) {
                temp.get(p.getI()).set(p.getJ(), p);
            }
        }
        tetris.compareGrid(temp);
    }

    public void setTetris(Tetris tetris) {
        this.tetris = tetris;
    }

    ArrayList<Pixel> pixels = new ArrayList<>();

    public void drop() {
        ArrayList<ArrayList<Pixel>> grid = Utility.copy(tetris.getGrid());
        if (flip){
            flip = false;
            Collections.reverse(pixels);
        }
        while (true) {
            boolean dropping = true;
            for (Pixel p : pixels) {
                if (p.getI() + 1 < 50) {
                    if (grid.get(p.getI() + 1).get(p.getJ()).getValue() != 0 && !Utility.contains(pixels, grid.get(p.getI() + 1).get(p.getJ()))) {
                        dropping = false;
                        break;
                    }
                } else {
                    dropping = false;
                    break;
                }
            }
            if (!dropping) {
                break;
            }
            for (Pixel p : pixels) {
                int i = p.getI();
                int j = p.getJ();
                grid.get(i).set(j, new Pixel(i, j, 0));
                grid.get(i + 1).set(j, p);
                p.setI(i + 1);
            }
            tetris.compareGrid(grid);
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                System.out.println("Problem while doing sleep for 1.5 seconds");
            }
        }
    }

    public void moveRight() {
        ArrayList<ArrayList<Pixel>> grid = Utility.copy(tetris.getGrid());
        if (flip){
            flip = false;
            Collections.reverse(pixels);
        }
        boolean can_move = true;
        for (Pixel p : pixels) {
            if (p.getJ() + 1 < 50) {
                if (grid.get(p.getI()).get(p.getJ() + 1).getValue() != 0 && !Utility.contains(pixels, grid.get(p.getI()).get(p.getJ() + 1))) {
                    can_move = false;
                    break;
                }
            } else {
                can_move = false;
                break;
            }
        }
        if (can_move) {
            for (Pixel p : pixels) {
                int i = p.getI();
                int j = p.getJ();
                grid.get(i).set(j + 1, p);
                p.setJ(j + 1);
                if (Utility.contains(pixels, grid.get(i).get(j))){
                    grid.get(i).set(j, new Pixel(i, j, 0));
                }
            }
            tetris.compareGrid(grid);
        }
    }

    public void moveLeft() {
        ArrayList<ArrayList<Pixel>> grid = Utility.copy(tetris.getGrid());
        if (!flip){
            flip = true;
            Collections.reverse(pixels);
        }
        boolean can_move = true;
        for (Pixel p : pixels) {
            if (p.getJ() - 1 >= 0) {
                if (grid.get(p.getI()).get(p.getJ() - 1).getValue() != 0 && !Utility.contains(pixels, grid.get(p.getI()).get(p.getJ() - 1))) {
                    can_move = false;
                    break;
                }
            } else {
                can_move = false;
                break;
            }
        }
        if (can_move) {
            for (Pixel p : pixels) {
                int i = p.getI();
                int j = p.getJ();
                grid.get(i).set(j - 1, p);
                p.setJ(j - 1);
                if (Utility.contains(pixels, grid.get(i).get(j))){
                    grid.get(i).set(j, new Pixel(i, j, 0));
                }
            }
            tetris.compareGrid(grid);
        }
    }
}

class Rectangle extends Block {


    public Rectangle(int i, int j) {
        super(i, j);
        System.out.println("Created Rectangle");

        Random r = new Random();
        int value = r.nextInt(15) + 1;
        for (int k = 0; k < 4; k++) {
            pixels.add(new Pixel(0, 0, value));
        }
        width = 1;
    }

    protected boolean setPos(int i, int j) {
        pixels.get(3).setI(i);
        pixels.get(2).setI(i + 1);
        pixels.get(1).setI(i + 2);
        pixels.get(0).setI(i + 3);

        pixels.get(3).setJ(j);
        pixels.get(2).setJ(j);
        pixels.get(1).setJ(j);
        pixels.get(0).setJ(j);
        if (canSetBlock()) {
            setBlock();
            return true;
        }
        return false;
    }
}

class Square extends Block {

    public Square(int i, int j) {
        super(i, j);
        System.out.println("Created Square");

        Random r = new Random();
        int value = r.nextInt(15) + 1;
        for (int k = 0; k < 4; k++) {
            pixels.add(new Pixel(0, 0, value));
        }
        width = 2;
    }

    protected boolean setPos(int i, int j) {
        pixels.get(3).setI(i);
        pixels.get(2).setI(i);
        pixels.get(1).setI(i+1);
        pixels.get(0).setI(i+1);

        pixels.get(3).setJ(j);
        pixels.get(2).setJ(j + 1);
        pixels.get(1).setJ(j);
        pixels.get(0).setJ(j+1);
        if (canSetBlock()) {
            setBlock();
            return true;
        }
        return false;
    }
}

class El extends Block {

    public El(int i, int j) {
        super(i, j);
        System.out.println("Created El");

        Random r = new Random();
        int value = r.nextInt(15) + 1;
        for (int k = 0; k < 5; k++) {
            pixels.add(new Pixel(0, 0, value));
        }
        width = 4;
    }

    protected boolean setPos(int i, int j) {
        pixels.get(4).setI(i);
        pixels.get(3).setI(i + 1);
        pixels.get(2).setI(i + 1);
        pixels.get(1).setI(i + 1);
        pixels.get(0).setI(i + 1);

        pixels.get(4).setJ(j);
        pixels.get(3).setJ(j);
        pixels.get(2).setJ(j + 1);
        pixels.get(1).setJ(j + 2);
        pixels.get(0).setJ(j + 3);
        if (canSetBlock()) {
            setBlock();
            return true;
        }
        return false;
    }
}

class Zed extends Block {

    public Zed(int i, int j) {
        super(i, j);
        System.out.println("Created Zed");
        Random r = new Random();
        int value = r.nextInt(15) + 1;
        for (int k = 0; k < 4; k++) {
            pixels.add(new Pixel(0, 0, value));
        }
        width = 3;
    }

    protected boolean setPos(int i, int j) {
        pixels.get(3).setI(i);
        pixels.get(2).setI(i);
        pixels.get(1).setI(i + 1);
        pixels.get(0).setI(i + 1);

        pixels.get(3).setJ(j);
        pixels.get(2).setJ(j + 1);
        pixels.get(1).setJ(j + 1);
        pixels.get(0).setJ(j + 2);
        if (canSetBlock()) {
            setBlock();
            return true;
        }
        return false;
    }
}
