import javax.swing.*;
import java.awt.*;

public class ExamSheet extends AdminPage{
    JFrame examSheet;
    JPanel centerPanelForExamQuestions;
    int questionIncrement=1;

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

        centerPanelForExamQuestions = new JPanel();
        centerPanelForExamQuestions.setLayout(new BoxLayout(centerPanelForExamQuestions, BoxLayout.Y_AXIS));
        centerPanelForExamQuestions.add(Box.createVerticalGlue());



        addMultipleChoice.addActionListener(e -> {
            JPanel exam = new JPanel();
            exam.setMaximumSize(new Dimension(200, 300)); //sets width to 200, but for some reason doesnt work on height
            exam.setPreferredSize(new Dimension(200, 250)); //sets height to 300, but for some reason doesnt work on width
            exam.setLayout(new BoxLayout(exam, BoxLayout.Y_AXIS));
            JPanel questionAnswer = new JPanel();
            JLabel question = new JLabel(questionIncrement + ". Enter question ");
            JTextField questionInput = new JTextField();
            JButton removeQuestion = new JButton("Remove Question");

//            exam.add(questionAnswer);
            questionAnswer.add(question);
            questionAnswer.add(questionInput);
            exam.add(questionAnswer);

            for (int i = 0; i < 4; i++) {
                JTextField answers = new JTextField();
                exam.add(answers);
            }
            centerPanelForExamQuestions.setLayout(new BoxLayout(centerPanelForExamQuestions, BoxLayout.Y_AXIS));
            centerPanelForExamQuestions.setAlignmentY(Component.TOP_ALIGNMENT);
            centerPanelForExamQuestions.add(Box.createVerticalGlue());
            centerPanelForExamQuestions.add(exam);
            centerPanelForExamQuestions.revalidate();
            questionIncrement++;
        });

        JScrollPane examScrollPane = new JScrollPane(centerPanelForExamQuestions);
        examScrollPane.setPreferredSize(new Dimension(1920, 1080));
        examScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        examSheet = new JFrame("Create your exam");
        examSheet.add(centerLeftPanel, BorderLayout.WEST);
        examSheet.getContentPane().add(examScrollPane, BorderLayout.CENTER);
        examSheet.setSize(1600, 900);
        examSheet.setLocationRelativeTo(null);
        examSheet.setVisible(true);
    }
}
