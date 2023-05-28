package model;

public abstract class Item {
    int itemId;
    protected String name;
    protected int price;
    protected int calories;

    protected Item(int ItemId,String name, int price, int calories) {
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.itemId = ItemId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCalories() {
        return calories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
