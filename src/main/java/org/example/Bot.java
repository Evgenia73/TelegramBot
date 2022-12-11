package org.example;

import org.example.domain.AnswerMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {
    private SendMessage messager;
    private final ActionsHandler actionsHandler;
    public SendMessage getMessager() {
        return messager;
    }

    public void setMessager(SendMessage messager) {
        this.messager = messager;
    }

    public Bot() {
        messager = new SendMessage();
        actionsHandler = new ActionsHandler();
    }

    @Override
    public String getBotUsername() {
        return "BOT_NAME";
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message;
        Long chatId;
        if (update.hasMessage()) {
            message = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
        } else  {
            message = update.getCallbackQuery().getMessage().getText();
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
        sendMsg(chatId.toString(), message);
    }


    public void sendMsg(String chatId, String message) {
        messager.setChatId(chatId);
        AnswerMessage answer = actionsHandler.processUserMessage(message, chatId);
        messager.setText(answer.getStringResponse());
        if (answer.hasResponseList())
            sendReplyKeyBoardMessage(messager, answer.getResponseList());
        tryExecute(messager);
    }

    private void tryExecute(SendMessage messager) {
        try {
            execute(messager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendReplyKeyBoardMessage(SendMessage sendMessage, List<String> variables) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        for (String variable : variables) {
            KeyboardRow row = new KeyboardRow();
            row.add(variable);
            keyboardRows.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
}
