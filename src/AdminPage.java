import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPage extends SharedPage {
    public void createAdminPage() {
        createSharedPage();

        JButton createExam = new JButton("Create exam");
        leftMenu.add(createExam);

        createExam.addActionListener(e -> {
            Exam newExam = new Exam(true);
//            newExam.createExamOnceInitialized();
        });
    }

}
