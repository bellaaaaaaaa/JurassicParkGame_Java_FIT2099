package game;

import edu.monash.fit2099.engine.Food;

public class MealKit extends Food {
    String type;
    public MealKit(String type){
        super(9999, 100, 0, "Vegetarian Meal Kit", 'm', true);
        this.type = type;
        this.setPrice(100);
        if (this.type == "carnivore"){
            this.setPrice(500);
            this.setName("Carnivore Meal Kit");
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
