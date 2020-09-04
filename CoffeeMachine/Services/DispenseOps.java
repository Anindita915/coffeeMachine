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
            Thread.currentThread();
            Thread.sleep(2000); // delay added to show parallel processing and replicate processing time
            boolean dispense = inventory.checkQuantityOfIngredient(beverage); // checking inventory. Will dispense iff
                                                                              // ingredients are sufficient

            if (dispense) {

                this.inventory.updateInventory(beverage); // pulling items from the invetory & updating
                System.out.println("***** " + beverage.getName() + " prepared *****\n");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            CoffeeMachine.permits.complete(); // releasing permit to accept new orders
        }
    }

}