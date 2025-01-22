package ef.client.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import ef.client.actions.LoginRequest;
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

public class LoginController {

    private ClientSocket clientSocket;
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
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Button loginBtn;
    @FXML
    private Button goRegisterBtn;

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

        //Go to registration
        goRegisterBtn.setOnAction(event -> { sceneManager.showScene("register"); });

        //Sign up (no data)
        loginBtn.setOnAction(event -> { sceneManager.showScene("user-panel"); });

        //Sign up
        //loginBtn.setOnAction(event -> { handleLogin(); });
    }

    private void handleLogin(){

        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if(username.isEmpty() || password.isEmpty()){
            System.out.println("Empty");
        }

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest loginRequest = new LoginRequest("login", username, password);
            String json = objectMapper.writeValueAsString(loginRequest);

            //Sending request
            clientSocket.sendMessage(json);

            //Wait for response
            String response = clientSocket.receiveMessage();
            if("success".equals(response)){ sceneManager.showScene("user-panel"); }
            else { System.out.println("Error"); }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}