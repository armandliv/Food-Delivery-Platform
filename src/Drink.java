public class Drink extends Item {
    private int volume;

    public Drink(String name, int price, int calories, int volume) {
        super(name, price, calories);
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
