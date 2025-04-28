package org.example.Model.enums;

public enum Hero {
    SHANA("SHANA",4, 4),
    DIAMOND("DIAMOND",7, 1),
    SCARLET("SCARLET",5, 3),
    LILITH("LILITH",3, 5),
    DASHER("DASHER",10, 2)
    ;
    public final String name;
    public final int maxHP;
    public final int speed;

    Hero(String name, int maxHP, int speed) {
        this.name = name;
        this.maxHP = maxHP;
        this.speed = speed;
    }

    public static Hero getHero(String name) {
        for (Hero hero : Hero.values()) {
            if (hero.name.equals(name)) {
                return hero;
            }
        }
        return null;
    }
}
