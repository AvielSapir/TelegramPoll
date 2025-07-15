package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddQuestion extends JDialog {

    private boolean full = false;
    private String questionValue;
    private String answerValue1;
    private String answerValue2;
    private String answerValue3;
    private JLabel question;
    private JLabel option1;
    private JLabel option2;
    private JLabel option3;
    private JTextField questionField;
    private JTextField optionField1;
    private JTextField optionField2;
    private JTextField optionField3;
    private JButton okButton;

    private JTextField textField;
    private String userInput;

    public AddQuestion(JFrame parent) {
        super(parent, "Add Question", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));
        getContentPane().setBackground(new Color(61, 61, 61));
        setSize(500, 250);
        setResizable(false);

        questionValue = "";
        answerValue1 = "";
        answerValue2 = "";
        answerValue3 = "";

        textField = new JTextField(20);

        this.question = newLabel("Enter question: ", 20);
        this.option1 = newLabel("Enter option 1: ", 17);
        this.option2 = newLabel("Enter option 2: ", 17);
        this.option3 = newLabel("Enter option 3: ", 17);
        JLabel empty = new JLabel("", JLabel.CENTER);

        this.questionField = newTextField();
        this.optionField1 = newTextField();
        this.optionField2 = newTextField();
        this.optionField3 = newTextField();

        this.okButton = newButton("Save", 20);


        add(question);
        add(questionField);

        add(option1);
        add(optionField1);

        add(option2);
        add(optionField2);

        add(option3);
        add(optionField3);

        add(empty);
        add(okButton);
    }

    private JLabel newLabel(String text, int size) {
        JLabel jLabel = new JLabel(text);
        jLabel.setForeground(new Color(208, 208, 208));
        jLabel.setFont(new Font("Arial", Font.BOLD, size));
        Border paddingBorder = BorderFactory.createEmptyBorder(0, 10, 0, 0);
        jLabel.setBorder(paddingBorder);
        return jLabel;
    }

    private JTextField newTextField() {
        JTextField textField = new JTextField(1);
        textField.setBackground(new Color(80, 79, 79));
        textField.setForeground(new Color(208, 208, 208));
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        Border newBorder = BorderFactory.createLineBorder(new Color(33, 33, 33), 1); // Change to your desired color and thickness
        textField.setBorder(newBorder);

        return textField;
    }

    private JButton newButton(String text , int size) {
        JButton okButton = new JButton(text);
        okButton.setBackground(new Color(80, 79, 79));
        okButton.setForeground(new Color(208, 208, 208));
        okButton.setFont(new Font("Arial", Font.BOLD, size));
        okButton.setPreferredSize(new Dimension(10, 40));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (questionField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter question");
                    return;
                }
                if (optionField1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter option 1");
                    return;
                }
                if (optionField2.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter option 2");
                    return;
                }
                if (optionField3.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter option 3");
                    return;
                }

                questionValue = questionField.getText();
                answerValue1 = optionField1.getText();
                answerValue2 = optionField2.getText();
                answerValue3 = optionField3.getText();
                full = true;
                dispose();
            }
        });
        return okButton;
    }

    public boolean isFull() {
        return full;
    }

    public String getQuestionValue() {
        return questionValue;
    }
    public String getAnswerValue1() {
        return answerValue1;
    }
    public String getAnswerValue2() {
        return answerValue2;
    }
    public String getAnswerValue3() {
        return answerValue3;
    }
}
