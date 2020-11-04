package game;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

public class Agilisaurus extends Dinosaur {
    private Behaviour behaviour;

    /**
     * This constructor instantiates a new Agilisaurus by calling the Dinosaur class constructor which in turn calls the Actor class constructor.
     */
    public Agilisaurus() {
        super("agilisaurus", 'g', 100);
        this.setGender(this.randomiseGender());
        behaviour = new WanderBehaviour();
    }

    /**
     * This method increases an agilisaurus instance's food level by 50 each time it eats a dead dinosaur
     */
    public void eatCarcass(){
        this.setFoodLvl(this.getFoodLvl() + 50);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action wander = behaviour.getAction(this, map);
        if (wander != null)
            return wander;

        return new DoNothingAction();
    }
}
