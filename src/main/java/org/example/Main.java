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

    private static volatile boolean isContinueClicked = false;

    public static void main(String[] args) {
        //bot
        MyBot bot = new MyBot();
        botConfig(bot);

        // window
        JFrame window = new JFrame();
        windowConfig(window, bot);

        while(true) {
            //create poll
            createPoll(window);

            //send poll
            bot.sendPoll(poll);

            // waiting...
            Thread waitingThread = waiting(window, bot);
            try {
                waitingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            bot.clearPollAnswers();
            poll.clearPoll();
        }


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

    public static Thread waiting(JFrame window, MyBot bot) {
        WaitPanel waitPanel = new WaitPanel(WIDTH, HEIGHT);

        JLayeredPane layeredPane = window.getLayeredPane();
        long startTime = System.currentTimeMillis();
        SwingUtilities.invokeLater(() -> {
            waitPanel.setBounds(0, 0, WIDTH, HEIGHT);
            layeredPane.add(waitPanel, JLayeredPane.PALETTE_LAYER);
            layeredPane.revalidate();
            layeredPane.repaint();
        });

        Thread waitingThread = new Thread(() -> {
            int minutes = 5;

            while (System.currentTimeMillis() - startTime < 60000 * minutes && !bot.isAllAnswered()) {
                sleepFor(100);
                long mill = 5 *60000 -(System.currentTimeMillis() - startTime);
                int currentMinutes = (int)(mill / 1000) / 60;
                int currentSeconds = (int)(mill / 1000) % 60;
                waitPanel.updateTime(currentMinutes, currentSeconds);
            }

            try {
                SwingUtilities.invokeAndWait(() -> {
                    layeredPane.remove(waitPanel);
                    showResult(window);
                    layeredPane.revalidate();
                    layeredPane.repaint();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            isContinueClicked = false;
            while (!isContinueClicked) {
                sleepFor(100);
            }

            SwingUtilities.invokeLater(() -> {
                window.getContentPane().removeAll();
                window.revalidate();
                window.repaint();
            });
        });

        waitingThread.start();
        return waitingThread;
    }
    public static void showResult(JFrame window) {
        PollResultsPanel p = new PollResultsPanel(poll, WIDTH, HEIGHT);
        window.getContentPane().removeAll();
        window.add(p);

        JButton continueButton = new JButton("New Poll");
        continueButton.setBackground(new Color(46, 46, 53));
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        continueButton.setBorderPainted(false);
        continueButton.setPreferredSize(new Dimension(200, 50));
        continueButton.setMinimumSize(new Dimension(200, 50));
        continueButton.setMaximumSize(new Dimension(200, 50));
        continueButton.setForeground(new Color(208, 208, 208));
        continueButton.setFont(new Font("Arial", Font.BOLD, 15));
        continueButton.addActionListener(e -> {
            isContinueClicked = true;
        });
        p.add(continueButton);
        p.add(Box.createVerticalGlue());

        System.out.println("Poll Results");

        SwingUtilities.invokeLater(() -> {
            window.revalidate();
            window.repaint();
        });
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