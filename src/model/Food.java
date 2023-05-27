package model;

public class Food extends Item {
    private int weight;

    public Food(String name, int price, int calories, int weight) {
        super(name, price, calories);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
