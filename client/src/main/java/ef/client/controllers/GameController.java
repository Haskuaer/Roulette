package ef.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    private GridPane board;
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

        initializeBoard();
        initializeImages();

        //Window functions
        windowController.dragWindow(rootPane, stage);
        windowController.minimizeWindow(stage, minimizeBtn);
        windowController.closeWindow(stage, exitBtn);
    }

    private void initializeBoard(){

        int cols = 13;
        int index = 0;

        for(Node node:board.getChildren()){
            if(node instanceof Pane pane){

                int row = index / cols;
                int col = index % cols;

                if((row + col) % 2 == 0){
                    pane.getStyleClass().add("even");
                } else {
                    pane.getStyleClass().add("odd");
                }

                index++;
            }
        }
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