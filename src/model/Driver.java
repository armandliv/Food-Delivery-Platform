package model;

public class Driver extends Person implements Comparable<Driver>{
    int driverId;
    private String car;
    private int yearsOfExperience;

    public Driver(int driverId, int personId, String firstName, String lastName, int age, String car, int yearsOfExperience) {
        super(personId, firstName, lastName, age);
        this.driverId = driverId;
        this.car = car;
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
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
