package org.example.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.example.Model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class GameDataManager {
    private static final String FILE_PATH = "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\controller\\game.json";
    private static  Gson gson = new Gson();

    static {

        RuntimeTypeAdapterFactory<Enemy> enemyAdapterFactory = RuntimeTypeAdapterFactory
                .of(Enemy.class, "saveType")
                .registerSubtype(TentacleMonster.class, "TentacleMonster")
                .registerSubtype(EyeBat.class, "EyeBat")
                .registerSubtype(Elder.class, "Elder");

        gson = new GsonBuilder()
                .registerTypeAdapterFactory(enemyAdapterFactory)
                .create();
    }

    public static void saveGame(Game game) {
        List<Game> games = loadAllGames();
        games.add(game);

        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(games, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Game> loadAllGames() {

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            try {
                file.createNewFile();
                try (Writer writer = new FileWriter(FILE_PATH)) {
                    gson.toJson(new ArrayList<Game>(), writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<List<Game>>() {}.getType());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }


    public static List<Game> getUserGames(String username) {
        List<Game> games = loadAllGames();
        List<Game> userGames = new ArrayList<>();

        for (Game game : games) {
            if (game.username.equals(username)) {
                userGames.add(game);
            }
        }
        return userGames;
    }

    public static boolean updateGameUsername(String oldUsername, String newUsername) {
        List<Game> games = loadAllGames();
        boolean updated = false;

        for (Game game : games) {
            if (game.username.equals(oldUsername)) {
                game.username = newUsername;
                updated = true;
            }
        }

        if (updated) {
            try (Writer writer = new FileWriter(FILE_PATH)) {
                gson.toJson(games, writer);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Game getLastGame(String username) {
        List<Game> userGames = getUserGames(username);

        if (!userGames.isEmpty()) {
            return userGames.get(userGames.size() - 1);
        }

        return null;
    }


}

