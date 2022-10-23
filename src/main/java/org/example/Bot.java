package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot{

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
        if(update.hasMessage()){
            if(update.getMessage().hasText()){
                //sendMsg(update.getMessage().getChatId().toString(), update.getMessage().getText());
                if(update.getMessage().getText().equals("test")){
                    try {
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                else
                    sendMsg(update.getMessage().getChatId().toString(), update.getMessage().getText());
            }
        }else if(update.hasCallbackQuery()){
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

    public void sendMsg(String chatId, String message){
        messager.setChatId(chatId);
        String answer = actionsHandler.processUserMessage(message);
        messager.setText(answer);
        try {
            execute(messager);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Тык");
        inlineKeyboardButton1.setCallbackData("Button Тык has been pressed");
        inlineKeyboardButton2.setText("Тык2");
        inlineKeyboardButton2.setCallbackData("Button Тык2 has been pressed");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage result = new SendMessage();
        result.setText("Вопрос");
        result.setChatId(chatId);
        result.setReplyMarkup(inlineKeyboardMarkup);
        return result;
    }
}
