package ef.client.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ef.client.controllers.LoginController;
import ef.client.controllers.RegisterController;
import ef.client.controllers.UserPanelController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class SceneManager {

    private final Stage stage;
    private final HashMap<String, String> scenes = new HashMap<>();
    private final HashMap<String, Object> controllers = new HashMap<>();

    public SceneManager(Stage stage) { this.stage = stage; }

    public void addScene(String name, String fxmlFile) {
        scenes.put(name, fxmlFile);
    }

    public void showScene(String name) {
        String fxmlFile = scenes.get(name);
        if(fxmlFile != null) {
            try{

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ef/client/style.css")).toExternalForm());
                stage.setScene(scene);

                Object controller = fxmlLoader.getController();
                if(controller == null){
                    System.out.println("controller is null for FXML file: " + fxmlFile);
                } else {
                    System.out.println("Loaded controller: " + controller.getClass().getSimpleName());
                }
                controllers.put(name, controller);
                applySceneManager(controller);

                stage.show();

            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public Object getController(String name) {
        return controllers.get(name);
    }

    private void applySceneManager(Object controller) {
        if(controller instanceof LoginController loginController) {
            loginController.setSceneManager(this);
            loginController.setStage(stage);
            System.out.println("Stage passed to LoginController: " + stage);
        } else if(controller instanceof RegisterController registerController) {
            registerController.setSceneManager(this);
            registerController.setStage(stage);
            System.out.println("Stage passed to RegisterController: " + stage);
        } else if(controller instanceof UserPanelController userPanelController) {
            userPanelController.setSceneManager(this);
            userPanelController.setStage(stage);
            System.out.println("Stage passed to UserPanelController: " + stage);
        }
    }
}