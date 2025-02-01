package ef.client.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ef.client.requests.AuthRequest;
import ef.client.util.ClientSocket;
import ef.client.util.ClientSocketHolder;
import ef.client.util.SceneManager;

import java.io.IOException;
import java.util.UUID;

public class BalanceInfo extends Service {

    private ClientSocket clientSocket = ClientSocketHolder.getClientSocket();

    public BalanceInfo(){}

    public String balanceInfo(UUID userId){

        String balance = null;

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            AuthRequest request = new AuthRequest("balanceInfo", userId);
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
                balance = jsonNode.get("balance").asText();
                return balance;
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
