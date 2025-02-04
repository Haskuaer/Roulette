package ef.client.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ef.client.requests.AuthRequest;
import ef.client.requests.LoginRequest;
import ef.client.util.ClientSocket;
import ef.client.util.ClientSocketHolder;
import ef.client.util.SceneManager;
import ef.client.util.UserID_Holder;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.UUID;

import static ef.client.util.UserID_Holder.getUserId;
import static ef.client.util.UserID_Holder.setUserId;

public class Play extends Service {

    private final ClientSocket clientSocket = ClientSocketHolder.getClientSocket();
    private final UUID userId = UserID_Holder.getUserId();

    public Play(){}

    public String findGame()
    {
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            AuthRequest request = new AuthRequest("play", userId);
            String json = objectMapper.writeValueAsString(request);
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

            if("success".equals(status))
            {
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
