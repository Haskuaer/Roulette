package ef.client.requests;

public class UserInfoRequest {

    private String username;
    private double balance;

    public UserInfoRequest(String username) { this.username = username; }
    public UserInfoRequest(double balance) { this.balance = balance; }
    public UserInfoRequest(String username, double balance) { this.username = username; this.balance = balance; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
