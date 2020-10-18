package edu.monash.fit2099.engine;

import game.Player;
import game.Stegosaur;

public class laserStegosaurAction extends Action {
    Stegosaur stegosaur;
    GameMap stegosaurMap;

    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof Player) {
            // Kill stegosaur in one or two hits. laser attack will be 50, where Dinosaur max health is 100.
            this.getStegosaur().hurt(50);
            if(this.getStegosaur().hitPoints <= 0){
                this.getStegosaur().setDead(true);
                this.getStegosaur().setNumTurnsDead(0);
                this.getStegosaur().setUnconscious(true);
                return "Player killed Stegosaur";
            }
        }
        return "Player attacked Stegosaur, it has " + this.getStegosaur().hitPoints + " health remaining";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Zap Stegosaur at (" + this.getStegosaurMap().locationOf(this.getStegosaur()).x() + ", " + this.getStegosaurMap().locationOf(this.getStegosaur()).y() + ")";
    }

    public Stegosaur getStegosaur() {
        return stegosaur;
    }

    public void setStegosaur(Stegosaur stegosaur) {
        this.stegosaur = stegosaur;
    }

    public GameMap getStegosaurMap() {
        return stegosaurMap;
    }

    public void setStegosaurMap(GameMap stegosaurMap) {
        this.stegosaurMap = stegosaurMap;
    }
}
