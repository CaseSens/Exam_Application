import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class LogOrRegister {
    private static JButton submit;
    private static JButton registerBtn;
    private static JFrame mainFrame;
    private static String tempUsername;
    private static String tempPassword;
    private static LinkedHashMap<String, String> tempUserMap = new LinkedHashMap<>();
    private static JTextField usernameText;
    private static JTextField passwordText;
    private static UserFileMap userFileMap = new UserFileMap();
    private static LinkedHashSet<String> combinedKeySet = new LinkedHashSet<>();
    private static LinkedHashSet<String> combinedAdminSet = new LinkedHashSet<>();
    private static LinkedHashSet<String> combinedStudentSet = new LinkedHashSet<>();
    public static volatile boolean isLoggedIn = false;
    public static volatile boolean isAdmin = false;

    public void buildLoginPage() {
        createKeySet();
        createMainFrame();
    }

    private JFrame createMainFrame() {
        mainFrame = new JFrame("Please Log In");
        mainFrame.setSize(900, 900);
        mainFrame.setLayout(null);
        mainFrame.add(login());
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return mainFrame;
    }

    private static JPanel login()
    {
        JPanel userPanel = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(0, 0, 100, 30);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(0, 70, 100, 30);
        usernameText = new JTextField();
        usernameText.setBounds(100, 0, 200, 30);
        passwordText = new JTextField();
        passwordText.setBounds(100, 70, 200, 30);
        registerBtn = new JButton("Register");
        registerBtn.setBounds(40, 160, 100, 40);
        submit = new JButton("Log In");
        submit.setBounds(160, 160, 100, 40);

        userPanel.add(usernameLabel);
        userPanel.add(usernameText);
        userPanel.add(passwordLabel);
        userPanel.add(passwordText);
        userPanel.add(submit);
        userPanel.add(registerBtn);
        userPanel.setLayout(null);
        userPanel.setBounds(300, 270, 300, 200);

        registerClicked();
        loginClicked();

        return userPanel;
    }

    private static void createKeySet() {
        combinedAdminSet.addAll(userFileMap.getAdminUsers().keySet());
        combinedStudentSet.addAll(userFileMap.getStudentUsers().keySet());
        combinedKeySet.addAll(combinedAdminSet);
        combinedKeySet.addAll(combinedStudentSet);
        System.out.println(combinedKeySet.toString());
    }

    private static void registerClicked()
    {
        registerBtn.addActionListener(e -> {
            tempUsername = usernameText.getText();
            tempPassword = passwordText.getText();

                if (!combinedKeySet.contains(tempUsername)) {
                    createRegisterPage();
                } else {
                    JOptionPane.showMessageDialog(null, "You have entered a username that is already" +
                    " in use, please login instead.");
                }
        });
    }

    private static void loginClicked()
    {
        submit.addActionListener(e -> {
            tempUsername = usernameText.getText();
            tempPassword = passwordText.getText();


            if (combinedAdminSet.contains(tempUsername)) {
                String adminPassword = userFileMap.getAdminUsers().get(tempUsername);
                if (adminPassword != null && adminPassword.equals(tempPassword)) {
                    setLoggedIn(true, true);
                    AdminPage adminPage = new AdminPage();
                    adminPage.createAdminPage();
                    mainFrame.setVisible(false);
                    System.out.println("welcome " + tempUsername);
                    System.out.println(isLoggedIn + " " + isAdmin);
                } else {
                    JOptionPane.showMessageDialog(null, "You have entered the wrong password");
                }
            } else if (combinedStudentSet.contains(tempUsername)) {
                String studentPassword = userFileMap.getStudentUsers().get(tempUsername);
                if(studentPassword != null && studentPassword.equals(tempPassword)) {
                    setLoggedIn(true, false);
                    NonAdminPage nonAdminPage = new NonAdminPage();
                    nonAdminPage.createNonAdminPage();
                    mainFrame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "You have entered the wrong password");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You have entered a username that does not" +
                        " exist, please register instead.");
            }
        });
    }

    public synchronized static void setLoggedIn(boolean loggedIn, boolean admin) {
        isLoggedIn = loggedIn;
        isAdmin = admin;
    }

    private static JFrame createRegisterPage()
    {
        JFrame register = new JFrame("Register");
        JPanel registerPnl = new JPanel();
        JLabel registerLabel = new JLabel("Register USER as:");
        JRadioButton adminType = new JRadioButton("Admin");
        JRadioButton nonAdminType = new JRadioButton("Student");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(adminType);
        buttonGroup.add(nonAdminType);
        submit = new JButton("Submit");
        JLabel askPrivKey = new JLabel("Please input the secret key");
        JTextField privKeyInput = new JTextField();
        JFrame privateKeyFrame = new JFrame("add secret key");

        registerPnl.setLayout(new FlowLayout());
        registerPnl.add(registerLabel);
        registerPnl.add(adminType);
        registerPnl.add(nonAdminType);
        registerPnl.add(submit);

        register.add(registerPnl);
        register.setSize(400, 400);
        register.setLayout(new FlowLayout());
        register.setVisible(true);

        submit.addActionListener(e -> {
            if (adminType.isSelected()) {
                Admin adminKey = new Admin();
                askPrivKey.setBounds(10,10,200,40);
                privKeyInput.setBounds(200,10,100,40);
                submit = new JButton("Submit");
                submit.setBounds(120,100,80,30);
                privateKeyFrame.add(askPrivKey);
                privateKeyFrame.add(privKeyInput);
                privateKeyFrame.add(submit);
                privateKeyFrame.setSize(400, 250);
                privateKeyFrame.setLayout(null);
                privateKeyFrame.setVisible(true);

                submit.addActionListener(e1 -> {
                    if (privKeyInput.getText().equals(adminKey.getPrivateKey())) {
                        privateKeyFrame.setVisible(false);
                        register.setVisible(false);
                        mainFrame.setVisible(false);

                        Users userInfo = new Users(tempUsername, tempPassword);
                        System.out.println(userInfo.getUsername() + " " + userInfo.getPassword());
                        userInfo.createAdminUser();

                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Invalid Input");
                        privKeyInput.setText("");
                    }
                });
            } else if (nonAdminType.isSelected()) {
                Users userInfo = new Users(tempUsername, tempPassword);
                System.out.println(userInfo.getUsername() + " " + userInfo.getPassword());
                userInfo.createStudentUser();
            }
        });

        return register;
    }

}
