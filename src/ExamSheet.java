import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ExamSheet extends AdminPage implements Serializable {
    Exam selectedExam;

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
    private JCheckBox setVisible = new JCheckBox("Make exam visible to students?");

    ExamSheet (Exam selectedExam) {
        this.selectedExam = selectedExam;
        try {
            String specifiedFile = selectedExam.getNameOfExam() + "/" + selectedExam.getNameOfExam() + ".json";
            String finishedPath = "Exams/FinishedExams/" + specifiedFile;
            String unfinishedPath = "Exams/UnfinishedExams/" + specifiedFile;

            String fileIn;
            File finishedFile = new File(finishedPath);
            File unfinishedFile = new File(unfinishedPath);

            if (finishedFile.exists()) {
                fileIn = finishedPath;
            } else {
                if (unfinishedFile.exists()) {
                    fileIn = unfinishedPath;
                } else {
                    throw new FileNotFoundException("File wasnt found");
                }
            }
            questions = ExamSerializer.deserializeQuestionsFromJson(fileIn);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        allQuestionsContainer = new JPanel(new GridBagLayout());
        examScrollPane = new JScrollPane(allQuestionsContainer);

        // Create the exam sheet GUI
        createExamSheet();

        // Render the questions in the allQuestionsContainer
        renderQuestions();


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

        JButton addTrueFalse = new JButton("True or False");
        addTrueFalse.setFont(font);
        addTrueFalse.setAlignmentX(Component.CENTER_ALIGNMENT);
        addTrueFalse.setMaximumSize(new Dimension(150, 50)); //sets height but for some reason doesnt work on width
        addTrueFalse.setPreferredSize(new Dimension(150, 50)); //sets width but for some reason doesnt work on height
        addMenu.add(addTrueFalse);
        addMenu.add(Box.createVerticalStrut(10));

        JPanel centerLeftPanel = new JPanel();
        BoxLayout centerMenuLayout = new BoxLayout(centerLeftPanel, BoxLayout.X_AXIS);
        centerLeftPanel.setLayout(centerMenuLayout);
        centerLeftPanel.add(Box.createVerticalGlue());
        centerLeftPanel.add(addMenu, BorderLayout.WEST);
        centerLeftPanel.setBackground(Color.cyan);

        //----------------------------------------------------------------------

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

        addTrueFalse.addActionListener(e -> {
            initializeQuestion(QuestionType.TRUE_FALSE);
            renderQuestions();
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel visibility = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveExam = new JButton("Save exam");
        if (isVisible) {
            setVisible.setSelected(true);
        }

        visibility.add(setVisible);
        bottomPanel.add(saveExam, BorderLayout.CENTER);
        bottomPanel.add(visibility, BorderLayout.EAST);

        saveExam.addActionListener(e -> {
            String changeFilePath = "/";
            try {
                if (!filePath.exists()) {
                    filePath.createNewFile();
                }

                for (Question question: questions) {
                    if (question.getQuestionType() != QuestionType.SHORT_ANSWER && question.getQuestionType() != QuestionType.LONG_ANSWER &&
                        question.getRightAnswer() == -1) {
                        JOptionPane.showMessageDialog(new JOptionPane(JOptionPane.ERROR_MESSAGE), "Question " +
                        question.getRealQuestionNumber() + " is lacking an answer");
                        return;
                    }
                }

                if (setVisible.isSelected()) {
                    Path path = Paths.get(filePath.getPath() + "/UnfinishedExams/" + selectedExam.getNameOfExam() + "/");
                    if (Files.exists(path)) {
                        try {
                            deleteFolder(path);
                            System.out.println("Folder deleted: " + path);
                        } catch (IOException e1) {
                            System.err.println("Failed to delete folder " + path);
                            e1.printStackTrace();
                        }
                    }

                    changeFilePath = "/FinishedExams/" + selectedExam.getNameOfExam() + "/";
                    selectedExam.setExamAsFinished(selectedExam);
                    selectedExam.setIsVisible(true);
                    try {
                        ExamList.addVisibleToDatabase(selectedExam);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Path path = Paths.get(filePath.getPath() + "/FinishedExams/" + selectedExam.getNameOfExam() + "/");
                    if (Files.exists(path)) {
                        try {
                            deleteFolder(path);
                            System.out.println("Folder deleted: " + path);
                        } catch (IOException e1) {
                            System.err.println("Failed to delete folder " + path);
                            e1.printStackTrace();
                        }
                    }
                    changeFilePath = "/UnfinishedExams/" + selectedExam.getNameOfExam() + "/";
                }

                selectedExam.setAllExamQuestions(questions);

                ExamSerializer.serializeQuestionsToJson(selectedExam, (filePath + changeFilePath));
                System.out.println("visible?: " + setVisible.isSelected());

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            examSheet.dispose();
        });

        examScrollPane.setPreferredSize(new Dimension(1600, 900));
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
                    createMultipleChoice(allQuestionsContainer, index, question);
                    question.setRealQuestionNumber(index + 1);
                    index++;
                    break;
                case SHORT_ANSWER:
                    createShortAnswer(allQuestionsContainer, index, question);
                    question.setRealQuestionNumber(index + 1);
                    index++;
                    break;
                case LONG_ANSWER:
                    createLongAnswer(allQuestionsContainer, index, question);
                    question.setRealQuestionNumber(index + 1);
                    index++;
                    break;
                case TRUE_FALSE:
                    createTrueFalse(allQuestionsContainer, index, question);
                    question.setRealQuestionNumber(index + 1);
                    index++;
                    break;
                default:
                    System.out.println("if you reach this you've done something very wrong");
                    break;
            }
        }
    }

    private void createLongAnswer(JPanel allQuestionsContainer, int index, Question thisQuestion) {
        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JLabel question = new JLabel("Q" + (index + 1) + ": ");
        JTextArea questionInput = new JTextArea();
        questionInput.setLineWrap(true);
        questionInput.setWrapStyleWord(true);
        questionInput.setText(thisQuestion.getQuestion());

        DelayedListener.addDelayedListener(questionInput, 250, () -> {
                    String questionInputtedByUser;
                    questionInputtedByUser = questionInput.getText();
                    questions.get(index).setQuestion(questionInputtedByUser);
                    System.out.println("Question " + (index + 1) + " is " + questions.get(index).getQuestion());
        });

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

    private void createShortAnswer(JPanel allQuestionsContainer, int index, Question thisQuestion) {
        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JLabel question = new JLabel("Q" + (index + 1) + ": ");
        JTextArea questionInput = new JTextArea();
        questionInput.setLineWrap(true);
        questionInput.setWrapStyleWord(true);
        questionInput.setText(thisQuestion.getQuestion());

        DelayedListener.addDelayedListener(questionInput, 250, () -> {
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

    private void createMultipleChoice(JPanel allQuestionsContainer, int index, Question thisQuestion) {
        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JPanel answerPanel = new JPanel(new GridBagLayout());
        JLabel question = new JLabel("Q" + (index + 1) + ": ");
        JTextArea questionInput = new JTextArea();

        questionInput.setLineWrap(true);
        questionInput.setWrapStyleWord(true);
        questionInput.setText(thisQuestion.getQuestion());

        DelayedListener.addDelayedListener(questionInput, 250, () -> {
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
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i=0; i<4; i++) {
            JLabel abcd = new JLabel(String.valueOf(currentCharacter) + ": ");
            JTextField answerInput = new JTextField();
            JRadioButton rightAnswer = new JRadioButton("Right answer?");
            JPanel inputPanel = new JPanel();
            GridBagConstraints multipleAnswerConstraints = new GridBagConstraints();
            multipleAnswerConstraints.gridx = GridBagConstraints.RELATIVE;
            multipleAnswerConstraints.gridy = 1;

            if (thisQuestion.getAnswers() != null && thisQuestion.getAnswers().size() == 4) {
                answerInput.setText(thisQuestion.getAnswers(i));
            }

            final int answerIndex = i;

            if (thisQuestion.getRightAnswer() != -1 && thisQuestion.getRightAnswer() == answerIndex) {
                rightAnswer.setSelected(true);
            }

            DelayedListener.addDelayedListener(answerInput, 250, () -> {
                String answer;
                answer = answerInput.getText();
                thisQuestion.setAnswers(answerIndex, answer);
                System.out.println("Question " + (index+1) + "'s answers are: " + thisQuestion.getAnswers());
            });

            ItemListener itemListener = e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JRadioButton selectedButton = (JRadioButton) e.getItem();
                    String selectedButtonText = answerInput.getText();
                    questions.get(index).setRightAnswer(answerIndex);
                    System.out.println("Right answer to the question is :" + thisQuestion.getRightAnswer());
                }
            };
            rightAnswer.addItemListener(itemListener);

            answerInput.setPreferredSize(new Dimension(multipleAnswerDimensions));
            inputPanel.add(abcd);
            inputPanel.add(answerInput);
            answerPanel.add(inputPanel);
            buttonGroup.add(rightAnswer);
            answerPanel.add(rightAnswer, multipleAnswerConstraints);
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

    private void createTrueFalse(JPanel allQuestionsContainer, int index, Question thisQuestion) {
        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JPanel answerPanel = new JPanel();
        JLabel question = new JLabel("Q" + (index + 1) + ": ");
        JTextArea questionInput = new JTextArea();
        ButtonGroup buttonGroup = new ButtonGroup();

        questionInput.setLineWrap(true);
        questionInput.setWrapStyleWord(true);
        questionInput.setText(thisQuestion.getQuestion());

        DelayedListener.addDelayedListener(questionInput, 250, () -> {
            String questionInputtedByUser;
            questionInputtedByUser = questionInput.getText();
            questions.get(index).setQuestion(questionInputtedByUser);
            System.out.println("Question " + (index+1) + " is " + questions.get(index).getQuestion());
        });

        JScrollPane questionTextPane = new JScrollPane(questionInput);
        questionTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        questionTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questionTextPane.setPreferredSize(new Dimension(questionTextDimensions));
        JButton removeQuestion = new JButton("Remove question");
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

                if (thisQuestion.getRightAnswer() != -1 && thisQuestion.getRightAnswer() == 0) {
                    trueButton.setSelected(true);
                }

                ItemListener itemListener = e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        JRadioButton selectedButton = (JRadioButton) e.getItem();
                        String selectedButtonText = selectedButton.getText();
                        thisQuestion.setRightAnswer(answerIndex);
                        System.out.println("Right answer to the question is :" + questions.get(index).getRightAnswer());
                    }
                };
                trueButton.addItemListener(itemListener);
            } else if (i==1) {
                JRadioButton falseButton = new JRadioButton("FALSE");
                buttonGroup.add(falseButton);
                answerPanel.add(falseButton);

                if (thisQuestion.getRightAnswer() != -1 && thisQuestion.getRightAnswer() == 0) {
                    falseButton.setSelected(true);
                }

                ItemListener itemListener = e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        JRadioButton selectedButton = (JRadioButton) e.getItem();
                        String selectedButtonText = selectedButton.getText();
                        thisQuestion.setRightAnswer(answerIndex);
                        System.out.println("Right answer to the question is :" + questions.get(index).getRightAnswer());
                    }
                };
                falseButton.addItemListener(itemListener);
            }
        }

        questionPanel.add(question);
        questionPanel.add(questionTextPane);
        questionPanel.add(removeQuestion);
        questionContainer.add(questionPanel, c);
        c.insets = new Insets(0, 0, 0, 110);
        questionContainer.add(answerPanel, c);



        allQuestionsContainer.add(questionContainer, gbc);
        examSheet.revalidate();

        removeQuestion(allQuestionsContainer, index, questionContainer, removeQuestion);
    }

    private void removeQuestion(JPanel allQuestionsContainer, int index, JPanel questionContainer, JButton removeQuestion) {
        removeQuestion.addActionListener(e1 -> {
            allQuestionsContainer.remove(questionContainer);
            questions.remove(index);
            renderQuestions();
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
            case TRUE_FALSE:
            default:
                newQuestion = new Question("", "", type);
                break;
        }

        questions.add(newQuestion);
    }
}
