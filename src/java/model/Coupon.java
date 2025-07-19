package model;

public class Coupon {
    private int couponId;
    private String couponCode;
    private String couponDescription;
    private double discountPercentage;
    private String startDate;
    private String endDate;
    private double totalPrice;

    // Constructor
    public Coupon() {}

    // Getters & Setters
    public int getCouponId() { return couponId; }
    public void setCouponId(int couponId) { this.couponId = couponId; }

    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }

    public String getCouponDescription() { return couponDescription; }
    public void setCouponDescription(String couponDescription) { this.couponDescription = couponDescription; }

    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
