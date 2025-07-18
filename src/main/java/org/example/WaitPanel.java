package org.example;

import javax.swing.*;
import java.awt.*;

public class WaitPanel extends JPanel {

    private String time = "";
    JLabel timeLabel;


    public WaitPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(31, 31, 35)); // רקע כהה

        String text = "Please wait...";
        JLabel waitingLabel = new JLabel(text, SwingConstants.CENTER);
        Font labelFont = new Font("Arial", Font.BOLD, 50);
        waitingLabel.setFont(labelFont);
        waitingLabel.setForeground(Color.WHITE);
        waitingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        FontMetrics metrics = waitingLabel.getFontMetrics(labelFont);
        int textWidth = metrics.stringWidth(text);
        int progressBarWidth = textWidth + 40;

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(progressBarWidth, 25));
        progressBar.setMaximumSize(new Dimension(progressBarWidth, 25));
        progressBar.setBackground(new Color(31, 31, 35));
        progressBar.setForeground(new Color(55, 55, 55));
        progressBar.setBorderPainted(false);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        timeLabel = new JLabel("5:00", SwingConstants.CENTER);
        Font timeFont = new Font("roberto", Font.PLAIN, 12);
        timeLabel.setFont(timeFont);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        this.add(Box.createVerticalGlue());
        this.add(waitingLabel);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(progressBar);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(timeLabel);
        this.add(Box.createVerticalGlue());

        this.setVisible(true);
    }

    public void updateTime(int min,int sec) {
        String time = String.format("%02d:%02d", min, sec);
        this.timeLabel.setText("The Poll will close in " + time + " minutes.");
    }
}
