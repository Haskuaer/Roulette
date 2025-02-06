package ef.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ef.dao.GameSessionDao;
import ef.dao.RoundDao;
import ef.models.GameSession;
import ef.models.User;
import ef.requests.AddFundsRequest;
import ef.requests.AuthRequest;
import ef.requests.LoginRequest;
import ef.requests.RegisterRequest;
import ef.dao.UserDao;
import ef.responses.BalanceResponse;
import ef.responses.Response;
import ef.responses.UsernameResponse;
import ef.services.*;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final UserDao userDao;
    private final GameSessionDao gameSessionDao;
    private final RoundDao roundDao;

    public ClientHandler(Socket socket, UserDao userDao, GameSessionDao gameSessionDao, RoundDao roundDao) {
        this.clientSocket = socket;
        this.userDao = userDao;
        this.gameSessionDao = gameSessionDao;
        this.roundDao = roundDao;
    }

    @Override
    public void run() {

        AuthService authService = new AuthService(userDao);
        AccountService accountService = new AccountService(userDao);
        GameSessionService gameSessionService = new GameSessionService(gameSessionDao, roundDao);
        //GameHandler gameHandler = new GameHandler();

        try {
            while(true) {

                // Reading
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String receivedJson = in.readLine();
                System.out.println("Received: " + receivedJson);

                //JSON handling
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(receivedJson);
                String action = jsonNode.get("action").asText();

                String status;
                UUID userId = null;
                Response jsonResponse;
                String response = null;

                //Handling request
                switch (action) {
                    case "login":
                        LoginRequest loginRequest = objectMapper.readValue(receivedJson, LoginRequest.class);
                        userId = authService.handleLogin(loginRequest);
                        if(userId == null)
                        {
                            status = "reject";
                            jsonResponse = new Response(status, null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        status = "success";
                        jsonResponse = new Response(status, userId);
                        response = objectMapper.writeValueAsString(jsonResponse);
                        break;
                    case "register":
                        RegisterRequest registerRequest = objectMapper.readValue(receivedJson, RegisterRequest.class);
                        userId = authService.handleRegister(registerRequest);
                        if(userId == null)
                        {
                            status = "reject";
                            response = status;
                            break;
                        }
                        status = "success";
                        jsonResponse = new Response(status, userId);
                        response = objectMapper.writeValueAsString(jsonResponse);
                        break;
                    case "userInfo":
                        AuthRequest userInfo = objectMapper.readValue(receivedJson, AuthRequest.class);
                        userId = userInfo.getUserId();
                        String username = accountService.getUsername(userId);
                        UsernameResponse usernameResponse = new UsernameResponse("success", userInfo.getUserId(), username);
                        response = objectMapper.writeValueAsString(usernameResponse);
                        break;
                    case "balanceInfo":
                        AuthRequest balanceInfo = objectMapper.readValue(receivedJson, AuthRequest.class);
                        userId = balanceInfo.getUserId();
                        double balance = accountService.getBalance(userId);
                        BalanceResponse balanceResponse = new BalanceResponse("success", balanceInfo.getUserId(), balance);
                        response = objectMapper.writeValueAsString(balanceResponse);
                        break;
                    case "addFunds":
                        AddFundsRequest addFundsRequest = objectMapper.readValue(receivedJson, AddFundsRequest.class);
                        userId = addFundsRequest.getUserId();
                        double amount = addFundsRequest.getAmount();
                        status = accountService.addFunds(userId, amount);
                        jsonResponse = new Response(status, userId);
                        response = objectMapper.writeValueAsString(jsonResponse);
                        break;
                    case "play":
                        GameSession session = gameSessionService.findOrCreateSession();
                        User user = userDao.getUserById(userId);
                        status = gameSessionService.startSession(user, session) ? "success" : "reject";
                        System.out.println(status);
                        jsonResponse = new Response(status, userId);
                        System.out.println("Sending response: " + jsonResponse);
                        response = objectMapper.writeValueAsString(jsonResponse);
                        break;
                    default:
                        System.out.println("Unknown action: " + action);
                }

                //Sending response
                out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Connection problems: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
