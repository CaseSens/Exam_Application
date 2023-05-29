import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Exam extends ExamList implements Serializable {
    private String nameOfExam;
    private String subjectOfExam;
    private JButton submit = new JButton("Submit");
    private ArrayList<Question> allExamQuestions = new ArrayList<>();
    boolean isVisible = false;

    Exam() {

    }

    Exam(String name, String subject) { //when you want to access exam names
        this.nameOfExam = name;
        this.subjectOfExam = subject;
        ExamList.nameToSubjectMap.put(name, subject);

    }

    Exam(AdminPage adminPageInstance) //when you want to create an exam
    {
        JFrame createExamFrame = new JFrame("Create your exam");
        JLabel askNameOfExam = new JLabel("Enter the name of your exam");
        JLabel askSubjectOfExam = new JLabel("Enter the subject of your exam");
        JTextField enterNameOfExam = new JTextField();
        JTextField enterSubjectOfExam = new JTextField();
        JPanel enterExamField = new JPanel();
        enterExamField.setLayout(new GridLayout(3, 2, 10, 10));

        JPanel buttonCenterPanel = new JPanel();
        buttonCenterPanel.setLayout(new BoxLayout(buttonCenterPanel, BoxLayout.X_AXIS));
        buttonCenterPanel.add(Box.createHorizontalGlue());
        buttonCenterPanel.add(submit);
        buttonCenterPanel.add(Box.createHorizontalGlue());

        enterExamField.add(askNameOfExam);
        enterExamField.add(enterNameOfExam);
        enterExamField.add(askSubjectOfExam);
        enterExamField.add(enterSubjectOfExam);
        createExamFrame.setSize(500, 500);
        createExamFrame.add(enterExamField);
        createExamFrame.add(buttonCenterPanel, BorderLayout.SOUTH);

        createExamFrame.pack();
        createExamFrame.setLocationRelativeTo(null);
        createExamFrame.setVisible(true);


        submit.addActionListener(e -> {
            nameOfExam = enterNameOfExam.getText();
            subjectOfExam = enterSubjectOfExam.getText();
            doesExamExist(nameOfExam, subjectOfExam);
            if (!doesExamExist(nameOfExam, subjectOfExam)) {
                addExamToList(this);
                createExamFrame.setVisible(false);
                adminPageInstance.closeMainFrame();
                resetPage();
            } else {
                JOptionPane.showMessageDialog(null, "Error: Exam already exists");
            }
        });
    }



    public void resetPage() {
        ExamList.numberOfExams = 0;
        ExamList.allExamNames.clear();
        ExamList.allExamSubjects.clear();
        ExamList.nameToSubjectMap.clear();
        ExamList.examArrayList.clear();

        setNumberOfExamsOnFile();
        createExamMap();
        AdminPage adminPage = new AdminPage();
        adminPage.createAdminPage();
    }



    public String getNameOfExam() {
        return nameOfExam;
    }
    public String getSubjectOfExam() {
        return subjectOfExam;
    }

    public boolean isExamFinished() {
        return isVisible;
    }

    public ArrayList<Question> getAllExamQuestions() {
        return allExamQuestions;
    }

    public void setAllExamQuestions(ArrayList<Question> q) {
        allExamQuestions = q;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean getVisibility() {
        return isVisible;
    }

}
