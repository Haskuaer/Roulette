package ef.client.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ef.client.controllers.*;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class SceneManager {

    private ClientSocket clientSocket;
    private final Stage stage;
    private final HashMap<String, String> scenes = new HashMap<>();
    private final HashMap<String, Object> controllers = new HashMap<>();

    public SceneManager(Stage stage) { this.stage = stage; }

    public void setClientSocket(ClientSocket clientSocket) { this.clientSocket = clientSocket; }

    public void addScene(String name, String fxmlFile) { scenes.put(name, fxmlFile); }

    public void showScene(String name) {
        String fxmlFile = scenes.get(name);
        if(fxmlFile != null) {
            try{

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ef/client/style.css")).toExternalForm());
                stage.setScene(scene);
                centerStage(stage);

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

    public void centerStage(Stage stage){

        double screenWidth = javafx.stage.Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = javafx.stage.Screen.getPrimary().getVisualBounds().getHeight();

        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        double x = (screenWidth - stageWidth) / 2;
        double y = (screenHeight - stageHeight) / 2;

        stage.setX(x);
        stage.setY(y);
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
        } else if(controller instanceof GameController gameController) {
            gameController.setSceneManager(this);
            gameController.setStage(stage);
            System.out.println("Stage passed to UserPanelController: " + stage);
       }
    }

    public void showPopupScene(String name, Object controllerToSet) {
        String fxmlFile = scenes.get(name);
        if (fxmlFile != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = fxmlLoader.load();

                Stage popupStage = new Stage();
                popupStage.setTitle("Add Funds");
                popupStage.setScene(new Scene(root));
                popupStage.initStyle(StageStyle.UNDECORATED);
                popupStage.setResizable(false);
                popupStage.initModality(Modality.APPLICATION_MODAL);

                Object controller = fxmlLoader.getController();
                if (controller instanceof TopUpController topUpController && controllerToSet instanceof UserPanelController) {
                    topUpController.setStage(popupStage);
                    topUpController.setUserPanelController((UserPanelController) controllerToSet);
                    System.out.println("Stage passed to TopUpController: " + popupStage);
                } else if (controller instanceof TopUpController topUpController && controllerToSet instanceof GameController) {
                    topUpController.setStage(popupStage);
                    topUpController.setGameController((GameController) controllerToSet);
                }

                popupStage.showAndWait();

            } catch (IOException e) {
                System.out.println("Error loading FXML: " + fxmlFile);
                e.printStackTrace();
            }
        } else {
            System.out.println("Scene name not found: " + name);
        }
    }
}