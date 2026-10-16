package com.example.smartpantry;
public class PantryItem {
    public int id; public String name,unit,expiry; public double quantity;
    public PantryItem(int id,String name,double quantity,String unit,String expiry){
        this.id=id;this.name=name;this.quantity=quantity;this.unit=unit;this.expiry=expiry;
    }
}
