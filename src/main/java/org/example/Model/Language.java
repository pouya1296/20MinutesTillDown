package org.example.Model;

public enum Language {
    REGISTER(new String[]{"Register", "S'inscrire"}),
    EXIT(new String[]{"Exit", "Quitter"});

    public final String[] languages;

    Language(String[] languages) {
        this.languages = languages;
    }

}
