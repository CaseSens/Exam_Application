import javax.swing.*;
import java.awt.*;

public class ExamSheet extends AdminPage{
    JFrame examSheet;
    int questionIncrement=1;
    private static final GridBagConstraints gbc;
    static {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 80, 5);
    }
    private static final Dimension questionTextDimensions = new Dimension(300, 65);

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

        JScrollPane examScrollPane = new JScrollPane();
        examScrollPane.setPreferredSize(new Dimension(1600, 900));
        JPanel allQuestionsContainer = new JPanel();
        allQuestionsContainer.setLayout(new GridBagLayout());

        addMultipleChoice.addActionListener(e -> {
            createMultipleChoice(allQuestionsContainer);
        });

        addShortAnswer.addActionListener(e -> {
            createShortAnswer(allQuestionsContainer);
        });

        addLongAnswer.addActionListener(e -> {
            createLongAnswer(allQuestionsContainer);
        });

        examScrollPane.setViewportView(allQuestionsContainer);
        examScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        examSheet = new JFrame("Create your exam");
        examSheet.add(centerLeftPanel, BorderLayout.WEST);
        examSheet.getContentPane().add(examScrollPane, BorderLayout.CENTER);
        examSheet.setSize(1600, 900);
        examSheet.setLocationRelativeTo(null);
        examSheet.setVisible(true);
    }

    private void createLongAnswer(JPanel allQuestionsContainer) {
        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JLabel question = new JLabel("Q" + questionIncrement + ": ");
        JTextArea questionInput = new JTextArea();
        questionInput.setLineWrap(true);
        questionInput.setWrapStyleWord(true);
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
        questionIncrement++;
        System.out.println(questionIncrement);
        examSheet.revalidate();

        removeQuestion.addActionListener(e1 -> {
            allQuestionsContainer.remove(questionContainer);
            questionIncrement--;
            System.out.println(questionIncrement);
            refreshQuestionNumbers(allQuestionsContainer);
            allQuestionsContainer.revalidate();
            allQuestionsContainer.repaint();
            examSheet.revalidate();
        });
    }

    private void createShortAnswer(JPanel allQuestionsContainer) {
        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JLabel question = new JLabel("Q" + questionIncrement + ": ");
        JTextArea questionInput = new JTextArea();
        questionInput.setLineWrap(true);
        questionInput.setWrapStyleWord(true);
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
        questionIncrement++;
        System.out.println(questionIncrement);
        examSheet.revalidate();

        removeQuestion.addActionListener(e1 -> {
            allQuestionsContainer.remove(questionContainer);
            questionIncrement--;
            System.out.println(questionIncrement);
            refreshQuestionNumbers(allQuestionsContainer);
            allQuestionsContainer.revalidate();
            allQuestionsContainer.repaint();
            examSheet.revalidate();
        });
    }

    private void createMultipleChoice(JPanel allQuestionsContainer) {
        JPanel questionContainer = new JPanel(new GridBagLayout());
        JPanel questionPanel = new JPanel();
        JPanel answerPanel = new JPanel();
        JLabel question = new JLabel("Q" + questionIncrement + ": ");
        JTextArea questionInput = new JTextArea();
        questionInput.setLineWrap(true);
        questionInput.setWrapStyleWord(true);
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
            answerInput.setPreferredSize(new Dimension(multipleAnswerDimensions));
            answerPanel.add(abcd);
            answerPanel.add(answerInput);
            questionContainer.add(answerPanel, c);
            currentCharacter++;
        }

        allQuestionsContainer.add(questionContainer, gbc);
        questionIncrement++;
        System.out.println(questionIncrement);
        examSheet.revalidate();

        removeQuestion.addActionListener(e1 -> {
            allQuestionsContainer.remove(questionContainer);
            questionIncrement--;
            System.out.println(questionIncrement);
            refreshQuestionNumbers(allQuestionsContainer);
            allQuestionsContainer.revalidate();
            allQuestionsContainer.repaint();
            examSheet.revalidate();
        });
    }

    private void refreshQuestionNumbers(JPanel allQuestionsContainer) {
        Component[] components = allQuestionsContainer.getComponents(); //each questionContainer
        for (int i = 0; i < components.length; i++) { //loop through them
            if (components[i] instanceof JPanel) { //if component is JPanel
                JPanel panel = (JPanel) components[i]; //create a new JPanel from that component
                Component[] innerComponents = panel.getComponents(); //each components of questionContainer
                for (Component component : innerComponents) { //iterate through the inner components
                    if (component instanceof JPanel) {
                        JPanel newQuestionPanel = (JPanel) component;
                        Component[] questionComponents = newQuestionPanel.getComponents();
                        for (Component questionComponent : questionComponents) {
                            if (questionComponent instanceof JLabel) { //if its a JLabel
                                JLabel label = (JLabel) questionComponent; //create new Label from the component
                                String labelText = label.getText(); //get text from the Label
                                if (labelText.startsWith("Q")) { //only get Labels that start with Q
                                    int questionNumber = Integer.parseInt(labelText.substring(1, labelText.indexOf(":"))); //grab only the number
                                    if (questionNumber > questionIncrement - 1) {
                                        label.setText("Q" + (questionNumber - 1) + ": "); //set question - 1
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
