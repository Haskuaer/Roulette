package ef.client.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ef.client.requests.AddFundsRequest;
import ef.client.requests.AuthRequest;
import ef.client.requests.LoginRequest;
import ef.client.util.ClientSocket;
import ef.client.util.ClientSocketHolder;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

import static ef.client.util.UserID_Holder.setUserId;

public class TopUp extends Service
{
    private final ClientSocket clientSocket = ClientSocketHolder.getClientSocket();

    public TopUp(){}

    public String topUp(UUID userId, String value)
    {

        if(value.isEmpty())
        {
            System.out.println("Empty");
            return null;
        }

        double amount = Double.parseDouble(value);

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            AddFundsRequest request = new AddFundsRequest("addFunds", userId, amount);
            String json = objectMapper.writeValueAsString(request);

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
                return "success";
            }
            else
            {
                System.out.println("Error");
                return null;
            }


        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
