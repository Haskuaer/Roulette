package ef.client.requests;

public class RegisterRequest extends Request {

    private String username;
    private String password;
    private String confirmPassword;

    public RegisterRequest(String action, String username, String password, String confirmedPassword)
    {
        super(action);
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmedPassword;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
