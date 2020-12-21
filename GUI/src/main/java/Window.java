import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Window {
    private Scene scene;
    public StackPane pane;
    private Stage stage;
    public Window(Stage stage){
        pane = new StackPane();
        pane.setAlignment(Pos.TOP_LEFT);
        scene = new Scene(pane, 1200, 800);
        this.stage = stage;
        this.stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("MVC");
        this.stage.alwaysOnTopProperty();
        this.stage.show();
    }
}
