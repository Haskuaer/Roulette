package ef.services;

import ef.requests.LoginRequest;
import ef.requests.RegisterRequest;
import ef.dao.UserDao;
import ef.models.User;

import java.util.UUID;

public class AuthService {

    private final UserDao userDao;

    //CONSTRUCTOR
    public AuthService(UserDao userDao){ this.userDao = userDao; }

    //HANDLE LOGIN REQUEST DATA
    public UUID handleLogin(LoginRequest loginRequest) {
        System.out.println("SV: Handling login for: " + loginRequest.getUsername());
        try
        {
            User user = userDao.checkCreds(loginRequest.getUsername(), loginRequest.getPassword());
            if(user == null) { return null; }
            return user.getId();
        }
        catch (IllegalAccessError e) { e.printStackTrace(); }
        return null;
    }

    //HANDLE REGISTER REQUEST DATA
    public UUID handleRegister(RegisterRequest registerRequest)
    {
        System.out.println("SV: Handling register for: " + registerRequest.getUsername());
        try
        {
            User user = userDao.getUserByUsername(registerRequest.getUsername());

            if (user != null) { return null; }

            User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword());
            userDao.addUser(newUser);
            return newUser.getId();
        }
        catch (IllegalAccessException e) { e.printStackTrace(); }

        return null;
    }
}
