package com.example.womenshop;

public abstract class Product implements Comparable<Product>{

  private int id;
  private String name;
  private double price;
  private int nbItems;

  //static int nb=0;
  static double income=0;

  public Product(int id, String name, double price, int nbItems) {
    this.id=id;
    this.name = name;
    if (price >= 0) {
      this.price = price;
    } else throw new IllegalArgumentException("Price is negative !");
    if(nbItems >= 0){
      this.nbItems = nbItems;
    }else throw new IllegalArgumentException("Yon can't have a negative quantity !");
  }

  public Product(String name, double price, int nbItems) {
    this.name = name;

    if (price >= 0) {
      this.price = price;
    } else throw new IllegalArgumentException("Price is negative !");
    if(nbItems >= 0){
      this.nbItems = nbItems;
    }else throw new IllegalArgumentException("Yon can't have a negative quantity !");
  }

  public int getId() {
    return id;
  }

  public void setId(int id){
    this.id=id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) throws IllegalArgumentException {
      if (price >= 0) {
        this.price = price;
      } else throw new IllegalArgumentException("Price is negative");
    }



  public int getNbItems() {
    return nbItems;
  }

  public void setNbItems(int nbItems) {
    this.nbItems = nbItems;
  }



  public static double getIncome() {
    return income;
  }

  public void sell(int nbItems)throws IllegalArgumentException{
    if(nbItems<this.nbItems) {
      this.setNbItems(this.nbItems - nbItems);
      income += nbItems * this.price;
      System.out.println("Sell OK");
    }
    else throw new IllegalArgumentException("Unavailable product");
  }

  public void purchase(int nbItems)throws IllegalArgumentException{
    if(nbItems>0){
      this.setNbItems(this.nbItems+nbItems);
      System.out.println("Purchase OK !");
    }else throw new IllegalArgumentException("Purchase with negative number !!");
  }
  @Override
  public String toString() {
    return  " ID : " + id +
            " | Name : " + name +
            " | Price : " + price +
            " | Quantity : " + nbItems;
  }

  @Override
  public int compareTo(Product o) {
    return Double.compare(this.getPrice(), o.getPrice());
  }
}
