import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
    Communications comms;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        comms = new Communications();
        scene.setOnKeyPressed(e -> {
            String buff = "";
            switch (e.getCode()) {
                case A:
                    buff = "A";
                    break;
                case S:
                    buff = "S";
                    break;
                case W:
                    buff = "W";
                    break;
                case D:
                    buff = "D";
                    break;
                case SPACE:
                    buff = "SPACE";
                    break;
                case ESCAPE:
                    buff = "ESCAPE";
                    comms.close();
                    Platform.exit();
                    System.exit(0);
                    break;
                case ENTER:
                    buff = "ENTER";
                    comms.connect();
                    break;
            }
            if (!buff.equals("")) {
                System.out.println(buff);
                comms.sendMessage(buff);
            }
        });
        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing...");
            comms.sendMessage("ESCAPE");
            comms.close();
        });
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setMaxHeight(100);
        primaryStage.setMaxWidth(100);
    }
}
