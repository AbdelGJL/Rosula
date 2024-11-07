package com.example.womenshop;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import static java.sql.Types.NULL;

public class DBManager {
    public List<Product> loadProducts(){
        List<Product> productAll = new ArrayList<Product>();
        Connection myConn= this.Connector();
        try {
            Statement myStmt= myConn.createStatement();
            String sql = "select * from product";
            ResultSet myRs= myStmt.executeQuery(sql);
            while (myRs.next()) {
                switch(myRs.getString("type")){

                    case "Clothes":
                        Product s = new Clothes(myRs.getInt("idproduct"), myRs.getString("nom"), myRs.getDouble("prix"), myRs.getInt("quantite"), myRs.getInt("taille"));
                        productAll.add(s);
                        break;

                    case "Accessories":
                        Product q = new Accessories(myRs.getInt("idproduct"),myRs.getString("nom"), myRs.getDouble("prix"), myRs.getInt("quantite"));
                        productAll.add(q);
                        break;

                    case "Shoes":
                        Product l = new Shoes(myRs.getInt("idproduct"),myRs.getString("nom"), myRs.getDouble("prix"), myRs.getInt("quantite"), myRs.getInt("taille"));
                        productAll.add(l);
                        break;
                    default:
                        System.out.println("Choix incorrect");
                        break;
                }

            }
            this.close(myConn, myStmt, myRs);
            return productAll;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }
    public Connection Connector(){
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/shopwomen?serverTimezone=Europe%2FParis", "root","Abdeljijel18!");
            return connection;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if(myStmt!=null)
                myStmt.close();
            if(myRs!=null)
                myRs.close();
            if(myConn!=null)
                myConn.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void addProduct(Product product, String type){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            String sql = "INSERT INTO product (type, nom, quantite, prix, taille) VALUES (?, ?, ?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, type);
            myStmt.setString(2, product.getName());
            myStmt.setInt(3, product.getNbItems());
            myStmt.setDouble(4, product.getPrice());
            if (type == "Shoes") {
                Shoes shoes = (Shoes) product;
                myStmt.setInt(5, shoes.getSize());
            }
            else if(type == "Clothes"){
                Clothes clothes = (Clothes) product;
                myStmt.setInt(5, clothes.getSize());
            }
            else{
                myStmt.setInt(5, NULL);
            }



            myStmt.execute();
            Controller.showSuccessAlert("Product Added successfully, you can see it on the Inventory !");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }

    public void deleteProduct(int i){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;

        try {
            myConn = this.Connector();
            String sql = "DELETE FROM product WHERE idproduct = ? ";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, i);


            myStmt.execute();
            Controller.showSuccessAlert("Product Deleted");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }

    }

    public void modifyProduct(Product product, int id, String type){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            String sql = "UPDATE product SET type = ?, nom = ?, quantite = ?, prix = ?, taille = ? WHERE idproduct = ?";
            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, type);
            myStmt.setString(2, product.getName());
            myStmt.setInt(3, product.getNbItems());
            myStmt.setDouble(4, product.getPrice());
            if (type == "Shoes") {
                Shoes shoes = (Shoes) product;
                myStmt.setInt(5, shoes.getSize());
            }
            else if(type == "Clothes"){
                Clothes clothes = (Clothes) product;
                myStmt.setInt(5, clothes.getSize());
            }
            else{
                myStmt.setInt(5, NULL);
            }
            myStmt.setInt(6, id);



            myStmt.execute();
            Controller.showSuccessAlert("Product modified successfully ! See the modifications on your Inventory !");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }

    public void modifyQuantity(int quantity, int id){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            String sql = "UPDATE product SET quantite = ? WHERE idproduct = ?";
            myStmt = myConn.prepareStatement(sql);


            myStmt.setInt(1, quantity);

            myStmt.setInt(2, id);



            myStmt.execute();
            //Controller.showSuccessAlert("Quantity updated !");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }

    public void modifyPrice(double price, int id){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            String sql = "UPDATE product SET prix = ? WHERE idproduct = ?";
            myStmt = myConn.prepareStatement(sql);


            myStmt.setDouble(1, price);

            myStmt.setInt(2, id);



            myStmt.execute();
            Controller.showSuccessAlert("Price modified !");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }




}
