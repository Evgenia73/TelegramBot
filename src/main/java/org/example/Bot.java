package org.example;

import org.example.domain.Question;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.ActionsHandler.questions;
import static org.example.ActionsHandler.readFile;


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
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                sendMsg(update.getMessage().getChatId(), update.getMessage().getText());
            }
//                if(update.getMessage().getText().equals("test")){
//                    try {
//                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else
//                    sendMsg(update.getMessage().getChatId(), update.getMessage().getText());
//            }
        } else if (update.hasCallbackQuery()) {
            try {
                SendMessage mess = new SendMessage();
                mess.setText(update.getCallbackQuery().getData());
                mess.setChatId(update.getCallbackQuery().getMessage().getChatId());
                execute(mess);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(long chatId, String message) {
        messager.setChatId(chatId);
        if (message.equals("test")) {
            List<SendMessage> test = sendInlineKeyBoardMessage(chatId);
            for (SendMessage sendMessage : test) {
                messager = sendMessage;
                try {
                    execute(messager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            String answer = actionsHandler.processUserMessage(message);
            messager.setText(answer);
            try {
                execute(messager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<SendMessage> sendInlineKeyBoardMessage(long chatId) {
        List<SendMessage> res = new ArrayList<>();

        List<Question> listQuestions = new ArrayList<>(questions(readFile("test_all.txt")));

        for (Question listQuestion : listQuestions) {
            SendMessage result = new SendMessage();
            result.setText(listQuestion.getQuestionPart());
            List<String> variables = listQuestion.getResponseOptions();
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            for (String variable : variables) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(variable);
                if (variable.equals(listQuestion.getAnswer())) {
                    inlineKeyboardButton.setCallbackData(listQuestion.getAnswer() + " - Верно!");
                } else
                    inlineKeyboardButton.setCallbackData("no");
                List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
                keyboardButtonsRow.add(inlineKeyboardButton);
                rowList.add(keyboardButtonsRow);
            }
            inlineKeyboardMarkup.setKeyboard(rowList);
            result.setReplyMarkup(inlineKeyboardMarkup);
            result.setChatId(chatId);
            res.add(result);
        }
        return res;


    }
}
