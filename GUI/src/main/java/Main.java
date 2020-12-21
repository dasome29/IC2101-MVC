import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class Main extends Application {
    private Window window;
    private Communications comms;
    private Screen screen;

    public static void main(String[] args) {
        launch(args);
    }

    private void read() throws Exception{
        System.out.println("Reading...");
        String message = "";
        while (!message.equals("ESCAPE")){
            message = comms.listen();
            if (!message.equals("") && !message.equals("ESCAPE")){
                System.out.println(message);
                screen.update(message);
            }
        }
        comms.close();
        Platform.exit();
        System.exit(0);
    }

    public void start(Stage primaryStage) {
        window = new Window(primaryStage);
        comms = new Communications();
        screen = new Screen(window);


        new Thread(() -> {
            try {
                this.read();
            } catch (Exception e) {

            }
        }).start();
    }
}