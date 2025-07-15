package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main {
    static int WIDTH = 1080;
    static int HEIGHT = 720;
    static Poll poll = new Poll();


    public static void main(String[] args) {
        //bot
        MyBot bot = new MyBot();
        botConfig(bot);

        // window
        JFrame window = new JFrame();
        windowConfig(window, bot);

        //create poll
        createPoll(window);
        window.repaint();

        //send poll
        bot.sendPoll(poll);

        // waiting...
        waiting(window, bot);

        System.out.println(calculateStatistic(poll.getQuestions()[0], 0));
        System.out.println(calculateStatistic(poll.getQuestions()[0], 1));
        System.out.println(calculateStatistic(poll.getQuestions()[0], 2));

        //show result
        showResult(window);

    }


    public static void createPoll(JFrame window) {
        CreatePollPanel pollPanel = new CreatePollPanel(WIDTH, HEIGHT, window);
        window.add(pollPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setTitle("Poll bot");
        while (!pollPanel.isSend()){
            sleepFor(100);
        }
        poll = pollPanel.getPoll();
        window.remove(pollPanel);
    }

    public static void waiting(JFrame window, MyBot bot) {
        int minutes = 5;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < 60000 * minutes) {
            if(bot.isAllAnswered()){
                break;
            }

            //todo add waiting screen

            sleepFor(100);
        }
    }

    public static void showResult(JFrame window) {
        //todo
    }

    public static int calculateStatistic(PollItem pollItem, int questionNumber) {
        int numOfUsers = pollItem.howManyAnswers();
        return (pollItem.getAnswerCount()[questionNumber] / numOfUsers) * 100;
    }

    public static void windowConfig(JFrame window, MyBot bot) {
        Poll poll = new Poll();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(1080, 720);
        window.setResizable(false);
        window.setLayout(null);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setTitle("Poll bot");
        window.getContentPane().setBackground(new Color(40, 40, 40));
        window.repaint();
    }

    public static void botConfig(MyBot bot){
        try{
            TelegramBotsApi api =  new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
        }catch (TelegramApiException e){
            throw new RuntimeException();
        }
    }

    public static void sleepFor(long millis){
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}