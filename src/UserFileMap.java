import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class UserFileMap {

    public HashMap<String, String> getStudentUsers() {
        Users userFiles = new Users();
        File studentUsersFile = userFiles.getStudentFile();
        LinkedHashMap<String, String> studentMap = new LinkedHashMap<>();

        if (studentUsersFile != null) {

            try {
                Scanner scanner = new Scanner(studentUsersFile);

                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split(" ");
                    String username = line[0];
                    String password = line[1];
                    studentMap.put(username, password);
                }

                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            for (String username : studentMap.keySet()) {
//                String password = studentMap.get(username);
//                System.out.println(username + " " + password);
//            }
        }

        return studentMap;

    }

    public HashMap<String, String> getAdminUsers() {
        Users userFiles = new Users();
        File studentUsersFile = userFiles.getAdminFile();
        LinkedHashMap<String, String> AdminMap = new LinkedHashMap<>();

        if (studentUsersFile != null) {

            try {
                Scanner scanner = new Scanner(studentUsersFile);

                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split(" ");
                    String username = line[0];
                    String password = line[1];
                    AdminMap.put(username, password);
                }

                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            for (String username : studentMap.keySet()) {
//                String password = studentMap.get(username);
//                System.out.println(username + " " + password);
//            }
        }

        return AdminMap;

    }

}
