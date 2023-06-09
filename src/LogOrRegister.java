import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;

public class LogOrRegister {
    private static JButton submit;
    private static JButton registerBtn;
    private static JFrame mainFrame;
    private static JTextField usernameText;
    private static JTextField passwordText;
    private static UserFileMap userFileMap = new UserFileMap();
    private static LinkedHashSet<String> combinedKeySet = new LinkedHashSet<>();
    private static LinkedHashSet<String> combinedAdminSet = new LinkedHashSet<>();
    private static LinkedHashSet<String> combinedStudentSet = new LinkedHashSet<>();
    public volatile boolean isLoggedIn = false;
    public volatile boolean isAdmin = false;

    public void buildLoginPage() {
        createKeySet();
        createMainFrame();
    }

    private JFrame createMainFrame() {
        mainFrame = new JFrame("Please Log In");
        mainFrame.setSize(900, 900);
        mainFrame.setLayout(null);
        mainFrame.add(loginPanel());
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return mainFrame;
    }

    private JPanel loginPanel()
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

    private static void updateKeySet(String username) {
        combinedAdminSet.add(username);
        combinedKeySet.add(username);
    }

    private static void registerClicked()
    {
        registerBtn.addActionListener(e -> {
            String username = usernameText.getText();
            String password = passwordText.getText();

                if (!combinedKeySet.contains(username)) {
                    createRegisterPage(username, password);
                } else {
                    JOptionPane.showMessageDialog(null, "You have entered a username that is already" +
                            " in use, please login instead.");
                }
        });
    }

    private void loginClicked()
    {
        submit.addActionListener(e -> {
            String username = usernameText.getText();
            String password = passwordText.getText();

            if (combinedAdminSet.contains(username)) {
                String adminPassword = userFileMap.getAdminUsers().get(username);
                if (adminPassword != null && adminPassword.equals(password)) {
                    setLoggedIn(true, true);
                    AdminPage adminPage = new AdminPage();
                    adminPage.createAdminPage();
                    mainFrame.setVisible(false);
                    System.out.println("welcome " + username);
                    System.out.println(isLoggedIn + " " + isAdmin);
                } else {
                    JOptionPane.showMessageDialog(null, "You have entered the wrong password");
                }
            } else if (combinedStudentSet.contains(username)) {
                String studentPassword = userFileMap.getStudentUsers().get(username);
                if(studentPassword != null && studentPassword.equals(password)) {
                    setLoggedIn(true, false);
                    LoggedInUser loggedInUser = new LoggedInUser(username, password);
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

    public synchronized void setLoggedIn(boolean loggedIn, boolean admin) {
        isLoggedIn = loggedIn;
        isAdmin = admin;
    }

    public synchronized boolean getAdmin() {
        return isAdmin;
    }

    private static JFrame createRegisterPage(String username, String password)
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
//                        mainFrame.setVisible(false);

                        Users userInfo = new Users(username, password);
                        System.out.println(userInfo.getUsername() + " " + userInfo.getPassword());
                        userInfo.createAdminUser();
                        updateKeySet(username);

                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Invalid Input");
                        privKeyInput.setText("");
                    }
                });
            } else if (nonAdminType.isSelected()) {
                Users userInfo = new Users(username, password);
                System.out.println(userInfo.getUsername() + " " + userInfo.getPassword());
                userInfo.createStudentUser();
            }
        });

        return register;
    }

}
