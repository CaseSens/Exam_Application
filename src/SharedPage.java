import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.Flow;

public class SharedPage {
    static JFrame mainFrame;
    static JPanel leftPanel;
    static JPanel leftMenu;
    static JPanel exams;
    static JLabel availableExams;
    static int leftPanelWidth;
    public static void createSharedPage() {
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

        exams = new JPanel();
        exams.setLayout(new BoxLayout(exams, BoxLayout.Y_AXIS));
        for (int i = 0; i < examInfo.getNumberOfExamsOnFile(); i++) {
            JButton button = new JButton("<html>"+"<h1>" + examInfo.getNameOfExam(i) + "</h1>"+"<br>" + "<br>" + "<center>" + examInfo.getSubjectOfExam(i) + "</center>" + "</html>");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 300)); //sets width to 200, but for some reason doesnt work on height
            button.setPreferredSize(new Dimension(200, 250)); //sets height to 300, but for some reason doesnt work on width
            exams.add(Box.createVerticalStrut(10));
            exams.add(button);
        }

        exams.add(Box.createVerticalGlue());
        Component[] components = exams.getComponents();
        for (Component component : components) {
            component.setBackground(Color.CYAN);
        }

        JScrollPane examsScrollPane = new JScrollPane(exams);
        examsScrollPane.setPreferredSize(new Dimension(1920, 1080));
        examsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainFrame.getContentPane().add(examsScrollPane, BorderLayout.CENTER);
//        mainFrame.add(exams, BorderLayout.CENTER);
        mainFrame.add(leftPanel, BorderLayout.WEST);
        mainFrame.add(mainPageTitlePanel, BorderLayout.NORTH);

        mainFrame.setSize(1920, 1080);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }

    public void closeMainFrame() {
        mainFrame.setVisible(false);
    }


}
