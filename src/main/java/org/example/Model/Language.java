package org.example.Model;

public enum Language {
    REGISTER(new String[]{"Register", "S'inscrire"}),
    EXIT(new String[]{"Exit", "Quitter"}),
    LOGIN(new String[]{"Login", "Connexion"}),
    PLAY_AS_GUEST(new String[]{"Play as Guest", "Jouer en tant qu'invité"}),
    ENTER_YOUR_USERNAME(new String[]{"Enter your username"}),
    TYPE_YOUR_PASSWORD(new String[]{"Type your password"}),
    ENTER_YOUR_SECURITY_QUESTION(new String[]{"Enter your security question"}),
    ANSWER_YOUR_SECURITY_QUESTION(new String[]{"Answer your security question"}),
    TYPE_YOUR_PASSWORD_OR_FORGET(new String[]{"Type your password or \\'forget\\'"}),
    LOGGED_IN_SUCCESSFULLY(new String[]{"Logged in successfully"}),
    REGISTERED_SUCCESSFULLY(new String[]{"Registered Successfully you are logged in"}),
    USER_NOT_FOUND(new String[]{"User not found"}),
    INCORRECT_PASSWORD(new String[]{"Incorrect password"}),
    ;

    public final String[] languages;

    Language(String[] languages) {
        this.languages = languages;
    }

}
