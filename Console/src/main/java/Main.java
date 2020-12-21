import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class Main extends Application {
    private Communications comms;

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
                comms.send("[{'i': 1, 'j': 3, 'color': 0}, {'i': 2, 'j': 3, 'color': 3}, {'score': 100, 'lives': 3}]");
            }
        }
    }

    public void start(Stage primaryStage) {
        comms = new Communications();
        try {
            this.read();
        } catch (Exception e) {
            comms.close();
            Platform.exit();
            System.exit(0);
        }
    }
}