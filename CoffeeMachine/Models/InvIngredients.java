package CoffeeMachine.Models;

public class InvIngredients extends Ingredients {

    Integer min_quantity;
    Integer max_quantity;
    public boolean lowSupply;

    public InvIngredients(String n, Integer qty, Integer min, Integer max) {
        super(n, qty);
        min_quantity = min;
        max_quantity = max;
    }

    @Override
    public Integer getQuantity() {

        return super.getQuantity();
    }

    @Override
    public String getName() {

        return super.getName();
    }

    public Integer getMin_quantity() {
        return min_quantity;
    }

    public boolean getLowSuppy() {
        return lowSupply;

    }

    public void setLowSupply(boolean lowSupply) {
        this.lowSupply = lowSupply;
    }

    public void setMax_quantity(Integer max_quantity) {
        this.max_quantity = max_quantity;
    }

    public Integer getMax_quantity() {
        return max_quantity;
    }

}