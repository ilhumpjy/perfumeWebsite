<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.Perfume" %>
<%
    List<Perfume> cartItems = (List<Perfume>) request.getAttribute("cartItems");
    Map<Integer, Integer> quantityMap = (Map<Integer, Integer>) request.getAttribute("quantityMap");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout - Luxury Scents</title>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600&family=Roboto:wght@300;400&display=swap');

    * { box-sizing: border-box; margin: 0; padding: 0; }

    body {
        font-family: 'Roboto', sans-serif;
        background-color: #fefefe;
        color: #333;
    }

    .navbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        background-color: #fff;
        padding: 20px 60px;
        border-bottom: 1px solid #ddd;
        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
        position: sticky;
        top: 0;
        z-index: 100;
    }

    .navbar h1 {
        font-family: 'Playfair Display', serif;
        font-size: 26px;
        color: #b8955d;
    }

    .nav-links {
        display: flex;
        gap: 30px;
    }

    .nav-links a {
        text-decoration: none;
        color: #444;
        font-size: 15px;
        padding: 4px 8px;
        transition: color 0.3s ease;
    }

    .nav-links a:hover {
        color: #b8955d;
    }

    .dropdown {
        position: relative;
    }

    .dropdown-content {
        display: none;
        position: absolute;
        top: 28px;
        left: 0;
        background-color: #fff;
        border: 1px solid #eee;
        min-width: 160px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.05);
        z-index: 999;
    }

    .dropdown-content a {
        display: block;
        padding: 10px 15px;
        color: #444;
        text-decoration: none;
    }

    .dropdown-content a:hover {
        background-color: #f9f9f9;
        color: #b8955d;
    }

    .dropdown:hover .dropdown-content {
        display: block;
    }

    .page-title {
        text-align: center;
        font-family: 'Playfair Display', serif;
        font-size: 32px;
        margin: 40px 0 20px;
        color: #b8955d;
    }

    .cart-container {
        max-width: 950px;
        margin: 0 auto;
        display: flex;
        flex-direction: column;
        gap: 20px;
    }

    .cart-item {
        display: flex;
        align-items: flex-start;
        background-color: #fff;
        border: 1px solid #eee;
        border-radius: 10px;
        padding: 10px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.03);
    }

    .cart-item img {
        width: 160px;
        height: 160px;
        object-fit: cover;
    }

    .cart-details {
        flex: 1;
        padding: 20px;
    }

    .cart-details h3 {
        font-family: 'Playfair Display', serif;
        font-size: 20px;
        margin-bottom: 10px;
    }

    .cart-details p {
        font-size: 14px;
        color: #555;
        margin: 4px 0;
    }

    .grand-total {
        max-width: 950px;
        margin: 20px auto;
        text-align: right;
        font-size: 20px;
        font-weight: bold;
    }

    .form-section {
        max-width: 950px;
        margin: 20px auto;
        background-color: #fff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.05);
    }

    .form-section label {
        font-weight: bold;
        display: block;
        margin: 10px 0 5px;
    }

    .form-section select,
    .form-section input[type="checkbox"] {
        margin-top: 5px;
    }

    .checkout-container {
        text-align: right;
        margin-top: 20px;
    }

    .place-order-btn {
        background-color: #b8955d;
        color: #fff;
        padding: 10px 25px;
        border: none;
        border-radius: 8px;
        font-size: 15px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .place-order-btn:hover {
        background-color: #9f7a3a;
    }

    .empty {
        text-align: center;
        font-size: 18px;
        color: #777;
        margin: 60px 0;
    }

    footer {
        text-align: center;
        padding: 20px;
        font-size: 13px;
        color: #aaa;
    }
</style>

             
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <h1>Luxury Scents</h1>
    <div class="nav-links">
        <a href="dashboard.jsp">Home</a>
        <a href="ShopServlet">Shop</a>
        <a href="CartServlet">Cart</a>
        <a href="LogoutServlet">Logout</a>
        </div>
       
    </div>
</div>

<h2 class="page-title">Checkout Summary</h2>

<% if (cartItems != null && !cartItems.isEmpty()) { %>
<form method="post" action="PaymentServlet">
    <div class="cart-container">
        <%
            double grandTotal = 0;
            for (Perfume p : cartItems) {
                int quantity = quantityMap.get(p.getPerfumeId());
                double total = quantity * p.getPrice();
                grandTotal += total;
        %>
        <div class="cart-item">
            <img src="images/<%= p.getImageUrl() %>" alt="Perfume">
            <div class="cart-details">
                <h3><%= p.getPerfumeName() %></h3>
                <p>Category: <%= p.getCategoryName() %></p>
                <p>Unit Price: RM <%= String.format("%.2f", p.getPrice()) %></p>
                <p>Quantity: <%= quantity %></p>
                <p>Total: RM <%= String.format("%.2f", total) %></p>
                <input type="hidden" name="perfumeId" value="<%= p.getPerfumeId() %>">
                <input type="hidden" name="quantity_<%= p.getPerfumeId() %>" value="<%= quantity %>">
            </div>
        </div>
        <% } %>
    </div>

    <div class="grand-total">
        Grand Total: RM <%= String.format("%.2f", grandTotal) %>
    </div>

    <div class="form-section">
        <label><input type="checkbox" name="applyCoupon"> Apply 10% Discount (Min. 3 items)</label>

        <label for="method">Payment Method:</label>
        <select name="method" id="method" required>
            <option value="">-- Select Method --</option>
            <option value="Credit Card">Credit Card</option>
            <option value="Online Banking">Online Banking</option>
            <option value="Cash on Delivery">Cash on Delivery</option>
        </select>

        <div class="checkout-container">
            <button type="submit" class="place-order-btn">Place Order</button>
        </div>
    </div>
</form>
<% } else { %>
    <div class="empty">No items selected for checkout.</div>
<% } %>

<footer>
    &copy; 2025 Luxury Scents. All rights reserved.
</footer>

</body>
</html>
