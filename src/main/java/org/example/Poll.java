package org.example;

public class Poll {
    private PollItem[] questions;
    private int currentSize;

    public Poll(int maxSize){
        this.questions = new PollItem[maxSize];
        this.currentSize = 0;
    }

    public boolean addQuestion(PollItem item){
        if(this.currentSize < this.questions.length){
            this.questions[this.currentSize] = item;
            this.currentSize++;
            return true;
        }
        else{
            return false;
        }
    }

    public PollItem[] getQuestions() {
        return questions;
    }

    public void setQuestions(PollItem[] questions) {
        this.questions = questions;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }
}
