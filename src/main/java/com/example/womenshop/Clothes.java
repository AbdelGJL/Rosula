package com.example.womenshop;


public class Clothes extends Product{

  private int size;

  public Clothes(String name, double price, int nbItems, int size) {
    super(name, price, nbItems);
    if(size>=34 && size <=54){
      this.size = size;
    }else throw new IllegalArgumentException("Size is not valid");
  }

  public Clothes(int id, String name, double price, int nbItems, int size) {
    super(id, name, price, nbItems);
    if(size>=34 && size <=54){
      this.size = size;
    }else throw new IllegalArgumentException("Size is not valid");
  }

  public int getSize() {
    return size;
  }


  public void setSize(int size) throws IllegalArgumentException {
    if(size>=34 && size <=54){
      this.size = size;
    }else throw new IllegalArgumentException("Size is not valid");
  }



  @Override
  public String toString() {
    return "Clothes         | " +super.toString()+
            " | Size : " + size;
  }


  public static double applyDiscount(double price) {
    return price*(1-0.3);
  }

  public static double disableDiscount(double price) {
    return (price/(1-0.3));
  }


}
