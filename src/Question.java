import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String question;
    private ArrayList<String> answers;
    private QuestionType questionType;
    private Boolean isVisible = false;

    public Question (String question, String answers, QuestionType questionType) {
        this.question = question;
        this.answers = new ArrayList<>();
        this.answers.add(answers);
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * returns a specific answer via its INDEX
     * @param index
     * @return
     */
    public String getAnswers(int index) {
        return answers.get(index);
    }

    /**
     * returns a list of all answers to that question
     * @return
     */
    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(int index, String answer) {
        if (index >= 0 && index < answers.size()) {
            answers.set(index, answer);
        } else {
            answers.add(answer);
        }
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}

enum QuestionType {
    MULTIPLE_CHOICE,
    SHORT_ANSWER,
    LONG_ANSWER
}