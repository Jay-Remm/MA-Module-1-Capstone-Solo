package com.techelevator;

public class Chip extends Product {
    private String name;
    private double price;
    private int quantityRemaining;

    public Chip() {
        super();
    }

    public Chip(String name, double price, int quantityRemaining) {
        super();
        this.name = name;
        this.price = price;
        this.quantityRemaining = quantityRemaining;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int getQuantityRemaining() {
        return quantityRemaining;
    }

    @Override
    public void setQuantityRemaining(int quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }

    public String dispenseMessage() {
        return "Crunch Crunch, Yum!";
    }
}
