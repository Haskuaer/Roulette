package ef.client.controllers;

import ef.client.services.BalanceInfo;
import ef.client.services.Play;
import ef.client.util.ClientSocket;
import ef.client.util.UserID_Holder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ef.client.util.SceneManager;
import ef.client.util.WindowController;

import java.io.IOException;
import java.util.UUID;

public class UserPanelController {

    private ClientSocket clientSOcket;
    private UUID userId = UserID_Holder.getUserId();
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
    private Button playBtn;
    @FXML
    private Label balanceLabel;
    @FXML
    private Button topUpBtn;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setStage(Stage stage){

        this.stage = stage;
        System.out.println("UserPanelController stage: " + stage);

       updateBalance();

        //Window functions
        windowController.dragWindow(rootPane, stage);
        windowController.minimizeWindow(stage, minimizeBtn);
        windowController.closeWindow(stage, exitBtn);

        playBtn.setOnAction(event ->
        {
            Play play = new Play();
            String status = play.findGame();

            if (status == null) { System.out.println("Couldn't find game"); }

            if (status.equals("success"))
            {
                sceneManager.showScene("game-panel");
            }
            else
            {
                System.out.println("Error");
            }
        });

        topUpBtn.setOnAction(event -> { sceneManager.showPopupScene("top-up", this); });
    }

    public void updateBalance(){
        BalanceInfo balanceInfo = new BalanceInfo();
        String balance = balanceInfo.balanceInfo(userId);

        System.out.println("Upadated balance");

        if(balance != null){ balanceLabel.setText(balance); }
        else { System.out.println("No data"); }
    }
}