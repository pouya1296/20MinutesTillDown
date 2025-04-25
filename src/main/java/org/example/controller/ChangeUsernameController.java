package org.example.controller;

import org.example.Model.Response;
import org.example.Model.User;

import java.sql.SQLException;

import static org.example.controller.DatabaseManager.getUser;

public class ChangeUsernameController {
    public static Response checkUsername(String username) throws SQLException {
        User user = getUser(username);
        if (user != null)
            return new Response("Username already taken", false);
        return new Response(" ", true);
    }
}
