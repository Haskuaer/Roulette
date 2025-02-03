package ef.client.controllers;

import ef.client.services.BalanceInfo;
import ef.client.util.UserID_Holder;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ef.client.util.SceneManager;
import ef.client.util.WindowController;

import java.util.UUID;

public class GameController {

    private Stage stage;
    private UUID userId = UserID_Holder.getUserId();
    private SceneManager sceneManager;
    private final WindowController windowController = new WindowController();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button exitBtn;
    @FXML
    private Button minimizeBtn;
    @FXML
    private Label balanceLabel;
    @FXML
    private Button topUpBtn;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setStage(Stage stage){

        this.stage = stage;
        System.out.println("GameController stage: " + stage);

        updateBalance();

        //Window functions
        windowController.dragWindow(rootPane, stage);
        windowController.minimizeWindow(stage, minimizeBtn);
        windowController.closeWindow(stage, exitBtn);

        topUpBtn.setOnAction(event -> { sceneManager.showPopupScene("top-up", this);});
    }

    public void updateBalance(){
        BalanceInfo balanceInfo = new BalanceInfo();
        String balance = balanceInfo.balanceInfo(userId);

        System.out.println("Upadated balance");

        if(balance != null){ balanceLabel.setText(balance); }
        else { System.out.println("No data"); }
    }
}