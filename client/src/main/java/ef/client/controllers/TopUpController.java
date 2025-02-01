package ef.client.controllers;

import ef.client.services.TopUp;
import ef.client.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.UUID;

public class TopUpController {

    private ClientSocket clientSocket = ClientSocketHolder.getClientSocket();
    private UUID userId = UserID_Holder.getUserId();
    private Stage stage;
    private SceneManager sceneManager;
    private final WindowController windowController = new WindowController();
    private UserPanelController userPanelController;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button exitBtn;
    @FXML
    private Button minimizeBtn;
    @FXML
    private TextField numberField;
    @FXML
    private Button topUpBtn, backBtn;

    public void serUserPanelController(UserPanelController userPanelController) { this.userPanelController = userPanelController; }

    public UserPanelController getUserPanelController() { return userPanelController; }
    public void setUserPanelController(UserPanelController userPanelController) { this.userPanelController = userPanelController; }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
        System.out.println("TopUpController stage: " + stage);

        //Window functions
        windowController.dragWindow(rootPane, stage);
        windowController.minimizeWindow(stage, minimizeBtn);
        windowController.closeWindow(stage, exitBtn);

        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        topUpBtn.setOnAction(event -> { handleAddFounds(); });

        backBtn.setOnAction(event -> stage.close());
    }

    @FXML
    public void handleAddFounds() {
        String text = numberField.getText();
        System.out.println("Entered amount: " + text);

        if (text.isEmpty()) {
            System.out.println("Field is empty!");
            return;
        }

        try {
            double amount = Double.parseDouble(text);
            System.out.println("Trying to add funds: " + amount);

            TopUp topUpService = new TopUp();
            String result = topUpService.topUp(UserID_Holder.getUserId(), text);

            if ("success".equals(result)) {
                System.out.println("Funds added successfully!");
                if (userPanelController != null) {
                    userPanelController.updateBalance();  // Aktualizacja balansu
                } else {
                    System.out.println("UserPanelController is null!");
                }
                stage.close();
            } else {
                System.out.println("Failed to add funds.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format!");
        }
    }

}
