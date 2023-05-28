package model;

public class Food extends Item {
    int foodId;
    private int weight;

    public Food(int foodId, int itemId, String name, int price, int calories, int weight) {
        super(itemId, name, price, calories);
        this.foodId = foodId;
        this.weight = weight;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
