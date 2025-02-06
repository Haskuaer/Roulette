package ef.client.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import ef.client.requests.RegisterRequest;
import ef.client.services.Register;
import ef.client.util.ClientSocketHolder;
import ef.client.util.WindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ef.client.util.SceneManager;
import ef.client.util.ClientSocket;

import java.io.IOException;

public class RegisterController {

    private Stage stage;
    private SceneManager sceneManager;
    private final WindowController windowController = new WindowController();
    private ClientSocket clientSocket;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button exitBtn;
    @FXML
    private Button minimizeBtn;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private PasswordField confirmedPasswordTxt;
    @FXML
    private Button goLoginBtn;
    @FXML
    private Button registerBtn;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    //Getting stage from Launcher
    public void setStage(Stage stage){

        clientSocket = ClientSocketHolder.getClientSocket();
        this.stage = stage;
        //System.out.println("LoginController stage: " + stage);

        //Window functions
        windowController.dragWindow(rootPane, stage);
        windowController.minimizeWindow(stage, minimizeBtn);
        windowController.closeWindow(stage, exitBtn);

        //Go to Login scene
        goLoginBtn.setOnAction(event -> { sceneManager.showScene("login"); });

        //Register
        registerBtn.setOnAction((event ->
        {
            Register register = new Register();
            String status = register.handleRegister(usernameTxt, passwordTxt, confirmedPasswordTxt);
            if(status.equals("success")){ sceneManager.showScene("user-panel"); }
        }));
    }
}