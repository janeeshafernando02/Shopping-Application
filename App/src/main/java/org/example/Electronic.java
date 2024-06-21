package org.example;

public class Electronic extends Product{
    //Initializing Instance Variables
    private String brand_name;
    private int warranty_period;

    //Constructors
    public Electronic(String pro_id,String pro_name,int no_of_items,double price,String brand_name,int warranty_period){
        super(pro_id,pro_name,no_of_items,price);
        this.brand_name = brand_name;
        this.warranty_period = warranty_period;
    }

    public Electronic() {

    }

    // Setters
    public void setBrandName(String brand){
        this.brand_name = brand;
    }

    public void setWarranty(int warranty){
        this.warranty_period = warranty;
    }

    // Getters
    public String getBrandName(){
        return brand_name;
    }

    public int getWarrantyPeriod(){
        return warranty_period;
    }

    //Abstract method to get the type
    @Override
    public String getType(){
        return "Electronics";
    }

    //toString method
    @Override
    public String toString(){
        return "Type: " + getType() + ", " + super.toString() + ", Brand name: " + brand_name + ", Warranty Period: " + warranty_period;
    }
}
