package ef.client.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ef.client.requests.LoginRequest;
import ef.client.util.ClientSocket;
import ef.client.util.ClientSocketHolder;
import ef.client.util.SceneManager;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.UUID;

import static ef.client.util.UserID_Holder.setUserId;

public class Register extends Service {

    private ClientSocket clientSocket = ClientSocketHolder.getClientSocket();
    private SceneManager sceneManager;

    public Register(){}

    public String handleLogin(TextField usernameTxt, PasswordField passwordTxt){

        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if(username.isEmpty() || password.isEmpty())
        {
            System.out.println("Empty");
        }

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest loginRequest = new LoginRequest("login", username, password);
            String json = objectMapper.writeValueAsString(loginRequest);
            System.out.println("Sending json: " + json);

            //Sending request
            clientSocket.sendMessage(json);

            //Wait for response
            String response = clientSocket.receiveMessage();

            if(response == null || response.isBlank()){
                System.out.println("Empty");
                return null;
            }

            JsonNode jsonNode = objectMapper.readTree(response);

            if (!jsonNode.has("status")) {
                System.out.println("Invalid response format!");
                return "error";
            }

            String status = jsonNode.get("status").asText();
            UUID userId = null;

            if (jsonNode.has("userId") && !jsonNode.get("userId").isNull()) {
                try {
                    userId = UUID.fromString(jsonNode.get("userId").asText());
                    setUserId(userId);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid UUID format received!");
                    return "error";
                }
            }

            if("success".equals(status))
            {
                setUserId(userId);
                return status;
            }
            else
            {
                System.out.println("Error");
                return status;
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
