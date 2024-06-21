package org.example;
public abstract class Product {
    //Initializing instance variables
    protected String product_ID;
    protected String product_name;
    protected int number_of_items;
    protected double price;

    //Constructor
    public Product(String pro_id,String pro_name,int number_of_items,double price){
        this.product_ID = pro_id;
        this.product_name = pro_name;
        this.number_of_items = number_of_items;
        this.price = price;

    }

    public Product() {

    }

    //Setters
    public void setProductID(String pro_id){
        this.product_ID = pro_id;
    }

    public void setProductName(String name){
        this.product_name = name;
    }

    public void setNumberOfItems(int number_of_items){
        this.number_of_items = number_of_items;
    }

    public void setPrice(double price){
        this.price = price;
    }

    //Getters
    public String getProductID(){
        return product_ID;
    }

    public String getProductName(){
        return product_name;
    }

    public int getNumberOfItems(){
        return number_of_items;
    }

    public double getPrice(){
        return price;
    }

    //Abstract method to return the type

    public abstract String getType();

    //toString method
    @Override
    public String toString(){
        return "Product ID: " + product_ID + ", Product Name: " + product_name + ", Quantity: " + number_of_items + ", Price: " + String.format("%.2f",price)+"€";
    }
}
