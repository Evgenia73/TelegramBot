package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;



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
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
//        if (update.hasMessage()) {
//            long userId = update.getMessage().getChatId();
//            String message = update.getMessage().getText();
//            messager.setChatId(userId);
//            String answer = actionsHandler.processUserMessage(message);
//            messager.setText(answer);
//
//            try {
//                execute(messager);
//            } catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
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

}
