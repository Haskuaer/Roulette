package ef.services;

import ef.requests.LoginRequest;
import ef.requests.RegisterRequest;
import ef.dao.UserDao;
import ef.models.User;

import java.util.UUID;

public class AuthService {

    private final UserDao userDao;

    public AuthService(UserDao userDao){
        this.userDao = userDao;
    }

    public UUID handleLogin(LoginRequest loginRequest) {
        System.out.println("Handling login for: " + loginRequest.getUsername());
        try
        {
            User user = userDao.getUserByUsername(loginRequest.getUsername());
            if(user == null)
            {
                System.out.println("User not found!");
                return null;
            }
            System.out.println("Found user: " + user);
            return user.getId();
        } catch (IllegalAccessError e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public UUID handleRegister(RegisterRequest registerRequest) {
        System.out.println("Handling register for: " + registerRequest.getUsername());

        try {
            User user = userDao.getUserByUsername(registerRequest.getUsername());

            if (user != null) {
                return null;
            }

            User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword());
            userDao.addUser(newUser);
            return newUser.getId();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
