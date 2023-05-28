import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class FinishedExam extends Exam {
    private String examName;
    private String examSubject;
    private Exam exam;
    private JFrame mainFrame;
    ArrayList<Question> allQuestions;
    private JPanel allQuestionsPanel = new JPanel(new GridBagLayout());

    private static final GridBagConstraints gbc;
    static {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 80, 5);
    }

    public FinishedExam (Exam exam) {
        allQuestions = loadQuestionList("Exams/FinishedExams/" + exam.getNameOfExam() + "Questions.ser");
        System.out.println("Question list size: " + allQuestions.size());
        this.exam = exam;
        examName = exam.getNameOfExam();
        examSubject = exam.getSubjectOfExam();
        renderQuestions(allQuestions, allQuestionsPanel);
        displayExam(exam);
        System.out.println();
    }

    public void displayExam(Exam examGiven) {

        mainFrame = new JFrame("Complete your exam");

        JScrollPane examScrollPane = new JScrollPane(allQuestionsPanel);

        examScrollPane.setPreferredSize(new Dimension(1600, 900));
        examScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainFrame.getContentPane().add(examScrollPane, BorderLayout.CENTER);
        mainFrame.setSize(1600, 900);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void renderQuestions(ArrayList<Question> allQuestions, JPanel allQuestionsPanel) {
        int index = 0;
        for (Question question : allQuestions) {
            /* switch case dependent on type,
             * then run create[type]Answer*/
            switch (question.getQuestionType()) {
                case MULTIPLE_CHOICE:
                    createMultipleChoice(allQuestionsPanel, index, question);
                    index++;
                    break;
                case SHORT_ANSWER:
                    createShortAnswer(allQuestionsPanel, index, question);
                    index++;
                    break;
                case LONG_ANSWER:
                    createLongAnswer(allQuestionsPanel, index, question);
                    index++;
                    break;
                default:
                    System.out.println("if you reach this you've done something very wrong");
                    break;
            }
        }
    }

    public void createMultipleChoice(JPanel allQuestionsPanel, int index, Question question) {
        JPanel questionPanel = new JPanel(new GridBagLayout());
        JLabel questionText = new JLabel();
        //JLabel question = new JLabel("Q" + (index + 1) + ": ");
        JPanel answers = new JPanel();
        ButtonGroup bg = new ButtonGroup();
        Font questionFont = new Font("Arial", Font.PLAIN, 20);
        Font answerFont = new Font("Arial", Font.BOLD, 12);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;

        questionText.setText("Q" + (index+1) + ": " + question.getQuestion());
        questionText.setFont(questionFont);
        questionPanel.add(questionText, constraints);

        constraints.gridy = 1;

        for (int i=0; i < question.getAnswers().size(); i++) {
            JRadioButton answerN = new JRadioButton(question.getAnswers(i));
            answerN.setFont(answerFont);
            bg.add(answerN);
            answers.add(answerN);
        }

        questionPanel.add(answers, constraints);


        allQuestionsPanel.add(questionPanel, gbc);
    }

    public void createShortAnswer(JPanel allQuestionsPanel, int index, Question question) {
        JPanel questionPanel = new JPanel(new GridBagLayout());
        JLabel questionText = new JLabel();
        JPanel answerPanel = new JPanel();
        Font questionFont = new Font("Arial", Font.PLAIN, 20);
        Font answerFont = new Font("Arial", Font.BOLD, 12);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;

        questionText.setText("Q" + (index+1) + ": " + question.getQuestion());
        questionText.setFont(questionFont);
        questionPanel.add(questionText, constraints);

        constraints.gridy = 1;

        JTextField answer = new JTextField();
        answer.setFont(answerFont);
        answer.setPreferredSize(new Dimension(200, 50));
        answerPanel.add(answer);
        questionPanel.add(answerPanel, constraints);

        allQuestionsPanel.add(questionPanel, gbc);
    }

    public void createLongAnswer(JPanel allQuestionsPanel, int index, Question question) {

    }

    private static ArrayList<Question> loadQuestionList(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<Question> questionList = (ArrayList<Question>) in.readObject();
            in.close();
            fileIn.close();
            return questionList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
