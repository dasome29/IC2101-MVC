import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;


public class Main extends Application {
    private Window window;
    private Communications comms;
    private Screen screen;

    public static void main(String[] args) {
        launch(args);
    }

    private void read() throws Exception{


    }

    public void start(Stage primaryStage) {
        window = new Window(primaryStage);
        comms = new Communications();
        screen = new Screen(window);


        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Reading...");
                String message = "";
                while (!message.equals("ESCAPE")){
                    try {
                        message = comms.listen();
                    } catch (Exception e) {
                        System.out.println("");
                    }
                    if (!message.equals("") && !message.equals("ESCAPE")){
                        System.out.println(message);
                        String finalMessage = message;
                        Platform.runLater(() -> screen.update(finalMessage));
                    }
                }
                comms.close();
                Platform.exit();
                System.exit(0);
                return null;
            }
        };
        new Thread(task).start();
    }
}