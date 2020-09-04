package CoffeeMachine;

import java.util.concurrent.Semaphore;

/*Setting up a semaphore to mainatin acceses given to outlets. 
The semaphore is setup with a limit of number of outlets. 
All dispense requests will chck if any slots are available and 
the/ acquire a thread to dispense beverage */
public class DispensePermit {

    private Semaphore semaphore;

    public DispensePermit(int slotLimit) {
        semaphore = new Semaphore(slotLimit);
    }

    public boolean tryDispense() {
        return semaphore.tryAcquire();
    }

    public void complete() {
        semaphore.release();
    }

    public int availableSlots() {
        return semaphore.availablePermits();
    }
}