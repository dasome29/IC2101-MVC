import java.util.ArrayList;

public class Utility {
    public static ArrayList<ArrayList<Pixel>> copy(ArrayList<ArrayList<Pixel>> grid) {
        ArrayList<ArrayList<Pixel>> result = new ArrayList<>();
        for (ArrayList<Pixel> pixels : grid) {
            ArrayList<Pixel> temp = new ArrayList<>();
            for (Pixel pixel : pixels) {
                temp.add(pixel);
            }
            result.add(temp);
        }
        return result;
    }

    public static boolean contains(ArrayList<Pixel> pixels, Pixel pixel) {
        for (Pixel p :
                pixels) {
            if (pixel.equals(p)) {
                System.out.println(true);
                return true;
            }
        }
        System.out.println(false);
        return false;
    }
}
