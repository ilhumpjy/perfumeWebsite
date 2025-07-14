package model;

public class OrderItem {
    private String perfumeName;
    private double price;
    private int quantity;
    
     public OrderItem() {
        
    }

    public OrderItem(String perfumeName, double price, int quantity) {
        this.perfumeName = perfumeName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getPerfumeName() {
        return perfumeName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPerfumeName(String perfumeName) {
        this.perfumeName = perfumeName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
