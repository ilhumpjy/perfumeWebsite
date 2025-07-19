<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.Perfume" %>
<%
    List<Perfume> cartItems = (List<Perfume>) session.getAttribute("cartItems");
    Map<Integer, Integer> quantityMap = (Map<Integer, Integer>) session.getAttribute("quantityMap");

    if (cartItems == null || quantityMap == null) {
        response.sendRedirect("CartServlet");
        return;
    }

    String errorMsg = (String) session.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout - Luxury Scents</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600&family=Roboto:wght@300;400&display=swap');

        body { font-family: 'Roboto', sans-serif; background-color: #fff; color: #333; margin: 0; padding: 0; }
        .navbar { display: flex; justify-content: space-between; align-items: center; background-color: #fff; padding: 20px 60px; border-bottom: 1px solid #ddd; box-shadow: 0 4px 6px rgba(0,0,0,0.05); position: sticky; top: 0; z-index: 100; }
        .navbar h1 { font-family: 'Playfair Display', serif; font-size: 26px; color: #b8955d; }
        .nav-links { display: flex; gap: 30px; }
        .nav-links a { text-decoration: none; color: #444; font-size: 15px; padding: 4px 8px; transition: color 0.3s ease; }
        .nav-links a:hover { color: #b8955d; }

        h1 { text-align: center; margin: 40px 0 20px; font-family: 'Playfair Display', serif; font-size: 34px; color: #b8955d; }
        .container { max-width: 950px; margin: auto; padding: 20px; }

        .cart-item { display: flex; align-items: flex-start; justify-content: space-between; background-color: #fff; border: 1px solid #eee; border-radius: 10px; margin-bottom: 20px; padding: 15px; box-shadow: 0 2px 6px rgba(0, 0, 0, 0.03); }
        .cart-left { display: flex; align-items: flex-start; }
        .cart-item img { width: 120px; height: 120px; object-fit: cover; border-radius: 6px; margin-right: 20px; }
        .cart-info { }
        .cart-info h3 { font-family: 'Playfair Display', serif; font-size: 20px; color: #222; margin-bottom: 8px; }
        .cart-info p { font-size: 14px; color: #555; margin: 4px 0; }

        .checkout-summary { background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 6px rgba(0,0,0,0.05); margin-top: 30px; }
        .checkout-summary label { display: block; margin-top: 10px; font-weight: bold; }
        .checkout-summary input[type="text"], .checkout-summary select { width: 100%; padding: 8px; margin-top: 5px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 6px; }

        .checkout-footer { display: flex; justify-content: space-between; align-items: center; margin-top: 20px; }
        .total { font-size: 18px; color: #b8955d; font-weight: bold; }

        .btn-place { background-color: #b8955d; padding: 12px 24px; border: none; color: white; border-radius: 8px; font-weight: bold; cursor: pointer; transition: background-color 0.3s; }
        .btn-place:hover { background-color: #9f7a3a; }

        .error-message { background-color: #ffe6e6; color: #d8000c; padding: 10px; border-radius: 5px; margin-bottom: 15px; text-align: center; }

        footer { text-align: center; padding: 20px; color: #aaa; font-size: 13px; margin-top: 60px; }
    </style>
</head>
<body>

<div class="navbar">
    <h1>Luxury Scents</h1>
    <div class="nav-links">
        <a href="dashboard.jsp">Home</a>
        <a href="ShopServlet">Shop</a>
        <a href="CartServlet">Cart</a>
        <a href="LogoutServlet">Logout</a>
    </div>
</div>

<h1>Checkout</h1>

<div class="container">

<% if (errorMsg != null) { %>
    <div class="error-message">
        <strong>Error:</strong> <%= errorMsg %>
    </div>
<%
    session.removeAttribute("error");
} 
%>

<form action="PaymentServlet" method="post">
<%
    double grandTotal = 0;
    int totalItems = 0;
    for (Perfume p : cartItems) {
        int qty = quantityMap.get(p.getPerfumeId());
        double subtotal = qty * p.getPrice();
        grandTotal += subtotal;
        totalItems += qty;
%>
    <div class="cart-item">
        <div class="cart-left">
            <img src="<%= request.getContextPath() %>/<%= p.getImageUrl() %>" alt="Perfume Image">
            <div class="cart-info">
                <h3><%= p.getPerfumeName() %></h3>
                <p>Category: <%= p.getCategoryName() %></p>
                <p>Unit Price: RM <%= String.format("%.2f", p.getPrice()) %></p>
                <p>Quantity: <%= qty %></p>
                <p>Subtotal: RM <%= String.format("%.2f", subtotal) %></p>
                <input type="hidden" name="quantity_<%= p.getPerfumeId() %>" value="<%= qty %>">
            </div>
        </div>
    </div>
<% } %>

    <div class="checkout-summary">
        <label>Coupon Code:</label>
        <input type="text" name="couponCode" placeholder="Enter coupon (optional)">

        <label>Payment Method:</label>
        <select name="method" required>
            <option value="">--Select--</option>
            <option value="Credit Card">Credit Card</option>
            <option value="Online Banking">Online Banking</option>
            <option value="Cash on Delivery">Cash on Delivery</option>
        </select>

        <div class="checkout-footer">
            <span class="total">Grand Total: RM <%= String.format("%.2f", grandTotal) %></span>
            <button type="submit" class="btn-place">Place Order</button>
        </div>
    </div>

    <input type="hidden" name="totalItems" value="<%= totalItems %>">

</form>

</div>

<footer>
    &copy; 2025 Luxury Scents. All rights reserved.
</footer>

</body>
</html>
