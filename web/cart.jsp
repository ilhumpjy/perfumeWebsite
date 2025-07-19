<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.Perfume" %>
<%
    List<Perfume> cartItems = (List<Perfume>) request.getAttribute("cartItems");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart - Luxury Scents</title>
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
        .quantity-control { display: flex; align-items: center; gap: 10px; margin-top: 10px; }
        .quantity-control button { width: 30px; height: 30px; font-size: 18px; background-color: #eee; border: none; border-radius: 6px; cursor: pointer; transition: background 0.2s; }
        .quantity-control button:hover { background-color: #ddd; }
        .cart-right { display: flex; flex-direction: column; align-items: center; justify-content: center; }
        .cart-right input[type="checkbox"] { transform: scale(1.4); margin-bottom: 10px; }
        .remove-btn { padding: 6px 12px; background-color: #f44336; border: none; border-radius: 6px; color: white; cursor: pointer; }
        .checkout-section { display: flex; justify-content: flex-end; align-items: center; padding: 20px; background-color: white; border-radius: 10px; box-shadow: 0 2px 6px rgba(0,0,0,0.05); margin-top: 30px; }
        #total-amount { font-size: 18px; color: #b8955d; font-weight: bold; margin-right: 20px; }
        .btn-checkout { background-color: #b8955d; padding: 12px 24px; border: none; color: white; border-radius: 8px; font-weight: bold; cursor: pointer; transition: background-color 0.3s; }
        .btn-checkout:hover { background-color: #9f7a3a; }
        .error-msg { text-align: center; color: red; margin-bottom: 20px; font-weight: bold; }
        .empty-msg { text-align: center; font-size: 18px; color: #777; margin-top: 50px; }
        footer { text-align: center; padding: 20px; color: #aaa; font-size: 13px; margin-top: 60px; }
    </style>

    <script>
        function updateTotal() {
            let checkboxes = document.querySelectorAll('input[name="selectedItems"]:checked');
            let total = 0;
            checkboxes.forEach(cb => {
                let price = parseFloat(cb.getAttribute("data-price"));
                let perfumeId = cb.value;
                let qtyInput = document.getElementById("qty-" + perfumeId);
                let qty = parseInt(qtyInput.value);
                total += price * qty;
            });
            document.getElementById("total-amount").innerText = "RM " + total.toFixed(2);
        }

        function updateQuantity(perfumeId, change) {
            const qtyInput = document.getElementById("qty-" + perfumeId);
            const hiddenInput = document.getElementById("hidden-qty-" + perfumeId);
            let currentQty = parseInt(qtyInput.value);
            let newQty = currentQty + change;
            if (newQty < 1) return;
            qtyInput.value = newQty;
            hiddenInput.value = newQty;

            fetch('CartsQuantityServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'perfumeId=' + encodeURIComponent(perfumeId) + '&quantity=' + encodeURIComponent(newQty)
            })
            .then(response => response.text())
            .then(data => {
                if (data.includes("SUCCESS")) {
                    updateTotal();
                } else {
                    alert("Failed to update quantity: " + data);
                }
            })
            .catch(err => console.error("Error updating quantity:", err));
        }

        function removeFromCart(perfumeId) {
            if (!confirm("Are you sure you want to remove this item?")) return;
            fetch('CartRemoveServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'perfumeId=' + encodeURIComponent(perfumeId)
            })
            .then(response => response.text())
            .then(data => {
                if (data.includes("SUCCESS")) {
                    document.getElementById("item-" + perfumeId).remove();
                    updateTotal();
                } else {
                    alert("Failed to remove item: " + data);
                }
            })
            .catch(err => alert("Error: " + err));
        }

        window.onload = function() {
            updateTotal();
            let checkboxes = document.querySelectorAll('input[name="selectedItems"]');
            checkboxes.forEach(cb => {
                cb.addEventListener('change', updateTotal);
            });
        };
    </script>
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

<h1>Your Cart</h1>

<% if (error != null) { %>
    <p class="error-msg"><%= error %></p>
<% } %>

<div class="container">
<% if (cartItems != null && !cartItems.isEmpty()) { %>
<form action="CheckoutServlet" method="post">
<% for (Perfume p : cartItems) { %>
    <div class="cart-item" id="item-<%= p.getPerfumeId() %>">
        <div class="cart-left">
            <img src="<%= request.getContextPath() %>/<%= p.getImageUrl() %>" alt="Perfume Image">
            <div class="cart-info">
                <h3><%= p.getPerfumeName() %></h3>
                <p>Category: <%= p.getCategoryName() %></p>
                <p>Price: RM <%= String.format("%.2f", p.getPrice()) %></p>
                <div class="quantity-control">
                    <button type="button" onclick="updateQuantity(<%= p.getPerfumeId() %>, -1)">−</button>
                    <input type="text" id="qty-<%= p.getPerfumeId() %>" value="<%= p.getStock() %>" readonly style="width: 40px; text-align: center; border: none; background: transparent;" />
                    <button type="button" onclick="updateQuantity(<%= p.getPerfumeId() %>, 1)">+</button>
                </div>
            </div>
        </div>

        <div class="cart-right">
            <input type="checkbox" name="selectedItems" value="<%= p.getPerfumeId() %>" data-price="<%= p.getPrice() %>" checked>
            <input type="hidden" name="quantity-<%= p.getPerfumeId() %>" id="hidden-qty-<%= p.getPerfumeId() %>" value="<%= p.getStock() %>">
            <button type="button" class="remove-btn" onclick="removeFromCart(<%= p.getPerfumeId() %>)">Remove</button>
        </div>
    </div>
<% } %>

    <div class="checkout-section">
        <span id="total-amount">RM 0.00</span>
        <button type="submit" class="btn-checkout">Proceed to Checkout</button>
    </div>

</form>
<% } else { %>
    <p class="empty-msg">Your cart is currently empty.</p>
<% } %>
</div>

<footer>
    &copy; 2025 Luxury Scents. All rights reserved.
</footer>

</body>
</html>
