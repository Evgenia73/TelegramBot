package org.example.domain;

import org.example.QuestionType;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String questionPart;
    private List<String> responseOptions;
    private String answer;
    private QuestionType questionType;

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

    public Question(){
        this.questionPart = null;
        this.answer = null;
        this.responseOptions = new ArrayList<>();
    }
    public Question(String questionPart, List<String> responseOptions, String answer) {
        this.questionPart = questionPart;
        this.responseOptions = responseOptions;
        this.answer = answer;
//        this.questionType = questionType;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public boolean checkAnswer(String answer) {
        return answer.equals(this.answer);
    }
}
