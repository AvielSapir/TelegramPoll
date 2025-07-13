package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try{
            TelegramBotsApi api =  new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(new MyBot());
        }catch (TelegramApiException e){
            throw new RuntimeException();
        }


        Poll poll = new Poll(2);
        PollItem p1 = new PollItem("color");
        p1.setAnswer(new String[]{"red", "blue", "green"});
        poll.addQuestion(p1);

        MyBot bot = new MyBot();
        bot.sendPoll(poll);
    }
}