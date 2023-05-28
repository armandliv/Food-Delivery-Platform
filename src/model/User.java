package model;

public class User extends Person{
    int userId;
    private String email;
    private String phoneNumber;

    public User(int userId, int personId,String firstName, String lastName, int age, String email, String phoneNumber) {
        super(personId, firstName, lastName, age);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void printUser() {
        System.out.println("First name: " + firstName);
        System.out.println("Last name: " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Email: " + email);
        System.out.println("Phone number: " + phoneNumber);
    }
}
