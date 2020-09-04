package CoffeeMachine.Services;

import java.util.concurrent.CountDownLatch;

import CoffeeMachine.CoffeeMachine;
import CoffeeMachine.Models.Beverages;

/* This class is respons for running parallel threads to dispense beverages in parallel */
public class DispenseOps implements Runnable {

    Inventory inventory = new Inventory();
    Beverages beverage;
    CoffeeMachine coffeeMachineState;
    CountDownLatch latch;

    public DispenseOps(Beverages bev, CoffeeMachine coffeeMachineState) throws InterruptedException {
        beverage = bev;
        this.coffeeMachineState = coffeeMachineState;
        // this.latch = latch;
    }

    @Override
    public void run() {

        try {

            boolean dispense = inventory.checkQuantityOfIngredient(beverage); // checking inventory. Will dispense iff
                                                                              // ingredients are sufficient

            if (dispense) {

                this.inventory.updateInventory(beverage); // pulling items from the invetory & updating

                Thread.currentThread();
                Thread.sleep(2000); // delay added to show parallel processing and replicate processing time
                System.out.println("***** " + beverage.getName() + " prepared *****\n");
            }

            CoffeeMachine.permits.complete();

        } catch (Exception e) {
            e.printStackTrace();
            CoffeeMachine.permits.complete();

        }
    }

}