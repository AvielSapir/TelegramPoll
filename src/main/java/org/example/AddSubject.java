package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSubject extends JDialog{
    private JTextField textField;
    private String subject;
    private int number;
    private boolean full = false;

    public AddSubject(JFrame parent) {
        super(parent, "Add Question", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 1, 10, 10)); // Changed layout to accommodate new elements
        getContentPane().setBackground(new Color(61, 61, 61));
        setSize(500, 250);
        setResizable(false);

        textField = newTextField();
        JLabel subjectLabel = newLabel("SUBJECT", 20);
        JLabel amountLabel = newLabel("Amount of questions to create", 17); // New label for amount

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Panel for buttons
        buttonPanel.setBackground(new Color(61, 61, 61));

        JButton button1 = newButton("1", 20);
        JButton button2 = newButton("2", 20);
        JButton button3 = newButton("3", 20);

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        add(subjectLabel);
        add(textField);
        add(amountLabel);
        add(buttonPanel);
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
        Border newBorder = BorderFactory.createLineBorder(new Color(33, 33, 33), 1);
        textField.setBorder(newBorder);
        return textField;
    }

    private JButton newButton(String text, int size) {
        JButton button = new JButton(text);
        button.setBackground(new Color(80, 79, 79));
        button.setForeground(new Color(208, 208, 208));
        button.setFont(new Font("Arial", Font.BOLD, size));
        button.setPreferredSize(new Dimension(80, 40)); // Adjusted button size
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a subject.");
                    return;
                }
                subject = textField.getText();
                number = Integer.parseInt(text);
                full = true;
                dispose();
            }
        });
        return button;
    }

    public String getSubject() {
        return subject;
    }
    public int getNumber() {
        return number;
    }

    public boolean isFull() {
        return full;
    }

}
