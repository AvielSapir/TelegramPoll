package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BotConfig {
    private static String userName;
    private static String token;

    private static String gptApi;

    static {
        try {
            Properties properties = new Properties();
            InputStream input = BotConfig.class.getClassLoader().getResourceAsStream("config.properties");

            if (input == null) {
                throw new FileNotFoundException("config.properties not found in resources!");
            }

            properties.load(input);

            userName = properties.getProperty("bot.username");
            token = properties.getProperty("bot.token");
            gptApi = properties.getProperty("gpt.api");

        } catch (IOException e) {
            System.err.println("Failed to load bot config: " + e.getMessage());
        }
    }

    public static String getUserName() {
        return userName;
    }

    public static String getToken() {
        return token;
    }

    public static String getGptApi() {
        return gptApi;
    }
}