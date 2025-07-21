package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BotConfig {
    private static final String CONFIG_PATH = "config.properties";
    private static String userName;
    private static String token;

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(CONFIG_PATH));
            userName = properties.getProperty("bot.username");
            token = properties.getProperty("bot.token");
        } catch (IOException e) {
            System.err.println("Failed to load bot config: " + e.getMessage());
        }
    }

    public static String getUserName(){
        return userName;
    }

    public static String getToken(){
        return token;
    }
}
