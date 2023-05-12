import javax.swing.*;
import java.awt.*;

public class SharedPage {

    static JPanel centerPanel;
    static JPanel leftMenu;
    static JPanel exams;
    static JLabel availableExams;
    public static void createSharedPage() {
        Exam examInfo = new Exam();
        JFrame mainFrame = new JFrame();

        availableExams = new JLabel("Available Exams");

        leftMenu = new JPanel();
        BoxLayout leftMenuLayout = new BoxLayout(leftMenu, BoxLayout.Y_AXIS);
        leftMenu.setLayout(leftMenuLayout);
        centerPanel = new JPanel();
        BoxLayout centerPanelLayout = new BoxLayout(centerPanel, BoxLayout.X_AXIS);
        centerPanel.setLayout(centerPanelLayout);
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(leftMenu, BorderLayout.WEST);

        JLabel mainPageTitle = new JLabel("Exams");
        mainPageTitle.setFont(mainPageTitle.getFont().deriveFont(24f));
        JPanel mainPageTitlePanel = new JPanel();
        mainPageTitlePanel.add(mainPageTitle);

        exams = new JPanel();
        exams.setPreferredSize(new Dimension(100, 100));
        exams.setLayout(new GridLayout(examInfo.getNumberOfExamsOnFile(), 1));
        for (int i = 0; i < examInfo.getNumberOfExamsOnFile(); i++) {
            JButton button = new JButton("Exam " + i);
            exams.add(button);
        }
        Component[] components = exams.getComponents();
        for (Component component : components) {
            component.setBackground(Color.CYAN);
        }


        mainFrame.add(exams, BorderLayout.CENTER);
        mainFrame.add(centerPanel, BorderLayout.WEST);
        mainFrame.add(mainPageTitlePanel, BorderLayout.NORTH);

        mainFrame.setSize(1920, 1080);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }


}
