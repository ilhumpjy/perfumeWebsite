package model;

public class CartItem {
    private int perfumeId;
    private String name;
    private String imageUrl;
    private double price;
    private int quantity;

    public CartItem(int perfumeId, String name, String imageUrl, double price, int quantity) {
        this.perfumeId = perfumeId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters & Setters
    public int getPerfumeId() { return perfumeId; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int q) { this.quantity = q; }
}
