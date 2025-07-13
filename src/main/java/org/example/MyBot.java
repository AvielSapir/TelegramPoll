package org.example;

import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class MyBot extends TelegramLongPollingBot {

    private final UserManager userManager;

    public MyBot() {
        this.userManager = new UserManager();
    }

    @Override
    public String getBotUsername() {
        return "AvielOriPollBot";
    }

    public String getBotToken() {
        return "7772509307:AAG3dBf92f5C-5sfLKmFsCPhdI7sk0kbzbk";
    }


    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        if (Objects.equals(update.getMessage().getText().toLowerCase(), "/start") || Objects.equals(update.getMessage().getText(), "hi") || Objects.equals(update.getMessage().getText(), "היי")) {
            if (userManager.addUserId(chatId)) {
                sendMessage(chatId, "hello! welcome to poll bot!");
            }
        }
    }


    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        }catch (TelegramApiException e){
            throw new RuntimeException();
        }
    }

    public void sendPoll(Poll poll) {
        PollItem[] questions = poll.getQuestions();


//        if (this.userManager.getUsersId().size() < 3) {
//            System.out.println("not enough users! ");
//            return;
//        }
        if (questions.length == 0) {
            System.out.println("no poll questions");
            return;
        }

        for (long user: this.userManager.getUsersId()) {
            SendPoll sendPoll = new SendPoll();
            sendPoll.setChatId(user);
            for (PollItem question: questions) {
                sendPoll.setQuestion(question.getQuestion());
                sendPoll.setOptions(Arrays.stream(question.getAnswer()).toList());
                try {
                    execute(sendPoll);
                }catch (TelegramApiException e){
                    throw new RuntimeException();
                }
            }
        }
    };


}
