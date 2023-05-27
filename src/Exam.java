import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Exam implements Serializable {
    private static int numberOfExams;
    private String nameOfExam;
    private String subjectOfExam;
    private static ArrayList<String> allExamNames = new ArrayList<>();
    private static ArrayList<String> allExamSubjects = new ArrayList<>();
    private ArrayList<Exam> examObjects = new ArrayList<>();
    private static final File unfinishedExamsFile = new File("Exams/UnfinishedExams/EXAM-LIST.txt");
    private static HashMap<String, String> nameToSubjectMap = new HashMap<>(); //reason why not just hashmap is because I want doubles of name/subject but not together


    private static ArrayList<Exam> examArrayList = new ArrayList<>();
    private static HashMap<Exam, Boolean> examVisibilityMap = new HashMap<>();
    private BufferedReader br;
    private JButton submit = new JButton("Submit");

    Exam() {

    }

    Exam(String name, String subject) { //when you want to access exam names
        this.nameOfExam = name;
        this.subjectOfExam = subject;
        nameToSubjectMap.put(name, subject);


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
            String nameOfThisExam = enterNameOfExam.getText();
            String subjectOfThisExam = enterSubjectOfExam.getText();
            doesExamExist();
            if (!doesExamExist()) {
                addExamToList(nameOfThisExam, subjectOfThisExam);
                createExamFrame.setVisible(false);
                adminPageInstance.closeMainFrame();
                resetPage();
            } else {
                JOptionPane.showMessageDialog(null, "Error: Exam already exists");
            }
        });
    }

    public void createExamMap() {
        try {
            br = new BufferedReader(new FileReader(unfinishedExamsFile));
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String keyName = parts[0];
                String valueSubject = parts[1];
                nameToSubjectMap.put(keyName, valueSubject);

                Exam newExam = new Exam(keyName, valueSubject);
                examVisibilityMap.put(newExam, false);


                examArrayList.add(i - 1, newExam);
                System.out.println(examArrayList.get(i - 1).getNameOfExam());
                i++;
                nameToSubjectMap.put(keyName, valueSubject);
                allExamNames.add(keyName);
                allExamSubjects.add(valueSubject);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(nameToSubjectMap);

    }

    public Exam getExamAtIndex(int index) {
        return examArrayList.get(index);
    }

    public int getIndexOfExam(Exam exam) {
        for (int i = 0; i < examArrayList.size(); i++) {
            if (examArrayList.get(i).equals(exam)) {
                return i; // Found the index for the specified Exam object
            }
        }
        return -1; // Exam not found in the ArrayList
    }

    public boolean doesExamExist() {
        for (String key : nameToSubjectMap.keySet()) { //for loop to ignore case
            if (key.equalsIgnoreCase(nameOfExam) && nameToSubjectMap.get(key).equalsIgnoreCase(subjectOfExam)) {
                return true;
            }
        }
        return false;
    }

    public void addExamToList(String nameOfThisExam, String subjectOfThisExam) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(unfinishedExamsFile, true));
            writer.println(nameOfThisExam + " " + subjectOfThisExam);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNumberOfExamsOnFile() {
        try {
            br = new BufferedReader(new FileReader(unfinishedExamsFile));
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
        numberOfExams = 0;
        allExamNames.clear();
        allExamSubjects.clear();
        nameToSubjectMap.clear();
        examArrayList.clear();

        setNumberOfExamsOnFile();
        createExamMap();
        AdminPage adminPage = new AdminPage();
        adminPage.createAdminPage();
    }

    public int getNumberOfExamsOnFile() {
        return numberOfExams;
    }

    public String getSubjectOfExam() {
        return subjectOfExam;
    }

    public String getNameOfExam() {
        return nameOfExam;
    }

    public String getNameOfExamAtIndex(int index) {
        return allExamNames.get(index);
    }

    public String getSubjectOfExamAtIndex(int index) {
        return allExamSubjects.get(index);
    }

    public ArrayList<Exam> getAllExamsByNumberMap() {
        return examArrayList;
    }

    /**
     * simply prints out all exams
     */
    public void examListPerLine() {
        for (int i = 0; i < examArrayList.size(); i++) {
            System.out.println("Exam " + (i + 1) + ": " + getNameOfExam());
        }
    }

    public boolean isExamFullyCreated() {
        return isExamFullyCreated();
    }
}
