package com.example.womenshop;


public class Accessories extends Product{

  public Accessories(String name, double price, int nbItems) {
    super(name, price, nbItems);
  }
  public Accessories(int id, String name, double price, int nbItems) {
    super(id, name, price, nbItems);
  }

  @Override
  public String toString() {
    return "Accessories   | "+ super.toString();
  }


  public static double applyDiscount(double price) {
    return (price*(1-0.5));
  }


  public static double disableDiscount(double price) {
    return (price/(1-0.5));
  }
}
