package org.example;

public class Poll {
    private PollItem[] questions;
    private int currentSize;

    public Poll(int size){
        this.questions = new PollItem[size];
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

   public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Poll contains ").append(this.currentSize).append("question \n \n");

       for (int i = 0; i < this.currentSize; i++) {
           sb.append("Question: ").append(i + 1).append("\n");
           sb.append(this.questions[i].toString()).append("\n");
       }
       return sb.toString();

   }
}
