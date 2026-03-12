package com.example.cryptodemo;

public class Crypto {
    public String name;
    public double price;
    public double change24h;

    public Crypto(String name, double price, double change24h) {
        this.name = name;
        this.price = price;
        this.change24h = change24h;
    }
}