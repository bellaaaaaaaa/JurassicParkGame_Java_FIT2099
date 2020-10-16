package edu.monash.fit2099.engine;

public class LaserGun extends WeaponItem {
    /**
     * Constructor.
     *
     */
    public LaserGun() {
        super("LaserGun", 'z', 100, "zaps");
        this.setPrice(500);
    }
}
