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
        JPanel panel3 = new JPanel();
        panel1.setBackground(new Color(27, 27, 27));
        panel2.setBackground(new Color(37, 37, 37));

        this.add(panel1);
        this.add(panel2);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        JButton addQButton = addQBT(window, panel2);
        JButton sendButton = sendBT(window, panel2);
        JButton gptButton = gptBT(window, panel2);


        addQButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gptButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Create Poll", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        panel1.add(titleLabel);
        panel2.add(Box.createVerticalGlue());

        //panel1.add(Box.createVerticalStrut(100));
        //panel1.add(Box.createVerticalGlue());
        panel1.add(addQButton);
        //panel1.add(Box.createVerticalStrut(50));
        panel1.add(Box.createVerticalGlue());
        panel1.add(sendButton);
        //panel1.add(Box.createVerticalStrut(50));
        panel1.add(Box.createVerticalGlue());
        panel1.add(gptButton);
        panel1.add(Box.createVerticalGlue());
        //panel1.add(Box.createVerticalGlue());


    }

    public void addCard(JPanel panel, String question, String[] options) {
        JLabel questionLabel = newLabel(question, 25);
        panel.add(questionLabel);

        for (String option : options) {
            if (option != null && !option.isEmpty()) {
                panel.add(newLabel(option, 18));
            }
        }
        JLabel space = newLabel("", 10);
        panel.add(space);
        panel.add(Box.createVerticalGlue());
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
        addQBT.setPreferredSize(new Dimension(300, 70));
        addQBT.setMinimumSize(new Dimension(300, 70));
        addQBT.setMaximumSize(new Dimension(300, 70));

        addQBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( poll.getCurrentSize() < 3) {
                    AddQuestion addQuestion = new AddQuestion(frame);
                    addQuestion.setLocationRelativeTo(frame);
                    addQuestion.setVisible(true);
                    if (addQuestion.isFull()) {
                        String[] answers = addQuestion.getAnswerValue();
                        addCard(panel, addQuestion.getQuestionValue(), answers);
                        addPollItem(addQuestion.getQuestionValue(), answers);
                    }
// ...
                }else {
                    JOptionPane.showMessageDialog(null, "Three questions is enough, don't you think?", "Error", JOptionPane.ERROR_MESSAGE);
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
        addQBT.setPreferredSize(new Dimension(300, 70));
        addQBT.setMinimumSize(new Dimension(300, 70));
        addQBT.setMaximumSize(new Dimension(300, 70));
        addQBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (poll.getCurrentSize() >= 1) {
                    String input = JOptionPane.showInputDialog(frame, "Enter a delay time in seconds before sending the survey: ", "Delay time", JOptionPane.QUESTION_MESSAGE);

                    if (input != null && !input.isEmpty()) {
                        try {
                            int delaySeconds = Integer.parseInt(input);
                            if (delaySeconds > 0) {
                                Thread delayThread = new Thread(() -> {
                                    try {
                                        JOptionPane.showMessageDialog(frame, "The survey will be sent in " + delaySeconds + " Seconds. ", " The survey was sent. ", JOptionPane.INFORMATION_MESSAGE);
                                        Thread.sleep(delaySeconds * 1000);
                                        send = true;
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                });
                                delayThread.start();
                            } else {
                                send = true;
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Please enter a valid number. ", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "It's a bit strange to send an empty poll. ", "Error", JOptionPane.ERROR_MESSAGE);
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
        addQBT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addQBT.setBorderPainted(false);
        addQBT.setPreferredSize(new Dimension(300, 70));
        addQBT.setMinimumSize(new Dimension(300, 70));
        addQBT.setMaximumSize(new Dimension(300, 70));

        addQBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddSubject subject = new AddSubject(frame);
                subject.setLocationRelativeTo(frame);
                subject.setVisible(true);
                if (subject.isFull()){
                    GptApi gptApi = new GptApi();
                    poll = gptApi.createWithGpt(subject.getSubject(), subject.getNumber());
                    panel.removeAll();
                    for (PollItem p : poll.getQuestions()) {
                        if (p == null){
                            continue;
                        }
                        String[] answers = p.getAnswer();
                        addCard(panel, p.getQuestion(), answers);
                        repaint();
                    }
// ...
                }
            }
        });
        return addQBT;
    }

    private void addPollItem(String question, String[] options) {
        PollItem pollItem = new PollItem(question);
        for (String option : options) {
            if (option != null) {
                pollItem.addAnswer(option);
            }
        }
        this.poll.addQuestion(pollItem);
    }

    public Poll getPoll() {
        return poll;
    }

    public boolean isSend() {
        return send;
    }
}
