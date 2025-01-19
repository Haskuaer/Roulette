package ef.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ef.client.util.SceneManager;
import ef.client.util.WindowController;

public class GameController {

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
    private ImageView token10, token25, token50, token100, token500, token1000;
    @FXML
    private  ImageView[] tokens = new ImageView[6];
    private final Image[] images = new Image[6];

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setStage(Stage stage){

        this.stage = stage;
        System.out.println("UserPanelController stage: " + stage);

        initializeImages();

        //Window functions
        windowController.dragWindow(rootPane, stage);
        windowController.minimizeWindow(stage, minimizeBtn);
        windowController.closeWindow(stage, exitBtn);
    }

    private void initializeImages(){

        tokens[0] = token10;
        tokens[1] = token25;
        tokens[2] = token50;
        tokens[3] = token100;
        tokens[4] = token500;
        tokens[5] = token1000;

        for(int i = 0; i < images.length; i++){
            images[i] = new Image(getClass().getResource("/ef/client/images/tokens/"+i+".png").toExternalForm());
            tokens[i].setImage(images[i]);
        }
    }
}