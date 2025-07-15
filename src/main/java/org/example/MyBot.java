package org.example;

import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.Array;
import java.util.*;


public class MyBot extends TelegramLongPollingBot {

    private final UserManager userManager;
    private Map<String, PollItem> pollIdMap;
    private Poll currentPoll;


    public MyBot() {
        this.userManager = new UserManager();
        this.pollIdMap = new HashMap<>();
        this.currentPoll = null;
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
        System.out.println("message received");
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (Objects.equals(update.getMessage().getText().toLowerCase(), "/start") || Objects.equals(update.getMessage().getText(), "hi") || Objects.equals(update.getMessage().getText(), "היי")) {
                Long chatId = update.getMessage().getChatId();
                if (userManager.addUserId(chatId)) {
                    sendMessage(chatId, "hello! welcome to poll bot!");
                    return;
                }
            }
        }
        if (update.hasPoll()){
            updatePollAnswer(update);
            return;
        }
    }

    private void updatePollAnswer(Update update) {
        String pollId = update.getPoll().getId();
        PollItem pollItem = pollIdMap.get(pollId);
        if (pollItem != null) {
            System.out.println("    |name: " + pollItem.getQuestion());
            System.out.println("    |number of voters: " + update.getPoll().getTotalVoterCount());

            for (int i = 0; i < update.getPoll().getOptions().size(); i++) {
                pollItem.addCount(i, update.getPoll().getOptions().get(i).getVoterCount());
            }
        }
        System.out.println("    | " + Arrays.toString(pollItem.getAnswerCount()));
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
        if (poll == null) {
            return;
        }
        this.currentPoll = poll;
        PollItem[] questions = currentPoll.getQuestions();
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
                if (question == null){
                    continue;
                }
                sendPoll.setQuestion(question.getQuestion());
                sendPoll.setOptions(Arrays.stream(question.getAnswer()).toList());
                try {
                    Message sentMessage = execute(sendPoll);
                    this.pollIdMap.put(sentMessage.getPoll().getId(), question);

                }catch (TelegramApiException e){
                    throw new RuntimeException();
                }
            }
        }
    };

    public boolean isAllAnswered(){
        for (PollItem pollItem: currentPoll.getQuestions()) {
            if(pollItem == null){
                continue;
            }
            if (pollItem.howManyAnswers() != this.userManager.getUsersId().size()) {
                return false;
            }
        }
        return true;
    }
}
