package ef.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ef.client.util.SceneManager;
import ef.client.util.WindowController;

public class UserPanelController {

    private Stage stage;
    private SceneManager sceneManager;
    private final WindowController windowController = new WindowController();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button exitBtn;
    @FXML
    private Button minimizeBtn;
    @FXML
    private Button playBtn;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setStage(Stage stage){

        this.stage = stage;
        System.out.println("UserPanelController stage: " + stage);

        //Window functions
        windowController.dragWindow(rootPane, stage);
        windowController.minimizeWindow(stage, minimizeBtn);
        windowController.closeWindow(stage, exitBtn);

        playBtn.setOnAction(event -> {

        });
    }
}