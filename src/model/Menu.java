package model;

import java.sql.SQLOutput;
import java.util.*;
public class Menu {
    int menuId;
    public ArrayList<Food>foods;
    public ArrayList<Drink>drinks;

    public Menu(int menuId) {
        this.menuId = menuId;
        foods = new ArrayList<Food>();
        drinks = new ArrayList<Drink>();
    }

    public Menu(int menuId, ArrayList<Food> foods, ArrayList<Drink> drinks) {
        this.menuId = menuId;
        this.foods = foods;
        this.drinks = drinks;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void addFood(int foodId, int itemId, String name, int price, int calories, int weight) {
        Food food = new Food(foodId,itemId,name, price, calories, weight);
        foods.add(food);
    }

    public void addDrink(int drinkId,int itemId,String name, int price, int calories, int volume) {
        Drink drink = new Drink(drinkId,itemId,name, price, calories, volume);
        drinks.add(drink);
    }

    public void removeFood(Food food) {
        foods.remove(food);
    }

    public void removeDrink(Drink drink) {
        drinks.remove(drink);
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public void printMenu() {
        System.out.println("model.Menu:");
        System.out.println("menuId: " + menuId);
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
