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
                int recommendedQty = (ing.getMin_quantity() - ing.getQuantity()) + ing.getMin_quantity();
                System.out.println("Enter quantity to be added for: " + ing.getName()
                        + " Recommended quantity, at least: " + recommendedQty);

                try {
                    opt = scanner.nextInt();

                    if (opt != 0 && opt > 0) {
                        inventory.addIngredient(opt, ing);

                    } else if (opt == 0)
                        continue;

                    else
                        System.out.println("Please enter positive values for quantity");

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