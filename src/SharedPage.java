import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SharedPage extends Exam {
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


        mainFrame.add(leftPanel, BorderLayout.WEST);
        mainFrame.add(topScreen, BorderLayout.NORTH);

        mainFrame.setSize(1920, 1080);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }

}
