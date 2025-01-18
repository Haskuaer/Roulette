package ef.client;

//import com.almasb.fxgl.net.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ef.client.controllers.LoginController;
import ef.client.util.ClientSocket;
import ef.client.util.SceneManager;

import java.io.IOException;
import java.util.Objects;

public class Launcher extends Application {

    private SceneManager sceneManager;
    private ClientSocket clientSocket;

    @Override
    public void start(Stage stage) throws IOException {
        //Test server connection
        try {
            clientSocket = new ClientSocket("localhost", 12345);
            // clientSocket.close();
        } catch (IOException e) {
            System.err.println("Could not connect to the server: " + e.getMessage());
        }

        //Setting SceneManager
        sceneManager = new SceneManager(stage);

        //Adding scenes to SceneManager
        sceneManager.addScene("login", "/ef/client/login.fxml");
        sceneManager.addScene("register", "/ef/client/register.fxml");
        sceneManager.addScene("user-panel", "/ef/client/user-panel.fxml");

        //Setting stage
        stage.setTitle("Roulette");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);

        //Login scene should be opened as first
        sceneManager.showScene("login");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}