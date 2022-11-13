package org.example;

import org.example.domain.Question;
import org.example.question.QuestionDAO;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
                sendMsg(update.getMessage().getChatId().toString(), update.getMessage().getText());
            }
        } else if (update.hasCallbackQuery()) {
            try {
                SendMessage answer = new SendMessage();

                answer.setChatId(update.getCallbackQuery().getMessage().getChatId());
                answer.setText(update.getCallbackQuery().getData());

                execute(answer);
                String userId = update.getCallbackQuery().getMessage().getChatId().toString();

                if(actionsHandler.getUsers().get(userId).getCurrentMessage().equals("test_all")){
                    sendMsg(userId, "test_all");
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(String chatId, String message) {
        messager.setChatId(chatId);
        String answer = actionsHandler.processUserMessage(message, chatId);
        Optional<Question> optionalQuestion = actionsHandler.processKeyBoard(chatId, message);
        if (optionalQuestion.isPresent())
            messager = sendInlineKeyBoardMessage(chatId, optionalQuestion.get());
        else {
            messager.setText(answer);
            messager.setReplyMarkup(null);
        }
        tryExecute(messager);
    }

    private void tryExecute(SendMessage messager) {
        try {
            execute(messager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SendMessage sendInlineKeyBoardMessage(String chatId, Question listQuestion) {
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
                inlineKeyboardButton.setCallbackData(variable + " - Неверно");

            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow);
        }

        inlineKeyboardMarkup.setKeyboard(rowList);

        result.setReplyMarkup(inlineKeyboardMarkup);
        result.setChatId(chatId);

        return result;
    }
}
