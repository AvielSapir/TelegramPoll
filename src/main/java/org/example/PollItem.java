package org.example;

import java.util.Arrays;

public class PollItem {
    private String question;
    private String[] answer;

    private int[] answerCount;
    private int currentAnswerIndex;


    public PollItem(String questions) {
        this.question = questions;
        this.answer = new String[3];
        this.answerCount = new int[3];
        this.currentAnswerIndex = 0;

    }

    public boolean addAnswer(String answer) {
        if (this.currentAnswerIndex < this.answer.length) {
            this.answer[this.currentAnswerIndex] = answer;
            this.currentAnswerIndex++;
            return true;
        } else {
            return false;
        }
    }

    public void clearAnswers(){
        for (int i = 0; i < this.answer.length; i++){
            this.answer[i] = null;
        }
        this.currentAnswerIndex = 0;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswer() {
        return answer;
    }

    public void addCount(int answerNumber, int value) {
        this.answerCount[answerNumber] += value;
    }

    public void setAnswer(String[] answer) {
        this.answer = answer;
    }

    public int[] getAnswerCount() {
        return answerCount;
    }

    public int howManyAnswers() {
        return Arrays.stream(this.answerCount).sum();
    }
}
