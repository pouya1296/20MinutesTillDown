package org.example.Model.enums;

public enum Weapon {
    REVOLVER("REVOLVER", 20, 1, 1, 6, new String[]{"C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\RevolverStill.png", "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\RevolverReload_0.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\RevolverReload_1.png", "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\RevolverReload_2.png", "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\RevolverReload_3.png"}),
    SHOTGUN("SHOTGUN", 10, 4, 1, 2, new String[]{"C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_Shotgun_SS_0.png", "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_Shotgun_SS_1.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_Shotgun_SS_1.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_Shotgun_SS_2.png", "C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\T_Shotgun_SS_3.png"}),
    SMG_S_DUAL("SMGs DUAL", 8 ,1, 2, 24, new String[]{"C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\SMGStill.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\SMGReload_0.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\SMGReload_1.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\SMGReload_2.png","C:\\Users\\ASUS\\IdeaProjects\\20min\\src\\main\\java\\org\\example\\view\\project assets\\Sprite\\SMGReload_3.png"}),;
    public String name;
    public int damage;
    public int projectile;
    public int reloadTime;
    public int maxAmmo;
    public  String[] paths;

    Weapon(String name, int damage, int projectile, int reloadTime, int maxAmmo, String[] paths) {
        this.name = name;
        this.damage = damage;
        this.projectile = projectile;
        this.reloadTime = reloadTime;
        this.maxAmmo = maxAmmo;
        this.paths = paths;
    }
}
