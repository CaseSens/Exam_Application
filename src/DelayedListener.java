import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DelayedListener {

    public static void addDelayedListener(JTextField answer, int delay, Runnable action) {
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

    public static void addDelayedListener(JTextArea question, int delay, Runnable action) {
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
}
