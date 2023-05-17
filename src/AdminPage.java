import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class AdminPage extends SharedPage {
    File filePath = new File("Exams/UnfinishedExams/examSheet.ser");

    public void createAdminPage() {
        Exam examInfo = new Exam();
        createSharedPage();
        createUnfinishedExamList(examInfo); //-------------------------------------------

        logOutClicked();
        JButton createExam = new JButton("Create exam");
        leftMenu.add(createExam);

        createExam.addActionListener(e -> {
            Exam newExam = new Exam(this);
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

    public void createUnfinishedExamList(Exam examInfo) {
        exams = new JPanel();
        exams.setLayout(new BoxLayout(exams, BoxLayout.Y_AXIS));
        for (int i = 0; i < examInfo.getNumberOfExamsOnFile(); i++) {
            JButton button = new JButton("<html>"+"<h1>" + examInfo.getNameOfExam(i) + "</h1>"+"<br>" + "<br>" + "<center>" + examInfo.getSubjectOfExam(i) + "</center>" + "</html>");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 300)); //sets width to 200, but for some reason doesnt work on height
            button.setPreferredSize(new Dimension(200, 250)); //sets height to 300, but for some reason doesnt work on width
            exams.add(Box.createVerticalStrut(10));
            exams.add(button);

            button.addActionListener(e -> {
                ExamSheet examSheet = new ExamSheet();
            });
        }

        exams.add(Box.createVerticalGlue());
        Component[] components = exams.getComponents();
        for (Component component : components) {
            component.setBackground(Color.CYAN);
        }

        JScrollPane examsScrollPane = new JScrollPane(exams);
        examsScrollPane.setPreferredSize(new Dimension(1920, 1080));
        examsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainFrame.getContentPane().add(examsScrollPane, BorderLayout.CENTER);
    }
}
