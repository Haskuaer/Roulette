package ef.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        GameSessionService gameSessionService = new GameSessionService(gameSessionDao, userDao, roundDao);
        //GameHandler gameHandler = new GameHandler();

        try
        {
            while(true)
            {
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
                UUID userId;
                Response jsonResponse;
                String response = null;

                //Handling request
                switch (action) {
                    case "login": {
                        //Get request value
                        LoginRequest request = parseRequest(objectMapper, receivedJson, LoginRequest.class);
                        if (request == null) {
                            System.out.println("RQ: Empty request value");
                            //Sending negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Get userId
                        userId = authService.handleLogin(request);
                        //System.out.println("UserID: " + userId); CHECK
                        if (userId == null) {
                            System.out.println("SV: Couldn't find user");
                            //Sending negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Sending positive response
                        jsonResponse = new Response("success", userId);
                        response = objectMapper.writeValueAsString(jsonResponse);
                        break;
                    }
                    case "register": {
                        //Get request value
                        RegisterRequest request = parseRequest(objectMapper, receivedJson, RegisterRequest.class);
                        if (request == null) {
                            System.out.println("RQ: Empty request value");
                            //Sending negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Get userId
                        userId = authService.handleRegister(request);
                        if (userId == null) {
                            System.out.println("SV: User already exists");
                            //Sending negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Sending positive response
                        jsonResponse = new Response("success", userId);
                        response = objectMapper.writeValueAsString(jsonResponse);
                        break;
                    }
                    case "userInfo": {
                        //Get request value
                        AuthRequest request = parseRequest(objectMapper, receivedJson, AuthRequest.class);
                        if (request == null)
                        {
                            System.out.println("Empty request value");
                            //Send negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Get userId
                        userId = request.getUserId();
                        if (userId == null)
                        {
                            System.out.println("RQ: Couldn't get ID value");
                            //Send negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Get username
                        String username = accountService.getUsername(userId);
                        if (username == null)
                        {
                            System.out.println("SV: Couldn't get username value");
                            //Send negative response
                            jsonResponse = new Response("error", userId);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Send positive response
                        UsernameResponse usernameResponse = new UsernameResponse("success", request.getUserId(), username);
                        response = objectMapper.writeValueAsString(usernameResponse);
                        break;
                    }
                    case "balanceInfo": {
                        //Get request value
                        AuthRequest request = parseRequest(objectMapper, receivedJson, AuthRequest.class);
                        if (request == null)
                        {
                            System.out.println("RQ: Empty request value");
                            //Send negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Get userId
                        userId = request.getUserId();
                        if (userId == null)
                        {
                            System.out.println("RQ: Couldn't get ID value");
                            //Send negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Get balance value
                        double balance = accountService.getBalance(userId);
                        //Send positive response
                        BalanceResponse balanceResponse = new BalanceResponse("success", request.getUserId(), balance);
                        response = objectMapper.writeValueAsString(balanceResponse);
                        break;
                    }
                    case "addFunds": {
                        //Get request value
                        AddFundsRequest request = parseRequest(objectMapper, receivedJson, AddFundsRequest.class);
                        if (request == null)
                        {
                            System.out.println("RQ: Empty request value");
                            //Send negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Get userId
                        userId = request.getUserId();
                        if (userId == null)
                        {
                            System.out.println("RQ: Couldn't get ID value");
                            //Send negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Get amount value
                        double amount = request.getAmount();
                        //Set status
                        status = accountService.addFunds(userId, amount);
                        if (status == null || status.equals("error"))
                        {
                            System.out.println("SV: Couldn't set balance");
                            //Send negative response
                            jsonResponse = new Response("error", userId);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        jsonResponse = new Response("success", userId);
                        response = objectMapper.writeValueAsString(jsonResponse);
                        break;
                    }
                    case "play": {
                        //Get request values
                        AuthRequest request = parseRequest(objectMapper, receivedJson, AuthRequest.class);
                        if (request == null)
                        {
                            System.out.println("RQ: Empty request given");
                            //Sending negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Get userId
                        userId = request.getUserId();
                        if (userId == null)
                        {
                            System.out.println("RQ: Couldn't get ID value");
                            //Sending negative response
                            jsonResponse = new Response("error", null);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Checks if there's a session, if not creates one
                        GameSession session = gameSessionService.findOrCreateSession();
                        if (session == null) {
                            System.out.println("SV: Couldn't load session");
                            //Sending negative response
                            jsonResponse = new Response("error", userId);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Get User which requested that
                        User user = userDao.getUserById(userId);
                        if (user == null) {
                            System.out.println("DAO: Couldn't load user");
                            //Sending negative response
                            jsonResponse = new Response("error", userId);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Add User to session
                        status = gameSessionDao.addUser(user, session);
                        if (status.equals("error")) {
                            System.out.println("DAO: Couldn't add user to session");
                            //Sending negative response
                            jsonResponse = new Response("error", userId);
                            response = objectMapper.writeValueAsString(jsonResponse);
                            break;
                        }
                        //Sending response
                        jsonResponse = new Response("success", userId);
                        response = objectMapper.writeValueAsString(jsonResponse);
                        break;
                    }
                    default: { System.out.println("Unknown action: " + action); }
                }

                out.println(response);
                out.flush();
            }
        }
        catch (IOException e) { System.out.println("Connection problems: " + e.getMessage()); }
        finally
        {
            try { clientSocket.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    //PARSING REQUESTS
    public <T> T parseRequest(ObjectMapper objectMapper, String receivedJson, Class<T> requestType)
    {
        try { return objectMapper.readValue(receivedJson, requestType); }
        catch (JsonProcessingException e) { System.out.println("RQ: Error parsing request: " + e.getMessage()); return null; }
    }
}