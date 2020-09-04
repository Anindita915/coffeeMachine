package CoffeeMachine.States;

import java.util.List;
import java.util.Scanner;

import CoffeeMachine.CoffeeMachine;
import CoffeeMachine.ICoffeMachineState;
import CoffeeMachine.Models.Beverages;
import CoffeeMachine.Models.InvIngredients;
import CoffeeMachine.Services.Inventory;

public class FillIngredients implements ICoffeMachineState {
    private CoffeeMachine coffeeMachineState;
    private Inventory inventory = new Inventory();
    Scanner scanner = new Scanner(System.in);

    FillIngredients(CoffeeMachine coffeMachine) {

        coffeeMachineState = coffeMachine;

    }

    @Override
    public void selectBeverages() {

        System.out.println("Select beverages after filling is completed");

    }

    /*
     * This method governs the refill state of machine. Prompts user to refill
     * indufficient/unavailable items. User can fill or choose to skip the
     * ingredient
     */
    @Override
    public void refillIngredients() {

        int opt = -1;
        System.out.println("Enter quantity of unavailable ingredients or 0 to skip");

        for (InvIngredients ing : Inventory.getInvIngredients()) {

            if (ing.lowSupply || ing.getQuantity() < ing.getMin_quantity()) {
                int minrecommendedQty = (ing.getMin_quantity() - ing.getQuantity());
                int maxReccomQty = ing.getMax_quantity() - ing.getQuantity();
                System.out.println("Enter quantity to be added for: " + ing.getName()
                        + " Recommended quantity, Minimum: " + minrecommendedQty + ", Maximum: " + maxReccomQty);

                try {

                    opt = scanner.nextInt();

                    if (opt > 0 && opt <= maxReccomQty) {
                        inventory.addIngredient(opt, ing);

                    } else if (opt == 0)
                        continue;

                    else
                        System.out.println("Please enter positive values for quantity less than recommended max value");

                } catch (Exception ex) {
                    System.out.println("Invalid Input");
                }

            }

        }

        if (!inventory.getlowSupply())
            this.coffeeMachineState.setHasLowSupply(false);
        this.coffeeMachineState.setCoffeemachineState(new ReadyForSelection(coffeeMachineState)); // moving to selection
                                                                                                  // state
        coffeeMachineState.selectBeverages();

    }

    @Override
    public void dispenseBeverage(List<Beverages> name) {

        System.out.println("Filling ingredients. Please wait.");

    }
}