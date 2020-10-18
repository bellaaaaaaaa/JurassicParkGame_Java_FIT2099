package game;

import edu.monash.fit2099.engine.Food;

/**
 * This is a meal kit class which inherits the functionality and behaviour of food
 */
public class MealKit extends Food {
    String type;

    /**
     * This constructor instantiates a meal kit. Meal kits can be fed to dinosaurs so that their foodLvl reaches its maximum
     * @param type a string value which must either be carnivore or vegetarian.
     */
    public MealKit(String type){
        super(9999, 100, 0, "Vegetarian Meal Kit", 'm', true);
        if (type.equals("carnivore") || type.equals("vegetarian")){
            this.type = type;
            this.setPrice(100);
            if (this.type == "carnivore"){
                this.setPrice(500);
                this.setName("Carnivore Meal Kit");
            }
        } else {
            throw new IllegalArgumentException("Type must be either carnivore or vegetarian");
        }
    }

    /**
     * Indicates the type of mealkit
     * @return a string type of either "carnivore" or "vegetarian"
     */
    public String getType() {
        return type;
    }
}
