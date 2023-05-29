import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FinishedExam extends Exam {
    private final String userTakingExam = new LoggedInUser().getUsername();
    private String examName;
    private String examSubject;
    private Exam exam;
    private JFrame mainFrame;
    private ArrayList<Question> allQuestions;
    private JPanel allQuestionsPanel = new JPanel(new GridBagLayout());
    private static final Dimension questionTextDimensions = new Dimension(300, 65);

    private LinkedHashMap<Question, Integer> answerButtonChosen = new LinkedHashMap<>();
    private LinkedHashMap<Question, String> answerTextGiven = new LinkedHashMap<>();

    private static final GridBagConstraints gbc;
    static {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 80, 5);
    }

    public FinishedExam (Exam exam) {
        loadQuestionList("Exams/FinishedExams/" + exam.getNameOfExam() + ".json");
        System.out.println("Question list size: " + allQuestions.size());
        this.exam = exam;
        examName = exam.getNameOfExam();
        examSubject = exam.getSubjectOfExam();
        renderQuestions(allQuestions, allQuestionsPanel);
        displayExam(exam);
        System.out.println();
    }

    public void displayExam(Exam exam) {

        mainFrame = new JFrame("Complete your exam");

        JScrollPane examScrollPane = new JScrollPane(allQuestionsPanel);

        examScrollPane.setPreferredSize(new Dimension(1600, 900));
        examScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainFrame.getContentPane().add(examScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton submitExam = new JButton("Submit exam");
        submitExam.setPreferredSize(new Dimension(200, 30));
        bottomPanel.add(submitExam);
        submitExam(submitExam);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.setSize(1600, 900);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void submitExam(JButton submitExam) {
        submitExam.addActionListener(e -> {
            String confirmation = "Are you sure you want to submit this exam? \n";
            String emptyQuestions = "";
            int questionIndex = 0;

            //check if there are any missing answers
            for (Question question : answerButtonChosen.keySet()) {
                int buttonChosen = answerButtonChosen.get(question);
                if (buttonChosen == -1) {
                    confirmation += "You have some unfinished questions: ";
                    emptyQuestions += "Q" + (question.getRealQuestionNumber()) + " ";
                }
                questionIndex++;
            }
            for (Question question : answerTextGiven.keySet()) {
                String textGiven = answerTextGiven.get(question);
                if (textGiven.isBlank()) {
                    confirmation += "You have some unfinished questions: ";
                    emptyQuestions += "Q" + (question.getRealQuestionNumber()) + " ";
                }
                questionIndex++;
            }

            Object selectedValue = new Object();
            if (!emptyQuestions.isEmpty()) {
                //set ans as the value of the YES_NO option
                int ans = JOptionPane.showConfirmDialog(null, confirmation + emptyQuestions,
                        "Submit Exam?", JOptionPane.YES_NO_OPTION);

                if (ans == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Saved");
//                    ExamSerializer.serializeQuestionsToJson();
                } else {
                    JOptionPane.showMessageDialog(null, "Not Saved");
                }
            } else {
                int ans = JOptionPane.showConfirmDialog(null, confirmation + emptyQuestions,
                        "Submit Exam?", JOptionPane.YES_NO_OPTION);

                if (ans == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Saved");
                } else {
                    JOptionPane.showMessageDialog(null, "Not Saved");
                }
            }

        });
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
                case TRUE_FALSE:
                    createTrueFalse(allQuestionsPanel, index, question);
                    index++;
                    break;
                default:
                    System.out.println("if you reach this you've done something very wrong");
                    break;
            }
        }
    }

    public void createMultipleChoice(JPanel allQuestionsPanel, int index, Question question) {
        //set the answer index to impossible value at beginning
        answerButtonChosen.put(question, -1);

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
            int answerIndex = i;
            JRadioButton answerN = new JRadioButton(question.getAnswers(i));
            answerN.setFont(answerFont);
            bg.add(answerN);
            answers.add(answerN);

            ItemListener itemListener = e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    answerButtonChosen.put(question, answerIndex);
                    System.out.println("The answer " + answerN.getText() + " which is answer index " + answerIndex + " has been given" +
                            " to question " + question.getRealQuestionNumber());
                    System.out.println("The hashMap reveals " + answerButtonChosen.get(question));
                }
            };
            answerN.addItemListener(itemListener);
        }

        questionPanel.add(answers, constraints);


        allQuestionsPanel.add(questionPanel, gbc);
    }

    public void createShortAnswer(JPanel allQuestionsPanel, int index, Question question) {
        //set the answer index to impossible value at beginning
        answerTextGiven.put(question, "");

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

        DelayedListener.addDelayedListener(answer, 250, () -> {
            String answerInputtedByUser;
            answerInputtedByUser = answer.getText();
            answerTextGiven.put(question, answerInputtedByUser);
            System.out.println("The answer " + answerInputtedByUser + " has been given" +
                    " to question " + question.getRealQuestionNumber());
            System.out.println("The hashMap reveals " + answerTextGiven.get(question));
        });
        questionPanel.add(answerPanel, constraints);

        allQuestionsPanel.add(questionPanel, gbc);
    }

    public void createLongAnswer(JPanel allQuestionsPanel, int index, Question question) {

    }

    public void createTrueFalse(JPanel allQuestionsPanel, int index, Question thisQuestion) {
        //set the answer index to impossible value at beginning
        answerButtonChosen.put(thisQuestion, -1);

        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JPanel answerPanel = new JPanel();
        JLabel questionNum = new JLabel("Q" + (index + 1) + ": ");
        JLabel question = new JLabel();
        ButtonGroup buttonGroup = new ButtonGroup();

        question.setText(thisQuestion.getQuestion());

        JScrollPane questionTextPane = new JScrollPane(question);
        questionTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        questionTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questionTextPane.setPreferredSize(new Dimension(questionTextDimensions));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; //set 0 columns
        c.gridy = GridBagConstraints.RELATIVE; //set row to 0, increment as components are added
        c.insets = new Insets(5, 5, 5, 5); //padding between components




        for (int i=0; i < 2; i++) {
            int answerIndex = i;

            if (i==0) {
                JRadioButton trueButton = new JRadioButton("TRUE");
                buttonGroup.add(trueButton);
                answerPanel.add(trueButton);

                ItemListener itemListener = e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        answerButtonChosen.put(thisQuestion, answerIndex);
                        System.out.println("The answer " + trueButton.getText() + " which is answer index " + answerIndex + " has been given" +
                                " to question " + thisQuestion.getRealQuestionNumber());
                        System.out.println("The hashMap reveals " + answerButtonChosen.get(thisQuestion));
                    }
                };
                trueButton.addItemListener(itemListener);

            } else if (i==1) {
                JRadioButton falseButton = new JRadioButton("FALSE");
                buttonGroup.add(falseButton);
                answerPanel.add(falseButton);

                ItemListener itemListener = e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        answerButtonChosen.put(thisQuestion, answerIndex);
                        System.out.println("The answer " + falseButton.getText() + " which is answer index " + answerIndex + " has been given" +
                                " to question " + thisQuestion.getRealQuestionNumber());
                        System.out.println("The hashMap reveals " + answerButtonChosen.get(thisQuestion));
                    }
                };
                falseButton.addItemListener(itemListener);
            }
        }

        questionPanel.add(questionNum);
        questionPanel.add(questionTextPane);
        questionContainer.add(questionPanel, c);
        c.insets = new Insets(0, 0, 0, 110);
        questionContainer.add(answerPanel, c);



        allQuestionsPanel.add(questionContainer, gbc);
    }

    private void loadQuestionList(String filePath) {
        try {
            allQuestions = ExamSerializer.deserializeQuestionsFromJson(filePath);
            for (Question question : allQuestions) {
                System.out.println(question.getQuestionType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
