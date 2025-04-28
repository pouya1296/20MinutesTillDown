package org.example.Model.enums;

public enum Ability {
    VITALITY("vitality"),
    DAMAGER("damager"),
    PROCREASE("procrease"),
    AMOCREASE("amorecase"),
    SPEEDY("speedy"),;
    public final String name;

    Ability(String name) {
        this.name = name;
    }
}
