package CoffeeMachine;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import CoffeeMachine.Models.Beverages;
import CoffeeMachine.Services.Inventory;
import CoffeeMachine.States.ReadyForSelection;

public class CoffeeMachine {

    private ICoffeMachineState coffeemachineState;
    public static int num_of_outlets = 3;
    public static ExecutorService executorService = Executors.newFixedThreadPool(num_of_outlets);
    public static DispensePermit permits = new DispensePermit(num_of_outlets);
    Inventory inventory = new Inventory();
    Beverages selectedBeverages;
    boolean hasLowSupply;

    /*
     * Contecxt class that holds the current state of the coffee machine and manages
     * all high level operations
     */
    CoffeeMachine() {
        Inventory.initialize();
        hasLowSupply = inventory.getlowSupply();
    }

    public void start() {

        coffeemachineState.selectBeverages();
    }

    public ICoffeMachineState getCoffeemachineState() {
        return coffeemachineState;
    }

    public CoffeeMachine setCoffeemachineState(ICoffeMachineState coffeemachineState) {
        this.coffeemachineState = coffeemachineState;
        return this;
    }

    public void selectBeverages() {

        this.coffeemachineState.selectBeverages();

    }

    public void refillIngredients() {
        this.coffeemachineState.refillIngredients();

    }

    public void dispenseBeverage(List<Beverages> selectedBeverages) {

        if (permits.availableSlots() >= selectedBeverages.size())

            this.coffeemachineState.dispenseBeverage(selectedBeverages);

        else {
            System.out.println("No outlet available!Try after sometime!");
            coffeemachineState.selectBeverages();

        }

    }

    public void setSelectedBeverages(Beverages selectedBeverages) {
        this.selectedBeverages = selectedBeverages;
    }

    public void setHasLowSupply(boolean hasLowSupply) {
        this.hasLowSupply = hasLowSupply;
    }

    public boolean getHasLowSupply() {
        return hasLowSupply;
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.setCoffeemachineState(new ReadyForSelection(coffeeMachine));
        coffeeMachine.start();

        CoffeeMachine.executorService.shutdown();
    }

}