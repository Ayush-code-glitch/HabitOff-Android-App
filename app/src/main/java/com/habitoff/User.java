package com.habitoff;

/**
 * User model class to represent user data
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String quitDate;
    private int cigarettesPerDay;
    private double pricePerPack;

    public User() {
    }

    public User(int id, String username, String email, String quitDate,
                int cigarettesPerDay, double pricePerPack) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.quitDate = quitDate;
        this.cigarettesPerDay = cigarettesPerDay;
        this.pricePerPack = pricePerPack;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(String quitDate) {
        this.quitDate = quitDate;
    }

    public int getCigarettesPerDay() {
        return cigarettesPerDay;
    }

    public void setCigarettesPerDay(int cigarettesPerDay) {
        this.cigarettesPerDay = cigarettesPerDay;
    }

    public double getPricePerPack() {
        return pricePerPack;
    }

    public void setPricePerPack(double pricePerPack) {
        this.pricePerPack = pricePerPack;
    }
}