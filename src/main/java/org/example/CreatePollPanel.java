package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePollPanel extends JPanel {

    int WIDTH;
    int HEIGHT;

    public CreatePollPanel(int width, int height, JFrame window) {
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


        panel1.add(addQBT(window, panel2));
        panel1.add(sendBT(window, panel2));
        panel1.add(gptBT(window, panel2));

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
        System.out.println("addCard");
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
        JButton addQBT = new JButton("Add Question");
        addQBT.setBackground(new Color(37, 37, 37));
        addQBT.setForeground(new Color(208, 208, 208));
        addQBT.setFont(new Font("Arial", Font.BOLD, 15));
        addQBT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addQBT.setBorderPainted(false);
        addQBT.setPreferredSize(new Dimension(200, 40));
        addQBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddQuestion addQuestion = new AddQuestion(frame);
                addQuestion.setLocationRelativeTo(frame);
                addQuestion.setVisible(true);
                if (addQuestion.isFull()) {
                    addCard(panel ,addQuestion.getQuestionValue(), addQuestion.getAnswerValue1(), addQuestion.getAnswerValue2(), addQuestion.getAnswerValue3());
                    System.out.println(addQuestion.getQuestionValue());
                }
            }
        });
        return addQBT;
    }

    //todo
    private JButton sendBT(JFrame frame, JPanel panel) {
        JButton addQBT = new JButton("send");
        addQBT.setBackground(new Color(37, 37, 37));
        addQBT.setForeground(new Color(208, 208, 208));
        addQBT.setFont(new Font("Arial", Font.BOLD, 15));
        addQBT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addQBT.setBorderPainted(false);
        addQBT.setPreferredSize(new Dimension(200, 40));
        addQBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        return addQBT;
    }
    private JButton gptBT(JFrame frame, JPanel panel) {
        JButton addQBT = new JButton("create with GPt");
        addQBT.setBackground(new Color(37, 37, 37));
        addQBT.setForeground(new Color(208, 208, 208));
        addQBT.setFont(new Font("Arial", Font.BOLD, 15));
        addQBT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addQBT.setBorderPainted(false);
        addQBT.setPreferredSize(new Dimension(200, 40));
        addQBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        return addQBT;
    }

}
