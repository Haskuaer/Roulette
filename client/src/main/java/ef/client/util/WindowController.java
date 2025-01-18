package ef.client.util;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class WindowController {

    private double xOffset = 0;
    private double yOffset = 0;

    public void dragWindow(Pane rootPane, Stage stage) {

        rootPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        rootPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public void minimizeWindow(Stage stage, Button minimizeBtn) {
        minimizeBtn.setOnAction(event -> { stage.setIconified(true); });
    }

    public void closeWindow(Stage stage, Button exitBtn) {
        exitBtn.setOnAction(event -> { System.exit(0); });
    }
}