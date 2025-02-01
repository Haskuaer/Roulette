package ef.services;

import ef.dao.UserDao;

import java.io.IOException;
import java.util.UUID;

public class AccountService {

    private final UserDao userDao;

    public AccountService(UserDao userDao) { this.userDao = userDao; }

    public String getUsername(UUID userId) { return userDao.getUsername(userId); }

    public double getBalance(UUID userId) { return userDao.getBalance(userId); }

    public String addFunds(UUID userId, double amount) {
        try{
            userDao.setBalance(userId, amount);
            return "success";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
