package edu.monash.fit2099.engine;

/**
 * A type of weapon that players can own and zap stegosaurs with
 */
public class LaserGun extends WeaponItem {
    /**
     * A constructor for a WeaponItem called LaserGun.
     * LaserGuns can be used to zap stegosaurs and kill them within one or two hits
     * They are a portable item a player instance can carry
     */
    public LaserGun() {
        super("LaserGun", 'z', 100, "zaps");
        this.setPrice(500);
    }
}
