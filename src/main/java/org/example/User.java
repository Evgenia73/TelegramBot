package org.example;

import org.example.domain.Question;
import org.example.domain.UserContext;

public class User {
    private String telegramId;
    private String currentMessage;
    private Question currentQuestion;

    private UserContext context;

    public UserContext getContext() {
        return context;
    }

    public void setContext(UserContext context) {
        this.context = context;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    private QuestionType questionType;
    public User(){
    }
    public User(String telegramId)
    {
        this.telegramId = telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }
    public void setCurrentQuestion(Question currentQuestion) {this.currentQuestion = currentQuestion;}

    public String getTelegramId() {
        return telegramId;
    }
    public Question getCurrentQuestion(){return currentQuestion;}

    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage;
    }
    public String getCurrentMessage() {
        return currentMessage;
    }
}
