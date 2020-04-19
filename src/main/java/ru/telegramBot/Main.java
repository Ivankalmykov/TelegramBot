package ru.telegramBot;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;


import java.io.IOException;




public class Main{
    public static void main(String[] args) throws IOException {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try{
            botsApi.registerBot(new MyBot());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}