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

        //send poll
        bot.sendPoll(poll);

        // waiting...
        waiting(window, bot);


    }


    public static void createPoll(JFrame window) {
        CreatePollPanel pollPanel = new CreatePollPanel(WIDTH, HEIGHT, window);
        window.add(pollPanel);
        window.setVisible(true);
        while (!pollPanel.isSend()){
            sleepFor(100);
        }
        poll = pollPanel.getPoll();
        window.remove(pollPanel);
        window.repaint();
    }

    public static void waiting(JFrame window, MyBot bot) {
        WaitPanel waitPanel = new WaitPanel(WIDTH, HEIGHT);

        JLayeredPane layeredPane = window.getLayeredPane();
        long startTime = System.currentTimeMillis();
        SwingUtilities.invokeLater(() -> {
            waitPanel.setBounds(0, 0, WIDTH, HEIGHT);
            layeredPane.add(waitPanel, JLayeredPane.PALETTE_LAYER);
            layeredPane.revalidate();
            layeredPane.repaint();
        });

        new Thread(() -> {
            int minutes = 5;

            while (System.currentTimeMillis() - startTime < 60000 * minutes && !bot.isAllAnswered()) {
                sleepFor(100);
                long mill = 5 *60000 -(System.currentTimeMillis() - startTime);
                int currentMinutes = (int)(mill / 1000) / 60;
                int currentSeconds = (int)(mill / 1000) % 60;
                waitPanel.updateTime(currentMinutes, currentSeconds);
            }

            SwingUtilities.invokeLater(() -> {
                layeredPane.remove(waitPanel);
                showResult(window);
                layeredPane.revalidate();
                layeredPane.repaint();
            });

        }).start();
    }
    public static void showResult(JFrame window) {
        PollResultsPanel p = new PollResultsPanel(poll, WIDTH, HEIGHT);
        window.getContentPane().removeAll();
        window.add(p);
        SwingUtilities.invokeLater(() -> {
            window.revalidate();
            window.repaint();
        });
        //todo
    }

    public static int calculateStatistic(PollItem pollItem, int questionNumber) {
        float numOfUsers = pollItem.howManyAnswers();
        System.out.println("Number of users: " + numOfUsers + "; sum: " + pollItem.getAnswerCount()[questionNumber] + ";");
        return (int) ((pollItem.getAnswerCount()[questionNumber]/numOfUsers) * 100);
    }

    public static void windowConfig(JFrame window, MyBot bot) {
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