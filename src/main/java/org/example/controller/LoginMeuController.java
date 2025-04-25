package org.example.controller;

import org.example.Model.App;
import org.example.Model.enums.Language;
import org.example.Model.Response;
import org.example.Model.User;

import java.sql.SQLException;

public class LoginMeuController {
    public static Response checkUser(String username) throws SQLException {
        User user = DatabaseManager.getUser(username);
        if (user == null) {
            return new Response(Language.USER_NOT_FOUND.languages[App.languageNumber], false);
        }
        return new Response("User found.", true);
    }

    public static Response login(String username, String password) throws SQLException {
        User user = DatabaseManager.getUser(username);
        if(user.password.equals(password)) {
            return new Response("User logged in.", true);
        }
        return new Response(Language.INCORRECT_PASSWORD.languages[App.languageNumber], false);
    }
}
