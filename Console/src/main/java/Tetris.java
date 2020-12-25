import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tetris {
    public ArrayList<ArrayList<Pixel>> old_grid;


    public ArrayList<ArrayList<Pixel>> getGrid() {
        return grid;
    }

    private Communications comms;

    private void manageBlock(Block block) throws Exception {
        String message = "";
        while (!message.equals("ESCAPE")) {
            message = comms.listen();
            System.out.println(message);
            if (message.equals("D")) {
                block.moveRight();
            }
            if (message.equals("A")) {
                block.moveLeft();
            }
            if (message.equals("SPACE")) {
                block.drop();
                break;
            }

        }

    }

    private boolean checkGameOver() {
        for (Pixel p : grid.get(0)) {
            if (p.getValue() != 0) {
                return true;
            }
        }
        return false;
    }

    public void start() throws Exception {
        Random r = new Random();
        while (true) {
            Block block = null;
            int val = r.nextInt(4);
            if (val == 0) {
                block = new Rectangle(0, 0);
            }
            if (val == 1) {
                block = new Square(0, 0);
            }
            if (val == 2) {
                block = new El(0, 0);
            }
            if (val == 3) {
                block = new Zed(0, 0);
            }
            assert block != null;
            block.setTetris(this);
            if (!placeBlock(block)){
                if (checkGameOver()){
                    break;
                }
            }

            manageBlock(block);

            checkRows();
        }
    }

    private boolean placeBlock(Block block) {

        for (int i = 0; i < 50 - block.width; i++) {
            if (block.setPos(i, 0)) {
                return true;
            }
        }
        return false;

    }

    public void setGrid(ArrayList<ArrayList<Pixel>> grid) {
        this.grid = grid;
    }

    private ArrayList<ArrayList<Pixel>> grid = new ArrayList<>();



    public void compareGrid(ArrayList<ArrayList<Pixel>> actual) {
        JSONArray data = new JSONArray();
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (grid.get(i).get(j).getValue() != actual.get(i).get(j).getValue()) {
                    JSONObject temp = new JSONObject();
                    Pixel pixel = actual.get(i).get(j);
                    temp.put("i", pixel.getI());
                    temp.put("j", pixel.getJ());
                    temp.put("value", pixel.getValue());
                    data.put(temp);
                    System.out.println(temp);
                }
            }
        }
        grid = Utility.copy(actual);
        System.out.println(data);
        try {
            if (data.length()>0){
                comms.send(data.toString());
            }
        } catch (Exception e) {
            System.out.println("Error while sending changes in grid");
        }
    }


    public Tetris(Communications comms) {
        this.comms = comms;
        for (int i = 0; i < 50; i++) {
            ArrayList<Pixel> temp = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                temp.add(new Pixel(i, j, 0));
            }
            grid.add(temp);
        }
    }

    private boolean checkFullRow(int i) {
        for (Pixel p :
                grid.get(i)) {
            if (p.getValue() == 0) {
                return false;
            }
        }
        return true;
    }

    private void pullDown(int limit, ArrayList<ArrayList<Pixel>> grid) {
        for (int i = limit; i > 0; i--) {
            grid.set(i, grid.get(i - 1));
        }
    }

    private ArrayList<Pixel> createEmptyRow(int i) {
        ArrayList<Pixel> temp = new ArrayList<>();
        for (int j = 0; j < 50; j++) {
            temp.add(new Pixel(i, j, 0));
        }
        return temp;
    }

    private void checkRows() {
        ArrayList<ArrayList<Pixel>> temp = Utility.copy(grid);
        for (int i = 49; i >= 0; i--) {
            if (checkFullRow(i)) {
                temp.set(i, createEmptyRow(i));
                pullDown(i, temp);
                compareGrid(temp);
            }
        }
    }


}


class Pixel {
    private int i;
    private int j;
    private int value;

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getValue() {
        return value;
    }

    public Pixel(int i, int j, int value) {
        this.i = i;
        this.j = j;
        this.value = value;
    }
}

