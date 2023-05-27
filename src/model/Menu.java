package model;

import java.util.*;
public class Menu {
    public ArrayList<Food>foods;
    public ArrayList<Drink>drinks;

    public Menu() {
        foods = new ArrayList<Food>();
        drinks = new ArrayList<Drink>();
    }

    public void addFood(String name, int price, int calories, int weight) {
        Food food = new Food(name, price, calories, weight);
        foods.add(food);
    }

    public void addDrink(String name, int price, int calories, int volume) {
        Drink drink = new Drink(name, price, calories, volume);
        drinks.add(drink);
    }

    public void removeFood(Food food) {
        foods.remove(food);
    }

    public void removeDrink(Drink drink) {
        drinks.remove(drink);
    }

    public void printMenu() {
        System.out.println("model.Menu:");
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
