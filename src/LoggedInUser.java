public class LoggedInUser extends Users {
    private String username;
    private String password;

    public LoggedInUser() {

    }
    public LoggedInUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }


}
