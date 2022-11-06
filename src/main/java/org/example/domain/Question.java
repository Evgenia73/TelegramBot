package org.example.domain;

import java.util.List;

public class Question {
    private String questionPart;


    private List<String> responseOptions;


    private String answer;

    public String getQuestionPart() {
        return questionPart;
    }

    public List<String> getResponseOptions() {
        return responseOptions;
    }

    public void setResponseOptions(List<String> responseOptions) {
        this.responseOptions = responseOptions;
    }

    public void setQuestionPart(String questionPart) {
        this.questionPart = questionPart;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Question(String questionPart, List<String> responseOptions, String answer) {
        this.questionPart = questionPart;
        this.responseOptions = responseOptions;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionPart='" + questionPart + '\'' +
                ", responseOptions=" + responseOptions +
                ", answer='" + answer + '\'' +
                '}';
    }
}
