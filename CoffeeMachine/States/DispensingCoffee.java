package CoffeeMachine.States;

import java.util.List;

import CoffeeMachine.CoffeeMachine;
import CoffeeMachine.ICoffeMachineState;
import CoffeeMachine.Models.Beverages;
import CoffeeMachine.Services.*;

public class DispensingCoffee implements ICoffeMachineState {

    private CoffeeMachine coffeeMachineState;
    private Inventory inventory = new Inventory();

    DispensingCoffee(CoffeeMachine coffeMachine) {

        coffeeMachineState = coffeMachine;

    }

    @Override
    public void selectBeverages() {
        if (inventory.getlowSupply()) {
            this.coffeeMachineState.setHasLowSupply(true);
            inventory.lowSupplyIngredients();
        }
        coffeeMachineState.setCoffeemachineState(new ReadyForSelection(coffeeMachineState));
        coffeeMachineState.selectBeverages();
    }

    @Override
    public void refillIngredients() {
        System.out.println("Dispensing beverage. Try after completion.");

    }

    /*
     * Dispense state will be governed by this method. To parallely dispense
     * multiple beverage, threads are spun. Semaphore is used to grant access to
     * dispense requests only if permits are available
     */
    @Override
    public void dispenseBeverage(List<Beverages> beverage) {

        for (Beverages bev : beverage) {

            CoffeeMachine.permits.tryDispense();
            try {
                CoffeeMachine.executorService.execute(new DispenseOps(bev, coffeeMachineState));
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }
        coffeeMachineState.selectBeverages();
    }

}