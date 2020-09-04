package CoffeeMachine.States;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

import CoffeeMachine.CoffeeMachine;
import CoffeeMachine.ICoffeMachineState;
import CoffeeMachine.Models.Beverages;
import CoffeeMachine.Services.Inventory;

public class ReadyForSelection implements ICoffeMachineState {
    private CoffeeMachine coffeeMachineState;
    private Inventory inventory = new Inventory();
    Scanner scanner = new Scanner(System.in);
    List<Beverages> selectedBeverages = new ArrayList<>();

    public ReadyForSelection(CoffeeMachine coffeMachine) {

        coffeeMachineState = coffeMachine;

    }

    /*
     * This methid will govern the selection state of the machine. It will take care
     * of multiple selection of beverages and also provide an indicator when
     * ingredients run low. Refill can be done from here as well.
     */
    @Override
    public void selectBeverages() {
        int selectedOption = -1;

        System.out.println("\nPick your beverages (1,2..." + inventory.getBeverages().size() + "):");

        int i = 1;
        for (Beverages beverage : inventory.getBeverages()) {

            System.out.println(Integer.toString(i) + ". " + beverage.getName());
            i++;
        }
        System.out.println("");
        // Checking if inventory is surplus else will provide an option to refill
        if (inventory.getlowSupply() || coffeeMachineState.getHasLowSupply()) {
            System.out.println("Low supply of ingredients. Press 0 to refill\n");
            // inventory.lowSupplyIngredients();
        }

        // Reading user input for beverage selection
        try {
            String options[] = scanner.nextLine().split(",");

            // Limit beverage selection upto 'n' outlets.
            if (options.length > CoffeeMachine.num_of_outlets) {
                System.out.println("You Can only select up to " + CoffeeMachine.num_of_outlets + " beverages");
                coffeeMachineState.selectBeverages();
            }

            // Preparing a list of selected beverages and validating selections
            else {
                for (int j = 0; j < options.length; j++) {
                    int curr = Integer.parseInt(options[j]);
                    if (curr != 0 && curr <= inventory.getBeverages().size())
                        selectedBeverages.add(inventory.getBeverages().get(curr - 1));

                    else if (curr == 0 && j == 0) {
                        selectedOption = 0;
                        break;
                    } else
                        System.out.println("(" + curr + ")" + " No such beverage exists");

                }
            }

            // Option 0 will navigate the state to refill where on can replenish the
            // inventory
            if (selectedOption == 0 && inventory.getlowSupply()) {
                coffeeMachineState.setCoffeemachineState(new FillIngredients(coffeeMachineState));
                coffeeMachineState.refillIngredients();
            }

            // Navigation to Dispense state if user selects more that 1 beverage
            else if (selectedBeverages.size() > 0) {
                coffeeMachineState.setCoffeemachineState(new DispensingCoffee(coffeeMachineState)); // moving to
                                                                                                    // Dispense
                                                                                                    // state

                coffeeMachineState.dispenseBeverage(selectedBeverages);
            } else {
                System.out.println("Invalid selection! Please select from the given range");
                coffeeMachineState.selectBeverages();
            }
        } catch (Exception e) {
            System.out.println("Please select numbers!(1,2,3...)");
            coffeeMachineState.selectBeverages();
        }

    }

    @Override
    public void refillIngredients() {
        coffeeMachineState.setCoffeemachineState(new FillIngredients(coffeeMachineState));

    }

    @Override
    public void dispenseBeverage(List<Beverages> name) {

        System.out.println("Select Beverage first!");

    }

}