package ru.telegramBot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyBot extends TelegramLongPollingBot {

    public void sendMsg(Message message, String text) throws IOException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public String getStatist() throws IOException {
        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        Document document = Jsoup.connect("https://coronavirus-monitor.ru/coronavirus-v-rossii").get();
        Element element = document.select("div.row.info-block-wrapper")
                .select("div.col-md-4.text-center")
                .select("div.info-block.disease").first();
        Element element2 = document.select("div.row.info-block-wrapper")
                .select("div.col-md-4.text-center")
                .select("div.info-block.healed").first();
        Element element3 = document.select("div.row.info-block-wrapper")
                .select("div.col-md-4.text-center")
                .select("div.info-block.deaths").first();

        String fin = "Текущая дата "+date +"\n"+element.text()+"\n" + element2.text()+"\n" + element3.text();
        return fin;
    }

    public String getStatsSamaraOblast() throws IOException {
        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        Document document = Jsoup.connect("https://coronavirus-monitor.ru/coronavirus-v-samarskoj-oblasti").get();
        Element element = document.select("div.page-container.js-city-page-container")
                .select("div.main-content-wrapper")
                .select("div.row.info-block-wrapper")
                .select("div.info-block.disease").first();
        Element element2 = document.select("div.page-container.js-city-page-container")
                .select("div.main-content-wrapper")
                .select("div.row.info-block-wrapper")
                .select("div.info-block.healed").first();
        Element element3 = document.select("div.page-container.js-city-page-container")
                .select("div.main-content-wrapper")
                .select("div.row.info-block-wrapper")
                .select("div.info-block.deaths").first();

        String fin = "Текущая дата "+date +"\n"+element.text()+"\n" + element2.text()+"\n" + element3.text();

        return fin;


    }
    public String getWorldStats() throws IOException {
        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        Document document = Jsoup.connect("https://coronavirus-spravka.ru").get();
        Element element = document.select("div.container.container-btm-map").select("div.row.total-stat-table").select("div.col-6.col-md-3.text-center").get(4);
        Element element2 = document.select("div.container.container-btm-map").select("div.row.total-stat-table").select("div.col-6.col-md-3.text-center").get(5);
        Element element3 = document.select("div.container.container-btm-map").select("div.row.total-stat-table").select("div.col-6.col-md-3.text-center").get(6);
        Element element4 = document.select("div.container.container-btm-map").select("div.row.total-stat-table").select("div.col-6.col-md-3.text-center").get(7);
        String fin = "Текущая дата " +date+"\n"+element.text()+"\n" + element2.text()+"\n"+element3.text()+"\n"+element4.text();
        return fin;
    }



    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if( message != null && message.hasText()){
            switch (message.getText()) {
                case "/start":
                    try {
                        sendMsg(message, "Нажмите соответствующую кнопку для отображения статистики!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/info":
                    try {
                        sendMsg(message, getStatist());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/infoSamara":
                    try {
                        sendMsg(message, getStatsSamaraOblast());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/infoWorld":
                    try {
                        sendMsg(message, getWorldStats());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }

    }
    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> list = new ArrayList<>();
        KeyboardRow keyboardFirsRow = new KeyboardRow();

        keyboardFirsRow.add("/info");
        keyboardFirsRow.add("/infoSamara");
        keyboardFirsRow.add("/infoWorld");

        list.add(keyboardFirsRow);

        replyKeyboardMarkup.setKeyboard(list);
    }

    @Override
    public String getBotUsername() {
        return "byKalmykov_CoronaBot";
    }

    @Override
    public String getBotToken() {
        return "тут находится токен вашего бота :)";
    }
}