package org.example;

import org.json.JSONArray;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserManager {


    private final String USERS_FILE = "src/main/resources/users.json";
    private List<Long> usersId;

    public UserManager() {
        this.usersId = loadUserIds();
    }
    private List<Long> loadUserIds() {
        List<Long> loadedIds = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(USERS_FILE))) {
                String content = new String(Files.readAllBytes(Paths.get(USERS_FILE)));
                if (!content.trim().isEmpty()) {
                    JSONArray jsonArray = new JSONArray(content);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        loadedIds.add(jsonArray.getLong(i));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user IDs file: " + e.getMessage());
        } catch (org.json.JSONException e) {
            System.err.println("Error parsing JSON from user IDs file: " + e.getMessage());
        }
        return loadedIds;
    }


    private void saveUserIds() {
        JSONArray jsonArray = new JSONArray(usersId);
        try (FileWriter file = new FileWriter(USERS_FILE)) {
            file.write(jsonArray.toString(4));
            file.flush();
        } catch (IOException e) {
            System.err.println("Error writing user IDs file: " + e.getMessage());
        }
    }


    public boolean addUserId(long chatId) {
        if (!usersId.contains(chatId)) {
            usersId.add(chatId);
            saveUserIds();
            System.out.println("User: " + chatId + " added.");
            return true;
        }
        return false;
    }

    public List<Long> getUsersId() {
        return usersId;
    }
}
