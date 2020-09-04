package CoffeeMachine.Models;

public class Ingredients {

    String name;
    Integer quantity;

    public Ingredients(String n, Integer qty) {

        name = n;
        quantity = qty;

    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}