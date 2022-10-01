package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new Bot());

//        Scanner console = new Scanner(System.in);
//        while(true) {
//            String userMessage = console.nextLine();
//            System.out.println(ActionsHandler.processUserMessage(userMessage));
//            userMessage="";
//        }
    }

}