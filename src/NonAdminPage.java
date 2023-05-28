import javax.swing.*;
import java.awt.*;
import java.io.File;

public class NonAdminPage extends SharedPage {
    private static JButton tempButton;
    File questionPath = new File("Exams/FinishedExams/");

    public void createNonAdminPage() {
        createSharedPage();
        logOutClicked();

        System.out.println("These are all the finished exams: " + getAllFinishedExams().toString());
        tempButton = new JButton("Temp button");
        leftMenu.add(tempButton);

        createFinishedExamList();


    }

    private void createFinishedExamList() {
        exams = new JPanel();
        exams.setLayout(new BoxLayout(exams, BoxLayout.Y_AXIS));
        for (int i = 0; i < getAllFinishedExams().size(); i++) {
            final int index = i;
            Exam exam = getAllFinishedExams().get(i);

            JButton button = new JButton("<html>"+"<h1>" + exam.getNameOfExam() + "</h1>"+"<br>" + "<br>" + "<center>" + exam.getSubjectOfExam() + "</center>" + "</html>");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 300)); //sets width to 200, but for some reason doesnt work on height
            button.setPreferredSize(new Dimension(200, 250)); //sets height to 300, but for some reason doesnt work on width
            exams.add(Box.createVerticalStrut(10));
            exams.add(button);

            button.addActionListener(e -> {
                FinishedExam finishedExam = new FinishedExam(exam);
            });

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


