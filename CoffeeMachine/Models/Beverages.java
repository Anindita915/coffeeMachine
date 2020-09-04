package CoffeeMachine.Models;

import java.util.List;

public class Beverages {
    String name;
    List<Ingredients> ingredients;

    public Beverages(String n, List<Ingredients> ing) {
        name = n;
        ingredients = ing;
    }

    public String getName() {
        return name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

}