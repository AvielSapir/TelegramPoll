package org.example;

public class PollItem {
    private String question;
    private String[] answer;
    private int currentAnswerIndex;


    public PollItem(String questions) {
        this.question = questions;
        this.answer = new String[3];
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswer() {
        return answer;
    }

    public void setAnswer(String[] answer) {
        this.answer = answer;
    }
}
