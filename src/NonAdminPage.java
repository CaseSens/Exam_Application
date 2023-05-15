import javax.swing.*;

public class NonAdminPage extends SharedPage{

    public void createNonAdminPage() {
        createSharedPage();
        logOutClicked();
    }

    public void logOutClicked() {
        logoutButton.addActionListener(e -> {
            LogOrRegister logOrRegister = new LogOrRegister();
            closeMainframe();
            logOrRegister.setLoggedIn(false, false);
            LogOrRegister newLoginPage = new LogOrRegister();
            newLoginPage.buildLoginPage();
        });
    }

    public void closeMainframe() {
            mainFrame.dispose();
    }
}


