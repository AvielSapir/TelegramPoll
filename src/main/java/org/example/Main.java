package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        MyBot bot = new MyBot();
        try{
            TelegramBotsApi api =  new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
        }catch (TelegramApiException e){
            throw new RuntimeException();
        }

        Poll poll = new Poll(2);
        PollItem p1 = new PollItem("color");
        PollItem p2 = new PollItem("number");
        p1.setAnswer(new String[]{"red", "blue", "green"});
        p2.setAnswer(new String[]{"1", "2", "3"});
        poll.addQuestion(p1);
        poll.addQuestion(p2);

        bot.sendPoll(poll);
    }
}