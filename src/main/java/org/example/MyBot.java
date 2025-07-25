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
        return BotConfig.getUserName();
    }

    public String getBotToken() {
        return BotConfig.getToken();
    }


    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("message received");
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (Objects.equals(update.getMessage().getText().toLowerCase(), "/start") || Objects.equals(update.getMessage().getText().toLowerCase(), "hi") || Objects.equals(update.getMessage().getText(), "היי")) {
                Long chatId = update.getMessage().getChatId();
                if (userManager.addUserId(chatId)) {
                    String name = update.getMessage().getFrom().getUserName();
                    int size = this.userManager.getUsersId().size();
                    for (Long id:this.userManager.getUsersId()) {
                        if (!Objects.equals(id, chatId)) {
                            sendMessage(id, name + " Join the community! The community size is now - " + size);
                        }else {
                            sendMessage(chatId, "hello! welcome to poll bot!");
                        }
                    }
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

    public boolean sendPoll(Poll poll) {
        if (poll == null) {
            return false;
        }
        this.currentPoll = poll;
        PollItem[] questions = currentPoll.getQuestions();
        if (this.userManager.getUsersId().size() < 3) {
            System.out.println("not enough users! ");
            return false;
        }
        if (questions.length == 0) {
            System.out.println("no poll questions");
            return false;
        }

        for (long user: this.userManager.getUsersId()) {
            SendPoll sendPoll = new SendPoll();
            sendPoll.setChatId(user);
            for (PollItem question: questions) {
                if (question == null){
                    continue;
                }
                sendPoll.setQuestion(question.getQuestion());

                List<String> validOptions = new ArrayList<>();

                String[] allAnswers = question.getAnswer();
                for (String answer : allAnswers) {
                    if (answer != null && !answer.trim().isEmpty()) {
                        validOptions.add(answer);
                    }
                }

                if (validOptions.size() < 2) {
                    System.err.println("Error on my bot");
                    return false;
                }
                sendPoll.setOptions(validOptions);
                try {
                    Message sentMessage = execute(sendPoll);
                    this.pollIdMap.put(sentMessage.getPoll().getId(), question);

                }catch (TelegramApiException e){
                    throw new RuntimeException();
                }
            }
        }
        return true;
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

    public void clearPollAnswers() {
        this.pollIdMap.clear();
    }
}
