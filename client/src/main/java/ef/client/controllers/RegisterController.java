package ef.client.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import ef.client.requests.RegisterRequest;
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
    private TextField confirmedPasswordTxt;
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
        registerBtn.setOnAction((event -> { handleRegister(); }));
    }

    private void handleRegister(){

        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        String confirmedPassword = confirmedPasswordTxt.getText();

        if(username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()){
            System.out.println("Empty");
        }

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterRequest registerRequest = new RegisterRequest("register", username, password, confirmedPassword);
            String json = objectMapper.writeValueAsString(registerRequest);

            //Sending request
            clientSocket.sendMessage(json);

            //Wait for response
            String response = clientSocket.receiveMessage();
            if("success".equals(response)){ sceneManager.showScene("user-panel"); }
            else { System.out.println("Error: " + response); }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}