import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPage extends SharedPage {

    public void createAdminPage() {
        Exam examInfo = new Exam();
        createSharedPage();

        logOutClicked();
        JButton createExam = new JButton("Create exam");
        leftMenu.add(createExam);

        createExam.addActionListener(e -> {
            Exam newExam = new Exam(this);
//            newExam.createExamOnceInitialized();
        });
    }

    public void logOutClicked() {
        logoutButton.addActionListener(e -> {
            LogOrRegister logOrRegister = new LogOrRegister();
            closeMainFrame();
            logOrRegister.setLoggedIn(false, false);
            LogOrRegister newLoginPage = new LogOrRegister();
            newLoginPage.buildLoginPage();
        });
    }

    public void closeMainFrame() {
        mainFrame.dispose();
    }

}
