package com.example.womenshop;


public class Shoes extends Product {

  private int shoeSize;

  public Shoes(String name, double price, int nbItems, int shoeSize) {
    super(name, price, nbItems);
    if(shoeSize>=36 && shoeSize <=50){
      this.shoeSize = shoeSize;
    }else throw new IllegalArgumentException("Size is not valid");
  }

  public Shoes(int id, String name, double price, int nbItems, int shoeSize) {
    super(id, name, price, nbItems);
    if(shoeSize>=36 && shoeSize <=50){
      this.shoeSize = shoeSize;
    }else throw new IllegalArgumentException("Size is not valid");
  }

  public int getSize() {
    return shoeSize;
  }

  public void setShoeSize(int shoeSize) throws IllegalArgumentException {
    if(shoeSize>=36 && shoeSize <=50){
      this.shoeSize = shoeSize;
    }else throw new IllegalArgumentException("Size is not valid");
  }

  @Override
  public String toString() {
    return "Shoes            | " + super.toString()+
            " | Size : " + shoeSize;
  }


  public static double applyDiscount(double price) {
    return (price*(1-0.2));
  }

  public static double disableDiscount(double price) {
    return (price/(1-0.2));
  }

}
