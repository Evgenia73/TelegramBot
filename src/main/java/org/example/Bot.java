package org.example;

import org.example.domain.Question;
import org.example.question.QuestionDAO;
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

import org.example.ActionsHandler;


public class Bot extends TelegramLongPollingBot {
    private SendMessage messager;
    private final ActionsHandler actionsHandler;
    private final QuestionDAO questionDAO = new QuestionDAO("test_all.txt");

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

    /**
     * Возвращает имя бота, указанное при регистрации
     */
    @Override
    public String getBotUsername() {
        return "BOT_NAME";
    }

    /**
     * Возвращает токен бота
     */
    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    /**
     * Создаем сообщение, которое отправит бот пользователю в ответ
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                sendMsg(update.getMessage().getChatId(), update.getMessage().getText());
            }
        } else if (update.hasCallbackQuery()) {
            try {
                SendMessage answer = new SendMessage();

                answer.setChatId(update.getCallbackQuery().getMessage().getChatId());
                answer.setText(update.getCallbackQuery().getData());

                execute(answer);

                if (questionDAO.getIsRandom()) {
                    questionDAO.setIsRandom(false);
                    return;
                }

                if (questionDAO.can()) {
                    SendMessage question = sendInlineKeyBoardMessage(update.getCallbackQuery().getMessage().getChatId(),
                            questionDAO.getNextQuestion());
                    execute(question);
                } else {
                    questionDAO.resetCurrentQuestion();
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Обработка команд для тестирования, если ни одной из них не было получено, то обработать
     * полученное сообщение с помощью processUserMessage
     * @param chatId
     * @param message
     */
    public void sendMsg(long chatId, String message) {
        messager.setChatId(chatId);

        if (message.equals("test_all")) {
            messager = sendInlineKeyBoardMessage(chatId, questionDAO.getNextQuestion());

            try {
                execute(messager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (message.equals("test_random")) {
            messager = sendInlineKeyBoardMessage(chatId, questionDAO.getRandomQuestion());

            try {
                execute(messager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String answer = actionsHandler.processUserMessage(message);
            messager.setText(answer);
            messager.setReplyMarkup(null);
            try {
                execute(messager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Создаем кнопки с вариантами выбора ответа (для тестирования пользователя)
     * реагируем на выбор варианта ответа
     * @param chatId - айди чата
     * @param listQuestion - список вопросов
     * @return
     */
    public SendMessage sendInlineKeyBoardMessage(long chatId, Question listQuestion) {
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
