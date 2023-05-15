import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SharedPage {
    JFrame currentCreateExamSheet;
    JFrame examSheet;
    JFrame mainFrame;
    JPanel leftPanel;
    JPanel leftMenu;

    JPanel exams;
    JLabel availableExams;
    int leftPanelWidth;
    JButton logoutButton;
    JButton setVisibleToStudentsButton;
    public void createSharedPage() {
        Exam examInfo = new Exam();
        mainFrame = new JFrame("Welcome");

        availableExams = new JLabel("Available Exams");

        leftMenu = new JPanel();
        BoxLayout leftMenuLayout = new BoxLayout(leftMenu, BoxLayout.Y_AXIS);
        leftMenu.setLayout(leftMenuLayout);
        leftPanel = new JPanel();
        BoxLayout centerPanelLayout = new BoxLayout(leftPanel, BoxLayout.X_AXIS);
        leftPanel.setLayout(centerPanelLayout);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(leftMenu, BorderLayout.WEST);
        leftPanel.setBackground(Color.cyan);


        createUnfinishedExamList(examInfo); //-------------------------------------------

        leftPanel.addComponentListener(new ComponentAdapter() { //only affects exams if leftPanel is present so good for student class
            @Override
            public void componentResized(ComponentEvent e) {
                leftPanelWidth = leftPanel.getWidth();
                exams.setBorder(BorderFactory.createEmptyBorder(0, (-leftPanelWidth + 15 /*+15 to more visually center it*/), 0, 0));
            }
        });

        JLabel mainPageTitle = new JLabel("Exams");
        mainPageTitle.setFont(mainPageTitle.getFont().deriveFont(24f));
        JPanel mainPageTitlePanel = new JPanel();
        mainPageTitlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPageTitlePanel.setBackground(Color.cyan);
        mainPageTitlePanel.add(mainPageTitle);

        // Create the account panel with GridBagLayout
        JPanel account = new JPanel(new GridBagLayout());

        // Add the logout button to the south-east of the account panel
        logoutButton = new JButton("Log out");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        account.add(logoutButton, gbc);

        JPanel topScreen = new JPanel(new BorderLayout());
        topScreen.add(mainPageTitlePanel, BorderLayout.NORTH);
        topScreen.add(account, BorderLayout.EAST);

        JScrollPane examsScrollPane = new JScrollPane(exams);
        examsScrollPane.setPreferredSize(new Dimension(1920, 1080));
        examsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainFrame.getContentPane().add(examsScrollPane, BorderLayout.CENTER);
        mainFrame.add(leftPanel, BorderLayout.WEST);
        mainFrame.add(topScreen, BorderLayout.NORTH);

        mainFrame.setSize(1920, 1080);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }

    public void createUnfinishedExamList(Exam examInfo) {
        exams = new JPanel();
        exams.setLayout(new BoxLayout(exams, BoxLayout.Y_AXIS));
        for (int i = 0; i < examInfo.getNumberOfExamsOnFile(); i++) {
            JButton button = new JButton("<html>"+"<h1>" + examInfo.getNameOfExam(i) + "</h1>"+"<br>" + "<br>" + "<center>" + examInfo.getSubjectOfExam(i) + "</center>" + "</html>");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 300)); //sets width to 200, but for some reason doesnt work on height
            button.setPreferredSize(new Dimension(200, 250)); //sets height to 300, but for some reason doesnt work on width
            exams.add(Box.createVerticalStrut(10));
            exams.add(button);

            button.addActionListener(e -> {
                createExamSheet();
            });
        }

        exams.add(Box.createVerticalGlue());
        Component[] components = exams.getComponents();
        for (Component component : components) {
            component.setBackground(Color.CYAN);
        }
    }


    public void createExamSheet() {
        if (currentCreateExamSheet != null) {
            currentCreateExamSheet.dispose();
        }

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

        JButton addShortAnswer = new JButton("Short Answer");

        addShortAnswer.setFont(font);
        addShortAnswer.setAlignmentX(Component.CENTER_ALIGNMENT);
        addShortAnswer.setMaximumSize(new Dimension(150, 50)); //sets height but for some reason doesnt work on width
        addShortAnswer.setPreferredSize(new Dimension(150, 50)); //sets width but for some reason doesnt work on height
        addMenu.add(addShortAnswer);

        JPanel centerLeftPanel = new JPanel();
        BoxLayout centerMenuLayout = new BoxLayout(centerLeftPanel, BoxLayout.X_AXIS);
        centerLeftPanel.setLayout(centerMenuLayout);
        centerLeftPanel.add(Box.createVerticalGlue());
        centerLeftPanel.add(addMenu, BorderLayout.WEST);
        centerLeftPanel.setBackground(Color.cyan);

        examSheet = new JFrame("Create your exam");
        examSheet.add(centerLeftPanel, BorderLayout.WEST);
        examSheet.setSize(1600, 900);
        examSheet.setLocationRelativeTo(null);
        examSheet.setVisible(true);
        currentCreateExamSheet = examSheet;
    }

}
