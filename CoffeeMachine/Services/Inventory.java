package CoffeeMachine.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import CoffeeMachine.Models.*;

/* Inventory Service class */
public class Inventory {

    /*
     * Synchronized collections of beverages and ingredients for thred-safe
     * execution
     */
    public static List<Beverages> beverages = Collections.synchronizedList(new ArrayList<>());
    public static List<InvIngredients> invIngredients = Collections.synchronizedList(new ArrayList<>());
    boolean lowSupply;

    /* Method to replenish inventory */
    public synchronized void addIngredient(Integer updatedamount, InvIngredients ingredient) {

        int index = invIngredients.indexOf(ingredient);

        ingredient.setQuantity(ingredient.getQuantity() + updatedamount);

        if (ingredient.getQuantity() >= ingredient.getMin_quantity())
            ingredient.lowSupply = false;

        getInvIngredients().set(index, ingredient);

    }

    /*
     * Validating quantity required to prepare the beverage. Will print unavailable
     * if not present in ventory and insufficient if difference will below 0 or
     * current quantity is 0
     */
    synchronized public boolean checkQuantityOfIngredient(Beverages beverage) {
        // ensuring no concureency issue happens
        synchronized (invIngredients) {
            for (Ingredients ing : beverage.getIngredients()) {

                if (!invIngredients.stream().anyMatch(i -> i.getName().equals(ing.getName()))) {

                    System.out.println("***** " + beverage.getName() + " cannot be prepared because " + ing.getName()
                            + " is not available *****");

                    return false;
                }

                else if (invIngredients.stream().anyMatch(i -> i.getName().equals(ing.getName())
                        && (i.getQuantity() == 0 || i.getQuantity() - ing.getQuantity() < 0))) {

                    InvIngredients in = invIngredients.stream().filter(i -> i.getName().equals(ing.getName())).findAny()
                            .orElse(null);

                    int index = invIngredients.indexOf(in);

                    in.lowSupply = true;

                    invIngredients.set(index, in);

                    System.out.println("***** " + beverage.getName() + " cannot be prepared because " + ing.getName()
                            + " is not sufficient *****\n");

                    return false;

                }

            }

            return true;

        }

    }

    public boolean getlowSupply() {
        if (invIngredients.stream().anyMatch(i -> i.lowSupply == true || i.getQuantity() < i.getMin_quantity())) {
            // lowSupplyIngredients();

            return true;
        }

        else
            return false;
    }

    public void lowSupplyIngredients() {

        List<InvIngredients> lowIngs = invIngredients.stream()
                .filter(i -> i.getLowSuppy() == true || i.getQuantity() < i.getMin_quantity())
                .collect(Collectors.toList());
        System.out.println("Low supply for following items:");
        lowIngs.forEach(ing -> System.out.println(ing.getName() + " -> " + ing.getQuantity()));

    }

    /* Method to pull from inventory and update it */

    synchronized public void updateInventory(Beverages beverage) {
        // ensuring no concureency issue happens
        synchronized (invIngredients) {
            for (Ingredients ing : beverage.getIngredients()) {

                InvIngredients in = invIngredients.stream().filter(i -> i.getName().equals(ing.getName())).findAny()
                        .orElse(null);

                int index = invIngredients.indexOf(in);
                int diff = in.getQuantity() - ing.getQuantity();

                in.setQuantity(diff);

                if (in.getQuantity() < in.getMin_quantity())
                    in.setLowSupply(true);

                invIngredients.set(index, in);

            }

        }
    }

    public List<Beverages> getBeverages() {
        return beverages;
    }

    synchronized public static List<InvIngredients> getInvIngredients() {
        return invIngredients;
    }

    // Initializing inventory with mock values
    public static void initialize() {

        invIngredients = Arrays.asList(new InvIngredients("hot_water", 400, 400, 2000),
                new InvIngredients("hot_milk", 400, 400, 2000), new InvIngredients("ginger_syrup", 30, 30, 500),
                new InvIngredients("sugar_syrup", 30, 30, 500), new InvIngredients("tea_leaves_syrup", 60, 60, 1000));

        List<Ingredients> tea_ings = Arrays.asList(new Ingredients("hot_water", 100), new Ingredients("hot_milk", 100),
                new Ingredients("ginger_syrup", 10), new Ingredients("sugar_syrup", 10),
                new Ingredients("tea_leaves_syrup", 30));
        List<Ingredients> cof_ings = Arrays.asList(new Ingredients("hot_water", 100), new Ingredients("hot_milk", 400),
                new Ingredients("ginger_syrup", 30), new Ingredients("sugar_syrup", 50),
                new Ingredients("tea_leaves_syrup", 30));
        List<Ingredients> blacktea_ings = Arrays.asList(new Ingredients("hot_water", 300),
                new Ingredients("ginger_syrup", 30), new Ingredients("sugar_syrup", 50),
                new Ingredients("tea_leaves_syrup", 30));
        List<Ingredients> greentea_ings = Arrays.asList(new Ingredients("green_mixture", 30),
                new Ingredients("hot_water", 300), new Ingredients("ginger_syrup", 30),
                new Ingredients("sugar_syrup", 50));

        Beverages tea = new Beverages("hot_tea", tea_ings);
        Beverages coffee = new Beverages("hot_coffee", cof_ings);
        Beverages blacktea = new Beverages("black_tea", blacktea_ings);
        Beverages greentea = new Beverages("green_tea", greentea_ings);

        beverages.add(tea);
        beverages.add(coffee);
        beverages.add(blacktea);
        beverages.add(greentea);
    }

}