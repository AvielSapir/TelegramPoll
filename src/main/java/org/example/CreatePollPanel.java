package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePollPanel extends JPanel {
    private Poll poll;
    private boolean send = false;
    private int WIDTH;
    private int HEIGHT;

    public CreatePollPanel(int width, int height, JFrame window) {
        this.poll = new Poll();
        this.WIDTH = width;
        this.HEIGHT = height;
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(1, 2));
        setBackground(Color.WHITE);
        setBackground(new Color(61, 61, 61));

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.setBackground(new Color(27, 27, 27));
        panel2.setBackground(new Color(37, 37, 37));

        this.add(panel1);
        this.add(panel2);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        JButton addQButton = addQBT(window, panel2);
        JButton sendButton = sendBT(window, panel2);
        JButton gptButton = gptBT(window, panel2);

        panel1.add(addQButton);
        panel1.add(sendButton);
        panel1.add(gptButton);

        addQButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gptButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel1.add(addQButton);
        panel1.add(Box.createVerticalStrut(20));
        panel1.add(sendButton);
        panel1.add(Box.createVerticalStrut(20));
        panel1.add(gptButton);

        panel1.add(Box.createVerticalGlue());


    }

    public void addCard(JPanel panel,String question, String option1, String option2, String option3) {
        JLabel questionLabel = newLabel(question, 25);
        JLabel optionLabel1 = newLabel(option1, 18);
        JLabel optionLabel2 = newLabel(option2, 18);
        JLabel optionLabel3 = newLabel(option3, 18);
        JLabel space = newLabel("", 10);


        panel.add(questionLabel);
        panel.add(optionLabel1);
        panel.add(optionLabel2);
        panel.add(optionLabel3);
        panel.add(space);
        panel.revalidate();
        panel.repaint();
    }

    private JLabel newLabel(String text, int size) {
        JLabel jLabel = new JLabel(text);
        jLabel.setForeground(new Color(208, 208, 208));
        jLabel.setFont(new Font("Arial", Font.BOLD, size));
        Border paddingBorder = BorderFactory.createEmptyBorder(5+size/3, 20, 0, 0);
        jLabel.setBorder(paddingBorder);
        jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return jLabel;
    }

    @Override
    public void repaint(Rectangle r) {
        super.repaint(r);
    }

    private JButton addQBT(JFrame frame, JPanel panel) {
        JButton addQBT = new JButton("Add question");
        addQBT.setBackground(new Color(37, 37, 37));
        addQBT.setForeground(new Color(208, 208, 208));
        addQBT.setFont(new Font("Arial", Font.BOLD, 15));
        addQBT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addQBT.setBorderPainted(false);
        addQBT.setPreferredSize(new Dimension(400, 70));

        addQBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddQuestion addQuestion = new AddQuestion(frame);
                addQuestion.setLocationRelativeTo(frame);
                addQuestion.setVisible(true);
                if (addQuestion.isFull() && poll.getCurrentSize() <= 3) {
                    addCard(panel ,addQuestion.getQuestionValue(), addQuestion.getAnswerValue1(), addQuestion.getAnswerValue2(), addQuestion.getAnswerValue3());
                    addPollItem(addQuestion.getQuestionValue(), addQuestion.getAnswerValue1(), addQuestion.getAnswerValue2(), addQuestion.getAnswerValue3());
                }
            }
        });
        return addQBT;
    }

    //todo
    private JButton sendBT(JFrame frame, JPanel panel) {
        JButton addQBT = new JButton("Send");
        addQBT.setBackground(new Color(37, 37, 37));
        addQBT.setForeground(new Color(208, 208, 208));
        addQBT.setFont(new Font("Arial", Font.BOLD, 15));
        addQBT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addQBT.setBorderPainted(false);
        addQBT.setPreferredSize(new Dimension(400, 70));
        addQBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (poll.getCurrentSize() >= 1) {
                    send = true;
                }else {
                    JOptionPane.showMessageDialog(null, "You need to send at least one question");
                }
            }
        });
        return addQBT;
    }
    private JButton gptBT(JFrame frame, JPanel panel) {
        JButton addQBT = new JButton("Create with GPT");
        addQBT.setBackground(new Color(37, 37, 37));
        addQBT.setForeground(new Color(208, 208, 208));
        addQBT.setFont(new Font("Arial", Font.BOLD, 15));
        addQBT.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        addQBT.setBorderPainted(false);
        addQBT.setPreferredSize(new Dimension(400, 70));

        addQBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo
            }
        });
        return addQBT;
    }

    private void addPollItem(String question, String option1, String option2, String option3) {
        PollItem pollItem = new PollItem(question);
        pollItem.addAnswer(option1);
        pollItem.addAnswer(option2);
        pollItem.addAnswer(option3);
        this.poll.addQuestion(pollItem);
    }

    public Poll getPoll() {
        return poll;
    }

    public boolean isSend() {
        return send;
    }
}
