package ef.services;

import ef.dao.UserDao;

import java.util.UUID;

public class AccountService {

    private final UserDao userDao;

    //CONSTRUCTOR
    public AccountService(UserDao userDao) { this.userDao = userDao; }

    //GETTERS
    public String getUsername(UUID userId) { return userDao.getUsername(userId); }
    public double getBalance(UUID userId) { return userDao.getBalance(userId); }

    //ADD FUNDS TO ACCOUNT
    public String addFunds(UUID userId, double amount)
    {
        try { userDao.setBalance(userId, amount); return "success"; }
        catch (IllegalAccessException e) { e.printStackTrace(); return "error"; }
    }
}
