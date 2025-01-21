package ef.actions;

public class LoginRequest {

    private String action;
    private String username;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String action, String username, String password) {
        this.action = action;
        this.username = username;
        this.password = password;
    }

    public String getAction() { return action; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setAction(String action) { this.action = action; }
    public void setUsername(String username) { this.username = username;}
    public void setPassword(String password) { this.password = password; }
}
