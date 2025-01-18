package ef.client.controllers;
import ef.client.util.WindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ef.client.util.SceneManager;

public class RegisterController {

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
    private Button goLoginBtn;
    @FXML
    private Button registerBtn;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    //Getting stage from Launcher
    public void setStage(Stage stage){

        this.stage = stage;
        //System.out.println("LoginController stage: " + stage);

        //Window functions
        windowController.dragWindow(rootPane, stage);
        windowController.minimizeWindow(stage, minimizeBtn);
        windowController.closeWindow(stage, exitBtn);

        //Go to Login scene
        goLoginBtn.setOnAction(event -> { sceneManager.showScene("login"); });
    }
}