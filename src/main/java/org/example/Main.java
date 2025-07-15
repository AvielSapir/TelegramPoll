package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
//        MyBot bot = new MyBot();
//        botConfig(bot);
//        sendPollExample(bot);

        // GUI
        JFrame window = new JFrame();
        windowConfig(window);

    }

    public static void windowConfig(JFrame window) {
        int WIDTH = 1080;
        int HEIGHT = 720;
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(1080, 720);
        window.setResizable(false);
        window.setLayout(null);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setTitle("Poll bot");
        window.getContentPane().setBackground(new Color(40, 40, 40));

        CreatePollPanel pollPanel = new CreatePollPanel(WIDTH, HEIGHT, window);
        window.add(pollPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setTitle("Poll bot");


//        AddQuestion addQuestion = new AddQuestion(window);
//        addQuestion.setLocationRelativeTo(window);
//        addQuestion.setVisible(true);

    }
    public static void botConfig(MyBot bot){
        try{
            TelegramBotsApi api =  new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
        }catch (TelegramApiException e){
            throw new RuntimeException();
        }
    }
    public static void sendPollExample(MyBot bot){
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