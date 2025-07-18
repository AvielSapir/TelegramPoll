package org.example;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class PollResultsPanel extends JPanel {

    public PollResultsPanel(Poll poll, int width, int height) {
        this.setBounds(0, 0, width, height);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(31, 31, 35));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Poll Results", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(titleLabel);
        this.add(Box.createRigidArea(new Dimension(0, 20)));

        DecimalFormat df = new DecimalFormat("0.0%");
        for (int i = 0; i < poll.getCurrentSize(); i++) {
            PollItem questionItem = poll.getQuestions()[i];
            if (questionItem == null) {
                continue;
            }

            JLabel questionLabel = new JLabel(questionItem.getQuestion(), SwingConstants.LEFT);
            questionLabel.setFont(new Font("Arial", Font.BOLD, 25));
            questionLabel.setForeground(new Color(150, 200, 255));
            questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.add(questionLabel);
            this.add(Box.createRigidArea(new Dimension(0, 20)));

            int totalAnswersForQuestion = questionItem.howManyAnswers();

            String[] answers = questionItem.getAnswer();
            int[] answerCounts = questionItem.getAnswerCount();

            for (int j = 0; j < answers.length; j++) {
                if (answers[j] == null) {
                    continue;
                }

                double percentage = 0.0;
                if (totalAnswersForQuestion > 0) {
                    percentage = (double) answerCounts[j] / totalAnswersForQuestion;
                }

                JPanel answerPanel = new JPanel();
                answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.X_AXIS));
                answerPanel.setBackground(new Color(31, 31, 35));
                answerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                answerPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
                JLabel answerTextLabel = new JLabel("   • " + answers[j], SwingConstants.LEFT);
                answerTextLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                answerTextLabel.setForeground(Color.LIGHT_GRAY);

                JLabel percentageLabel = new JLabel(df.format(percentage), SwingConstants.RIGHT);
                percentageLabel.setFont(new Font("Arial", Font.BOLD, 18));
                percentageLabel.setForeground(new Color(100, 255, 100));

                answerPanel.add(answerTextLabel);
                answerPanel.add(Box.createHorizontalGlue());
                answerPanel.add(percentageLabel);



                this.add(answerPanel);
                this.add(Box.createRigidArea(new Dimension(0, 5)));
            }
            this.add(Box.createRigidArea(new Dimension(0, 20)));
        }
        this.add(Box.createVerticalGlue());

        this.setVisible(true);
    }
}