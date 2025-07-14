package model;

import java.sql.Date;
import java.util.List;

public class OrderBean {
    private int id;
    private Date date;
    private double amount;
    private String status;
    private String couponCode;
    private List<OrderItem> items;
    
    public OrderBean() {
    }

    public OrderBean(int id, Date date, double amount, String status, String couponCode, List<OrderItem> items) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.status = status;
        this.couponCode = couponCode;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    
}
