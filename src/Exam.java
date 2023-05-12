import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Exam {
    private static int numberOfExams;

    private String tempNameExam;
    private String tempSubjectExam;
    private File examFile = new File("EXAM-LIST.txt");
    private HashMap<String, String> nameToSubjectMap = new HashMap<>(); //reason why not just hashmap is because I want doubles of name/subject but not together
    private BufferedReader br;
    private JButton submit = new JButton("Submit");

    Exam () {

    }

    Exam (boolean createExam)
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
            tempNameExam = enterNameOfExam.getText();
            tempSubjectExam = enterSubjectOfExam.getText();
            createExamMap();
            doesExamExist();
            if (!doesExamExist()) {
                addExamToList();
            } else {
                JOptionPane.showMessageDialog(null, "Error: Exam already exists");
            }
        });
    }

    public void createExamOnceInitialized(/*int amountOfMultipleChoice, int amountOfShortAnswer, int amountOfLongAnswer*/)
    {

    }

    public void createExamMap() {
        try {
            br = new BufferedReader(new FileReader(examFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String keyName = parts[0];
                String valueSubject = parts[1];
                nameToSubjectMap.put(keyName, valueSubject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean doesExamExist() {
        for (String key : nameToSubjectMap.keySet()) { //for loop to ignorecase
            if (key.equalsIgnoreCase(tempNameExam) && nameToSubjectMap.get(key).equalsIgnoreCase(tempSubjectExam)) {
                return true;
            }
        }
        return false;
    }

    public void addExamToList() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(examFile, true));
            writer.println(tempNameExam + " " + tempSubjectExam);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setNumberOfExamsOnFile() {
        try {
            br = new BufferedReader(new FileReader(examFile));
            String line;
            while ((line = br.readLine()) != null) {
                numberOfExams++;
            }
            System.out.println("number of exams: "+getNumberOfExamsOnFile());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfExamsOnFile() {
        return numberOfExams;
    }
}
