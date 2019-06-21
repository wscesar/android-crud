package com.wscesar.crud.model;

public class Item {
    private int id;
    private String itemName;
    private int itemQuantity;
    private Float price;
    private String dateItemAdded;
    private String currency;

    public Item() {
    }


    /*
    public Item(String itemName, String currency, String dateItemAdded) {
        this.itemName = itemName;
        this.currency = currency;
        this.dateItemAdded = dateItemAdded;
    }

    public Item(String itemName, int itemQuantity, String dateItemAdded) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.dateItemAdded = dateItemAdded;
    }

    public Item(int id, String itemName, int itemQuantity, String dateItemAdded) {
        this.id = id;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.dateItemAdded = dateItemAdded;
    }
    */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }


    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
