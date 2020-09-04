package CoffeeMachine;

import java.util.List;

import CoffeeMachine.Models.Beverages;

/*Interface to represent states of the Coffee Machine */
public interface ICoffeMachineState {

    void selectBeverages();

    void refillIngredients();

    void dispenseBeverage(List<Beverages> beverage);

}