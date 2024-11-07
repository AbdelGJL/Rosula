package com.example.womenshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Controller implements Initializable {

    DBManager manager;

    static double capital = 20000;

    static double costs = 0;

    static double income = 0;

    @FXML
    private Button add;

    @FXML
    private ToggleGroup buttons;

    @FXML
    private Button buy;

    @FXML
    private Button delete;

    @FXML
    private ToggleButton discount_no;

    @FXML
    private ToggleButton discount_yes;

    @FXML
    private TextArea infos;

    @FXML
    private Button modify;

    @FXML
    private TextField name;

    @FXML
    private TextField name_sp;

    @FXML
    private TextField p_price;

    @FXML
    private TextField price;

    @FXML
    private ListView<Product> product_list = new ListView<>();;

    @FXML
    private TextField quantity;

    @FXML
    private TextArea id;

    @FXML
    private TextArea n_price;

    @FXML
    private TextField quantity_sp;

    @FXML
    private Button sell;

    @FXML
    private TextField size;

    @FXML
    private Text text;

    @FXML
    private ComboBox<String> type;

    @FXML
    void togglebutton(ActionEvent event) {
        if(event.getSource() == discount_yes){
            text.setText("Discount Activated");
            switch(type.getValue()){
                case "Clothes":
                    double new_c = Clothes.applyDiscount(Double.parseDouble(price.getText()));
                    manager.modifyPrice(new_c, Integer.parseInt(id.getText()));
                    n_price.setText(String.valueOf(new_c));
                    break;
                case "Accessories":
                    double new_a = Accessories.applyDiscount(Double.parseDouble(price.getText()));
                    manager.modifyPrice(new_a, Integer.parseInt(id.getText()));
                    n_price.setText(String.valueOf(new_a));
                    break;
                case "Shoes":
                    double new_s = Shoes.applyDiscount(Double.parseDouble(price.getText()));
                    manager.modifyPrice(new_s, Integer.parseInt(id.getText()));
                    n_price.setText(String.valueOf(new_s));
                    break;
            }

            product_list.getSelectionModel().clearSelection();

            fetchProducts();


        }
        else if(event.getSource() == discount_no){
            text.setText("Discount Deactivated");
            switch(type.getValue()){
                case "Clothes":
                    double new_c = Clothes.disableDiscount(Double.parseDouble(price.getText()));
                    manager.modifyPrice(new_c, Integer.parseInt(id.getText()));
                    n_price.setText(String.valueOf(new_c));
                    break;
                case "Accessories":
                    double new_a = Accessories.disableDiscount(Double.parseDouble(price.getText()));
                    manager.modifyPrice(new_a, Integer.parseInt(id.getText()));
                    n_price.setText(String.valueOf(new_a));
                    break;
                case "Shoes":
                    double new_s = Shoes.disableDiscount(Double.parseDouble(price.getText()));
                    manager.modifyPrice(new_s, Integer.parseInt(id.getText()));
                    n_price.setText(String.valueOf(new_s));
                    break;
            }

            product_list.getSelectionModel().clearSelection();

            fetchProducts();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new DBManager();
        id.setEditable(false); // empêche d'écrire dans la case id
        n_price.setEditable(false); //idem
        infos.setEditable(false); //idem
        List<String> gvalues = new ArrayList<String>();
        gvalues.add("Shoes");
        gvalues.add("Clothes");
        gvalues.add("Accessories");
        ObservableList<String> gender = FXCollections.observableArrayList(gvalues);
        type.setItems(gender);


        infos.setText("Capital : " + capital
                + "\n\n\nCosts : " + costs +
                "\n\n\nIncomes : " + income);
        product_list.getSelectionModel().selectedItemProperty().addListener(e-> displayProductDetails(product_list.getSelectionModel().getSelectedItem()));


        fetchProducts();

    }
    private void displayProductDetails(Product p) {
        if(p!=null){
            name_sp.setText(p.getName());
            name.setText(p.getName());
            price.setText(String.valueOf(p.getPrice()));
            quantity.setText(String.valueOf(p.getNbItems()));
            id.setText(String.valueOf(p.getId()));
            n_price.setText(null);


            if (p instanceof Clothes) {
                Clothes c = (Clothes) p;
                size.setText(String.valueOf(c.getSize()));
                type.setValue("Clothes");
            }
            else if(p instanceof Shoes){
                Shoes s = (Shoes) p;
                size.setText(String.valueOf(s.getSize()));
                type.setValue("Shoes");
            }
            else{
                size.setText("NULL");
                type.setValue("Accessories");
            }

        }
    }

    public void fetchProducts() {
        List<Product> listProducts = manager.loadProducts();
        infos.setText("Capital : " + capital
                + "\n\n\nCosts : " + costs +
                "\n\n\nIncomes : " + income);
        if (listProducts != null) {
            ObservableList<Product> products = FXCollections.observableArrayList(listProducts);

            // Clear the existing items in the ListView
            product_list.getItems().clear();

            // Add the new items to the ListView
            product_list.setItems(products);
        }
    }


    public void onAdd() {
        try {
            if (type.getValue() == null) {
                throw new IllegalArgumentException("Please select a product type ! Otherwise I can't add your product :p");
            }
            switch (type.getValue()) {
                case "Clothes":
                    Product s = new Clothes(name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(quantity.getText()), Integer.parseInt(size.getText()));
                    manager.addProduct(s, "Clothes");
                    break;

                case "Accessories":
                    Product q = new Accessories(name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(quantity.getText()));
                    manager.addProduct(q, "Accessories");
                    break;

                case "Shoes":
                    Product l = new Shoes(name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(quantity.getText()), Integer.parseInt(size.getText()));
                    manager.addProduct(l, "Shoes");
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect choice");

            }

            product_list.getSelectionModel().clearSelection();
            this.name.setText(null);
            this.size.setText(null);
            this.price.setText(null);
            this.quantity.setText(null);
            this.type.setValue(null);
            this.name_sp.setText(null);
            this.id.setText(null);

            fetchProducts();
        } catch (NumberFormatException e) {
            showErrorPopup("Don't forget to complete all the data input.");
        } catch (IllegalArgumentException e) {
            showErrorPopup(e.getMessage());
        }
    }


    public void onDelete(){
        manager.deleteProduct(Integer.parseInt(id.getText()));
        product_list.getSelectionModel().clearSelection();
        fetchProducts();

        this.name.setText(null);
        this.size.setText(null);
        this.price.setText(null);
        this.quantity.setText(null);
        this.type.setValue(null);
        this.name_sp.setText(null);
        this.id.setText(null);
    }


    public void onModify() {
        try {
            if (type.getValue() == null) {
                throw new IllegalArgumentException("Please select a product type");
            }

            switch (type.getValue()) {
                case "Clothes":
                    Product s = new Clothes(name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(quantity.getText()), Integer.parseInt(size.getText()));
                    manager.modifyProduct(s, Integer.parseInt(id.getText()), "Clothes");
                    break;

                case "Accessories":
                    Product q = new Accessories(name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(quantity.getText()));
                    manager.modifyProduct(q, Integer.parseInt(id.getText()), "Accessories");
                    break;

                case "Shoes":
                    Product l = new Shoes(name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(quantity.getText()), Integer.parseInt(size.getText()));
                    manager.modifyProduct(l, Integer.parseInt(id.getText()), "Shoes");
                    break;

                default:
                    throw new IllegalArgumentException("Incorrect choice");
            }

            product_list.getSelectionModel().clearSelection();
            this.name.setText(null);
            this.size.setText(null);
            this.price.setText(null);
            this.quantity.setText(null);
            this.type.setValue(null);
            this.name_sp.setText(null);
            this.id.setText(null);

            fetchProducts();

        } catch (NumberFormatException e) {
            showErrorPopup("Invalid input. Verify that you have enter all the data needed and select the good type !");
        } catch (IllegalArgumentException e) {
            showErrorPopup(e.getMessage());
        }
    }




    public void onSell() {
        try {

            int quantity_selled = Integer.parseInt(quantity_sp.getText());
            int quantity_product = Integer.parseInt(quantity.getText());

            if (quantity_selled <= quantity_product) {
                manager.modifyQuantity(quantity_product - quantity_selled, Integer.parseInt(id.getText()));
                income += quantity_selled * Double.parseDouble(price.getText());
                capital += quantity_selled * Double.parseDouble(price.getText());
                showSuccessAlert("Selling confirmed");
            } else {
                throw new IllegalArgumentException("You don't have enough quantity to sell. Don't bite off more than you can chew!");
            }

            product_list.getSelectionModel().clearSelection();
            this.name.setText(null);
            this.size.setText(null);
            this.price.setText(null);
            this.quantity.setText(null);
            this.type.setValue(null);
            this.name_sp.setText(null);
            this.id.setText(null);
            this.p_price.setText(null);
            this.quantity_sp.setText(null);
            fetchProducts();
        } catch (NumberFormatException e) {
            showErrorPopup("Invalid input. Please enter valid numeric values.");
        } catch (IllegalArgumentException e) {
            showErrorPopup(e.getMessage());
        }
    }


    public void onPurchase() {
        try {
            if (Double.parseDouble(p_price.getText()) <= 0) {
                throw new IllegalArgumentException("You can't purchase a product with a negative price !");
            }
            int quantity_purchased = Integer.parseInt(quantity_sp.getText());
            int quantity_product = Integer.parseInt(quantity.getText());

            if (quantity_purchased > 0) {
                manager.modifyQuantity(quantity_product + quantity_purchased, Integer.parseInt(id.getText()));
                costs += quantity_purchased * Double.parseDouble(p_price.getText());
                capital -= quantity_purchased * Double.parseDouble(p_price.getText());
                showSuccessAlert("Purchase successful");
            } else {
                throw new IllegalArgumentException("You can't purchase 0 or less than 0 product !");
            }

            product_list.getSelectionModel().clearSelection();
            this.name.setText(null);
            this.size.setText(null);
            this.price.setText(null);
            this.quantity.setText(null);
            this.type.setValue(null);
            this.name_sp.setText(null);
            this.p_price.setText(null);
            this.id.setText(null);
            this.quantity_sp.setText(null);

            fetchProducts();
        } catch (NumberFormatException e) {
            showErrorPopup("Invalid input. Please enter valid numeric values.");
        } catch (IllegalArgumentException e) {
            showErrorPopup(e.getMessage());
        }
    }

    public static void showErrorPopup(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Hold Up, Wait a Minute !");
        alert.setContentText(message);


        Image errorIcon = new Image(Controller.class.getResourceAsStream("rose.png"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(errorIcon);



        alert.showAndWait();
    }

    public static void showSuccessAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Image Icon = new Image(Controller.class.getResourceAsStream("rose.png"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(Icon);

        alert.showAndWait();
    }




}
