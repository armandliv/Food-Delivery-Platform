package model;

public class Drink extends Item {
    int drinkId;
    private int volume;

    public Drink(int drinkId, int itemId, String name, int price, int calories, int volume) {
        super(itemId, name, price, calories);
        this.drinkId = drinkId;
        this.volume = volume;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
