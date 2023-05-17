import java.util.ArrayList;

public class Question {
    private String question;
    private ArrayList<String> answers;
    private QuestionType questionType;

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