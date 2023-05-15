import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Exam {
    private boolean VisibleToStudents = false;
    private static int numberOfExams;
    private ArrayList<Exam> examObjects= new ArrayList<>();
    private static ArrayList<String> nameOfExamsByIndex = new ArrayList<>();
    private static ArrayList<String> nameOfSubjectsByIndex = new ArrayList<>();



    private String tempNameExam;
    private String tempSubjectExam;
    private File examFile = new File("EXAM-LIST.txt");
    private static HashMap<String, String> nameToSubjectMap = new HashMap<>(); //reason why not just hashmap is because I want doubles of name/subject but not together


    private static HashMap<Integer, HashMap<String, String>> examByNumberMap = new HashMap<>();
    private static HashMap<Exam, Boolean> objectMap = new HashMap<>();
    private BufferedReader br;
    private JButton submit = new JButton("Submit");

    Exam () {

    }

    Exam (String name, String subject) { //when you want to access exam names
        nameOfExamsByIndex.add(name);
        nameOfSubjectsByIndex.add(subject);
    }

    Exam (AdminPage adminPageInstance) //when you want to create an exam
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
            doesExamExist();
            if (!doesExamExist()) {
                addExamToList();
                createExamFrame.setVisible(false);
                adminPageInstance.closeMainFrame();
                resetPage();
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
            int i = 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String keyName = parts[0];
                String valueSubject = parts[1];
                nameToSubjectMap.put(keyName, valueSubject);

                Exam newExam = new Exam(keyName, valueSubject);
                objectMap.put(newExam, false);
                i++;

                HashMap<String, String> examMap = new HashMap<>();
                examMap.put(keyName, valueSubject);

                examByNumberMap.put(i-1, examMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(nameToSubjectMap);

    }

    public void setExamVisibleToStudents(Exam exam) {
        boolean isVisible = objectMap.get(exam);
        if (!isVisible) {
            objectMap.put(exam, true);
        }
    }

    public boolean doesExamExist() {
        for (String key : nameToSubjectMap.keySet()) { //for loop to ignore case
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
            System.out.println("number of exams: " + getNumberOfExamsOnFile());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetPage() {
        numberOfExams=0;
        nameOfExamsByIndex.clear();
        nameOfSubjectsByIndex.clear();
        nameToSubjectMap.clear();
        examByNumberMap.clear();

        setNumberOfExamsOnFile();
        createExamMap();
        AdminPage adminPage = new AdminPage();
        adminPage.createAdminPage();
    }

    public int getNumberOfExamsOnFile() {
        return numberOfExams;
    }

    public String getNameOfExam(int examNumber) {
        return nameOfExamsByIndex.get(examNumber);
    }

    public String getSubjectOfExam(int examNumber) {
        return nameOfSubjectsByIndex.get(examNumber);
    }

    public HashMap<Integer, HashMap<String, String>> getAllExamsByNumberMap() {
        return examByNumberMap;
    }

    public void examMapPerLine() {
        int i = 1;
        for (HashMap<String, String> examsByNumber : examByNumberMap.values()) {
            System.out.println("Exam " + i + ": " + examsByNumber);
            i++;
        }
    }

    public boolean isExamFullyCreated() {
        return isExamFullyCreated();
    }

//    public HashMap<String, String> getValueAsString(int i) {
//        return examByNumberMap.get(i);
//    }
}
