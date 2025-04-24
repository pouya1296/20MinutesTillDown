package org.example.controller;

import org.example.Model.App;
import org.example.Model.Response;
import org.example.Model.User;


import java.sql.SQLException;

import static org.example.controller.DatabaseManager.getUser;

public class RegisterMenuController {
    public static Response checkUsername(String username) throws SQLException {
        User user = getUser(username);
        if (user != null)
            return new Response("Username already taken", false);
        return new Response(" ", true);
    }

    public static Response checkPassword(String password) {
        if(password.length() < 8)
            return new Response("Password must be at least 8 characters", false);
        if(!hasSpecial(password))
            return new Response("Password must be at least 1 special character", false);
        if(!password.matches("[A-Z0-9]*"))
            return new Response("Password must contain at least 1 uppercase character and 1 digit", false);
        return new Response(" ", true);
    }

    private static boolean hasSpecial(String password) {
        for(int i = 0; i < password.length(); i++) {
            if(isSpecial(password.charAt(i)))
                return true;
        }
        return false;
    }

    private static boolean isSpecial(char ch) {
        return ch == '@' || ch == '#' || ch == '$' ||
                ch == '%' || ch == '&' || ch == '*' || ch == ')' ||
                ch == '_' || ch == '(';
    }



}