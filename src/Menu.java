import java.util.*;
public class Menu {
    public ArrayList<Food>foods;
    public ArrayList<Drink>drinks;

    public Menu() {
        foods = new ArrayList<Food>();
        drinks = new ArrayList<Drink>();
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public void addDrink(Drink drink) {
        drinks.add(drink);
    }

    public void removeFood(Food food) {
        foods.remove(food);
    }

    public void removeDrink(Drink drink) {
        drinks.remove(drink);
    }

    public void printMenu() {
        System.out.println("Menu:");
        System.out.println("Foods:");
        for (Food food : foods) {
            System.out.println(food.getName() + " " + food.getPrice() + " " + food.getCalories() + " " + food.getWeight());
        }
        System.out.println("Drinks:");
        for (Drink drink : drinks) {
            System.out.println(drink.getName() + " " + drink.getPrice() + " " + drink.getCalories() + " " + drink.getVolume());
        }
    }

}
