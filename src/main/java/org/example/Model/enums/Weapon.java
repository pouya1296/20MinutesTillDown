package org.example.Model.enums;

public enum Weapon {
    REVOLVER("REVOLVER", 20, 1, 1, 6),
    SHOTGUN("SHOTGUN", 10, 4, 1, 2),
    SMG_S_DUAL("SMGs DUAL", 8 ,1, 2, 24),;
    public final String name;
    public final int damage;
    public final int projectile;
    public final int reloadTime;
    public final int maxAmmo;

    Weapon(String name, int damage, int projectile, int reloadTime, int maxAmmo) {
        this.name = name;
        this.damage = damage;
        this.projectile = projectile;
        this.reloadTime = reloadTime;
        this.maxAmmo = maxAmmo;
    }
}
