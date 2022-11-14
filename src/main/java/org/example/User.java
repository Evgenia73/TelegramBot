package org.example;

import org.example.domain.Question;

public class User {
    private String telegramId;
    private String currentMessage;
    private Question currentQuestion;


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
