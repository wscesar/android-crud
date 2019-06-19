package com.bawp.babyneeds.model;

public class Item {
    private int id;
    private String itemName;
    private int itemQuantity;
    private String dateItemAdded;

    public Item() {
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

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }
}
