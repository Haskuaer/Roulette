package ef.client.requests;

public class LoginRequest extends Request {

    private String username;
    private String password;

    public LoginRequest(String action, String username, String password) {
        super(action);
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username;}

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
