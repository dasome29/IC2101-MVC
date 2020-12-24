import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class Main extends Application {
    private Communications comms;
    private Tetris tetris;

    public static void main(String[] args) {
        launch(args);
    }

    private void read() throws Exception {
        System.out.println("Reading...");
        String message = "";
        while (!message.equals("ESCAPE")) {
            message = comms.listen();
            if (!message.equals("")) {
                System.out.println(message);
            }
        }
    }

    public void start(Stage primaryStage) {
        comms = new Communications();
        tetris = new Tetris(comms);
        try {
            tetris.start();
        } catch (Exception e) {
            e.printStackTrace();
            comms.close();
            Platform.exit();
            System.exit(0);
        }
    }
}