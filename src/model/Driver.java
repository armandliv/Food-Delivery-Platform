package model;

public class Driver extends Person implements Comparable<Driver>{
    private String car;
    private int yearsOfExperience;

    public Driver(String firstName, String lastName, int age, String car, int yearsOfExperience) {
        super(firstName, lastName, age);
        this.car = car;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void printDriver() {
        System.out.println("First name: " + firstName);
        System.out.println("Last name: " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Car: " + car);
        System.out.println("Years of experience: " + yearsOfExperience);
    }

    @Override
    public int compareTo(Driver p) {
        return -this.yearsOfExperience + p.yearsOfExperience;
    }
}
