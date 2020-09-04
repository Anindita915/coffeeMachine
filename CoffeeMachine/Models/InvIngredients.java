package CoffeeMachine.Models;

public class InvIngredients extends Ingredients {

    Integer min_quantity;
    public boolean lowSupply;

    public InvIngredients(String n, Integer qty, Integer min) {
        super(n, qty);
        min_quantity = min;

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

    @Override
    public boolean equals(Object obj) {

        if (obj.getClass() == Ingredients.class || obj.getClass() == InvIngredients.class) {
            Ingredients ing = (Ingredients) obj;
            if (this.name == ing.name)
                return true;

            else
                return false;
        } else

            return false;
    }

}