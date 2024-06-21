package org.example;

public class Clothing extends Product{
    //Initializing Instance Variables
    private String size;
    private String color;

    // Constructors
    public Clothing(String pro_id,String pro_name,int no_of_items,double price,String size,String color){
        super(pro_id,pro_name,no_of_items,price);
        this.size = size;
        this.color = color;
        String type = "Clothing";
    }

    public Clothing(){

    }

    //Setters
    public void setSize(String size){
        this.size = size;
    }

    public void setColor(String color){
        this.color = color;
    }

    //Getters
    public String getSize(){
        return size;
    }
    public String getColor(){
        return color;
    }

    @Override
    public String getType(){
        return "Clothing";
    }

    @Override
    public String toString(){
        return "Type: " + getType() + ", " + super.toString() + ", Size: " + size + ", Color: " + color;
    }

}
