import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Flow;

public class ExamSheet extends AdminPage{

    ArrayList<Question> questions = new ArrayList<>();
    JFrame examSheet;
    private static final GridBagConstraints gbc;
    static {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 80, 5);
    }
    private static final Dimension questionTextDimensions = new Dimension(300, 65);

    private JPanel allQuestionsContainer;
    private JScrollPane examScrollPane;

    ExamSheet () {
        createExamSheet();
    }
    public void createExamSheet() {

        JPanel addMenu = new JPanel();
        BoxLayout leftMenuLayout = new BoxLayout(addMenu, BoxLayout.Y_AXIS);
        addMenu.setLayout(leftMenuLayout);

        Font font = new Font("Arial", Font.PLAIN, 11);

        JButton addMultipleChoice = new JButton("Multiple Choice");
        addMultipleChoice.setFont(font);
        addMultipleChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
        addMultipleChoice.setMaximumSize(new Dimension(150, 50)); //sets height but for some reason doesnt work on width
        addMultipleChoice.setPreferredSize(new Dimension(150, 50)); //sets width but for some reason doesnt work on height
        addMenu.add(addMultipleChoice);
        addMenu.add(Box.createVerticalStrut(10));

        JButton addShortAnswer = new JButton("Short Answer");
        addShortAnswer.setFont(font);
        addShortAnswer.setAlignmentX(Component.CENTER_ALIGNMENT);
        addShortAnswer.setMaximumSize(new Dimension(150, 50)); //sets height but for some reason doesnt work on width
        addShortAnswer.setPreferredSize(new Dimension(150, 50)); //sets width but for some reason doesnt work on height
        addMenu.add(addShortAnswer);
        addMenu.add(Box.createVerticalStrut(10));

        JButton addLongAnswer = new JButton("Long Answer");
        addLongAnswer.setFont(font);
        addLongAnswer.setAlignmentX(Component.CENTER_ALIGNMENT);
        addLongAnswer.setMaximumSize(new Dimension(150, 50)); //sets height but for some reason doesnt work on width
        addLongAnswer.setPreferredSize(new Dimension(150, 50)); //sets width but for some reason doesnt work on height
        addMenu.add(addLongAnswer);
        addMenu.add(Box.createVerticalStrut(10));

        JPanel centerLeftPanel = new JPanel();
        BoxLayout centerMenuLayout = new BoxLayout(centerLeftPanel, BoxLayout.X_AXIS);
        centerLeftPanel.setLayout(centerMenuLayout);
        centerLeftPanel.add(Box.createVerticalGlue());
        centerLeftPanel.add(addMenu, BorderLayout.WEST);
        centerLeftPanel.setBackground(Color.cyan);

        //----------------------------------------------------------------------

        allQuestionsContainer = new JPanel();
        allQuestionsContainer.setLayout(new GridBagLayout());

        addMultipleChoice.addActionListener(e -> {
            initializeQuestion(QuestionType.MULTIPLE_CHOICE);
            renderQuestions();
        });

        addShortAnswer.addActionListener(e -> {
            initializeQuestion(QuestionType.SHORT_ANSWER);
            renderQuestions();
        });

        addLongAnswer.addActionListener(e -> {
            initializeQuestion(QuestionType.LONG_ANSWER);
            renderQuestions();
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel visibility = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveExam = new JButton("Save exam");
        JCheckBox setVisible = new JCheckBox("Make exam visible to students?");
        visibility.add(setVisible);
        bottomPanel.add(saveExam, BorderLayout.CENTER);
        bottomPanel.add(visibility, BorderLayout.EAST);


        examScrollPane = new JScrollPane();
        examScrollPane.setPreferredSize(new Dimension(1600, 900));
        examScrollPane.setViewportView(allQuestionsContainer);
        examScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        examSheet = new JFrame("Create your exam");
        examSheet.add(centerLeftPanel, BorderLayout.WEST);
        examSheet.add(bottomPanel, BorderLayout.SOUTH);

        examSheet.getContentPane().add(examScrollPane, BorderLayout.CENTER);
        examSheet.setSize(1600, 900);
        examSheet.setLocationRelativeTo(null);
        examSheet.setVisible(true);
    }

    public void renderQuestions() { //repaint allQuestionContainer as they are
        resetContainer(allQuestionsContainer);
        int index = 0;
        for (Question question : questions) {
            /* switch case dependent on type,
            * then run create[type]Answer*/
            switch (question.getQuestionType()) {
                case MULTIPLE_CHOICE:
                    createMultipleChoice(allQuestionsContainer, index, question.getQuestion());
                    index++;
                    break;
                case SHORT_ANSWER:
                    createShortAnswer(allQuestionsContainer, index, question.getQuestion());
                    index++;
                    break;
                case LONG_ANSWER:
                    createLongAnswer(allQuestionsContainer, index, question.getQuestion());
                    index++;
                    break;
                default:
                    System.out.println("bruh");
                    break;
            }
        }
    }

    private void createLongAnswer(JPanel allQuestionsContainer, int index, String questionText) {
        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JLabel question = new JLabel("Q" + (index + 1) + ": ");
        JTextArea questionInput = new JTextArea();
        questionInput.setLineWrap(true);
        questionInput.setWrapStyleWord(true);
        questionInput.setText(questionText);
        JScrollPane questionTextPane = new JScrollPane(questionInput);
        questionTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        questionTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questionTextPane.setPreferredSize(new Dimension(500, 250));
        JButton removeQuestion = new JButton("Remove question");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; //set 0 columns
        c.gridy = GridBagConstraints.RELATIVE; //set row to 0, increment as components are added
        c.insets = new Insets(5, 5, 5, 5); //padding between components

        questionPanel.setLayout(new FlowLayout());
        questionPanel.add(question);
        questionPanel.add(questionTextPane);
        questionPanel.add(removeQuestion);

        questionContainer.add(questionPanel, c);

        allQuestionsContainer.add(questionContainer, gbc);
        examSheet.revalidate();

        removeQuestion.addActionListener(e1 -> {
            allQuestionsContainer.remove(questionContainer);
            questions.remove(index);
            renderQuestions();
        });
    }

    public void resetContainer(JPanel container) {
        container.removeAll(); // Remove all components from the container
        container.revalidate();
        container.repaint();
    }

    private void createShortAnswer(JPanel allQuestionsContainer, int index, String questionText) {
        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JLabel question = new JLabel("Q" + (index + 1) + ": ");
        JTextArea questionInput = new JTextArea();
        questionInput.setLineWrap(true);
        questionInput.setWrapStyleWord(true);
        questionInput.setText(questionText);

        addDelayedListener(questionInput, 500, () -> {
            String questionInputtedByUser;
            questionInputtedByUser = questionInput.getText();
            questions.get(index).setQuestion(questionInputtedByUser);
            System.out.println("Question " + (index + 1) + " is " + questions.get(index).getQuestion());
        });

        JScrollPane questionTextPane = new JScrollPane(questionInput);
        questionTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        questionTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questionTextPane.setPreferredSize(questionTextDimensions);
        JButton removeQuestion = new JButton("Remove question");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; //set 0 columns
        c.gridy = GridBagConstraints.RELATIVE; //set row to 0, increment as components are added
        c.insets = new Insets(5, 5, 5, 5); //padding between components

        questionPanel.setLayout(new FlowLayout());
        questionPanel.add(question);
        questionPanel.add(questionTextPane);
        questionPanel.add(removeQuestion);

        questionContainer.add(questionPanel, c);

        allQuestionsContainer.add(questionContainer, gbc);
        examSheet.revalidate();

        removeQuestion.addActionListener(e1 -> {
            allQuestionsContainer.remove(questionContainer);
            questions.remove(index);
            renderQuestions();
        });
    }

    private void createMultipleChoice(JPanel allQuestionsContainer, int index, String questionText) {
        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JPanel answerPanel = new JPanel();
        JLabel question = new JLabel("Q" + (index + 1) + ": ");
        JTextArea questionInput = new JTextArea();
        questionInput.setLineWrap(true);
        questionInput.setWrapStyleWord(true);
        questionInput.setText(questionText);

        addDelayedListener(questionInput, 500, () -> {
            String questionInputtedByUser;
            questionInputtedByUser = questionInput.getText();
            questions.get(index).setQuestion(questionInputtedByUser);
            System.out.println("Question " + (index+1) + " is " + questions.get(index).getQuestion());
        });

        JScrollPane questionTextPane = new JScrollPane(questionInput);
        questionTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        questionTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Dimension multipleAnswerDimensions = new Dimension(300, 40);
        questionTextPane.setPreferredSize(new Dimension(questionTextDimensions));
        JButton removeQuestion = new JButton("Remove question");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; //set 0 columns
        c.gridy = GridBagConstraints.RELATIVE; //set row to 0, increment as components are added
        c.insets = new Insets(5, 5, 5, 5); //padding between components

        questionPanel.setLayout(new FlowLayout());
        questionPanel.add(question);
        questionPanel.add(questionTextPane);
        questionPanel.add(removeQuestion);

        questionContainer.add(questionPanel, c);

        char currentCharacter = 'A';
        for (int i=0; i<4; i++) {
            JLabel abcd = new JLabel(String.valueOf(currentCharacter) + ": ");
            JTextField answerInput = new JTextField();
            final int answerIndex = i;

            addDelayedListener(answerInput, 500, () -> {
                String answer;
                answer = answerInput.getText();
                questions.get(index).setAnswers(answerIndex, answer);
                System.out.println("Question " + (index+1) + "'s answers are: " + questions.get(index).getAnswers());
            });

            answerInput.setPreferredSize(new Dimension(multipleAnswerDimensions));
            answerPanel.add(abcd);
            answerPanel.add(answerInput);
            questionContainer.add(answerPanel, c);
            currentCharacter++;
        }

        allQuestionsContainer.add(questionContainer, gbc);
        examSheet.revalidate();

        removeQuestion.addActionListener(e1 -> {
            allQuestionsContainer.remove(questionContainer);
            questions.remove(index);
            renderQuestions();
        });
    }

    public void addDelayedListener(JTextField answer, int delay, Runnable action) {
        Timer timer = new Timer(delay, e -> {
            action.run();
        });
        timer.setRepeats(false);

           answer.getDocument().addDocumentListener(new DocumentListener() {

               private void scheduleTimer() {
                   timer.stop();
                   timer.start();
               }
               @Override
               public void insertUpdate(DocumentEvent e) {
                   scheduleTimer();
               }

               @Override
               public void removeUpdate(DocumentEvent e) {
                   scheduleTimer();
               }

               @Override
               public void changedUpdate(DocumentEvent e) {
                   scheduleTimer();
               }
           });
    }

    public void addDelayedListener(JTextArea question, int delay, Runnable action) {
        Timer timer = new Timer(delay, e -> {
            action.run();
        });
        timer.setRepeats(false);

        question.getDocument().addDocumentListener(new DocumentListener() {

            private void scheduleTimer() {
                timer.stop();
                timer.start();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                scheduleTimer();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                scheduleTimer();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                scheduleTimer();
            }
        });
    }

    public void initializeQuestion(QuestionType type) {
        Question newQuestion;
        switch (type) {
            case MULTIPLE_CHOICE:
                newQuestion = new Question("", "", type);
                break;
            case LONG_ANSWER:
            case SHORT_ANSWER:
            default:
                newQuestion = new Question("", "", type);
                break;
        }

        questions.add(newQuestion);
    }

//    private void refreshQuestionNumbers(JPanel allQuestionsContainer, int startIndex) {
//        Component[] components = allQuestionsContainer.getComponents(); //each questionContainer
//        for (int i = 0; i < components.length; i++) { //loop through them
//            if (components[i] instanceof JPanel) { //if component is JPanel
//                JPanel panel = (JPanel) components[i]; //create a new JPanel from that component
//                Component[] innerComponents = panel.getComponents(); //each components of questionContainer
//                for (Component component : innerComponents) { //iterate through the inner components
//                    if (component instanceof JPanel) {
//                        JPanel newQuestionPanel = (JPanel) component;
//                        Component[] questionComponents = newQuestionPanel.getComponents();
//                        for (Component questionComponent : questionComponents) {
//                            if (questionComponent instanceof JLabel) { //if its a JLabel
//                                JLabel label = (JLabel) questionComponent; //create new Label from the component
//                                String labelText = label.getText(); //get text from the Label
//                                if (labelText.startsWith("Q")) { //only get Labels that start with Q
//                                    int questionNumber = Integer.parseInt(labelText.substring(1, labelText.indexOf(":"))); //grab only the number
//                                    if (questionNumber > startIndex - 1) {
//                                        label.setText("Q" + (questionNumber - 1) + ": "); //set question - 1
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}
