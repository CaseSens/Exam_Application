import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Users
{
    /*Parent interface for Admin & Non-Admin alike, contains properties that
    both users types can share: Username, Password, login/logout functionality */
    private String username;
    private String password;
    private static final File studentFile = new File("Users/STUDENT_USER-INFO.txt");
    private static final File adminFile = new File("Users/ADMIN_USER-INFO.txt");

    public Users() {

    }

    public Users (String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void createAdminUser() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(adminFile, true));
            writer.println(username + " " + password);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createStudentUser() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(studentFile, true));
            writer.println(username + " " + password);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public File getAdminFile() {
        return adminFile;
    }

    public File getStudentFile() {
        return studentFile;
    }




}




