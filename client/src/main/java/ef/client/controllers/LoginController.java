package ef.client.controllers;
import ef.client.util.WindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ef.client.util.SceneManager;

public class LoginController {

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
    private Button loginBtn;
    @FXML
    private Button goRegisterBtn;

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

        //Go to registration
        goRegisterBtn.setOnAction(event -> { sceneManager.showScene("register"); });
        //Sign up
        loginBtn.setOnAction(event -> { sceneManager.showScene("user-panel"); });
    }
}