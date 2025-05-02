package org.example.Model.enums;

public enum Hero {
    SHANA("SHANA",4, 400, "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\Idle_0 #8330.png", "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\Run_0 #8762.png"),
    DIAMOND("DIAMOND",7, 100, "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\Idle_0 #8328.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\Run_0 #8760.png"),
    SCARLET("SCARLET",5, 300, "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\Idle_0 #8327.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\Run_0 #8759.png"),
    LILITH("LILITH",3, 500, "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\Idle_0 #8333.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\Run_0 #8765.png"),
    DASHER("DASHER",10, 200, "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\Idle_0 #8325.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\Run_0 #8757.png")
    ;
    public final String name;
    public final int maxHP;
    public final int speed;
    public final String idlePath;
    public final String runningPath;

    Hero(String name, int maxHP, int speed, String idlePath, String runningPath) {
        this.name = name;
        this.maxHP = maxHP;
        this.speed = speed;
        this.idlePath = idlePath;
        this.runningPath = runningPath;
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
