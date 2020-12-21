import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;


public class Screen {
    private Window window;
    private ArrayList<ArrayList<Pixel>> pixels = new ArrayList<>();


    public Screen(Window window) {
//        Color[] colors = {Color.BLACK, Color.WHITE, Color.BLUE, Color.CYAN, Color.GREEN, Color.GREY, Color.LIME,
//                Color.RED, Color.PINK, Color.PURPLE, Color.CORAL, Color.BEIGE, Color.AQUAMARINE, Color.DARKCYAN,
//                Color.BROWN, Color.NAVY};

        Color[] colors =
                {Color.valueOf("#61A0AF"), Color.valueOf("#96C9DC"), Color.valueOf("#F06C9B"), Color.valueOf("#CBF3F0"),
                        Color.valueOf("#04E762"), Color.valueOf("#DC0073"), Color.valueOf("#008BF8"), Color.valueOf("#6C464F"),
                        Color.valueOf("#9FA4C4"), Color.valueOf("#B3CDD1"), Color.valueOf("#C7F0BD"), Color.valueOf("#20063B"),
                        Color.valueOf("#FFFFFF"), Color.valueOf("#FFD166"), Color.valueOf("#8F8073"), Color.valueOf("#A682FF")};
        this.window = window;
        for (int i = 0; i < 50; i++) {
            ArrayList<Pixel> temp = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                temp.add(new Pixel(i, j, window));
            }
            pixels.add(temp);
        }
    }

    public void update(String string) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(string);
        } catch (JSONException err) {
            err.printStackTrace();
        }
        assert jsonArray != null;
        for (int i = 0; i < jsonArray.length() - 1; i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            System.out.println(jsonObj);
        }
    }
}

class Pixel {
    private int i;
    private int j;
    private Window window;
    private Rectangle rectangle;

    public Pixel(int i, int j, Window window) {
        this.i = i;
        this.j = j;
        this.window = window;
        rectangle = new Rectangle(15 * this.i, 15 * this.j, Color.valueOf("#12130F"));
        this.window.pane.getChildren().addAll(rectangle);
    }
}