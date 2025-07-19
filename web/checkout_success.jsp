<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String amountStr = (String) session.getAttribute("finalTotal");
    String discountStr = (String) session.getAttribute("discount");
    String method = (String) session.getAttribute("paymentMethod");
    Integer orderId = (Integer) session.getAttribute("orderId");

    Double amount = Double.parseDouble(amountStr);
    Double discount = Double.parseDouble(discountStr);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Success</title>
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        color: #333;
        margin: 0;
        padding: 0;
    }

    .container {
        max-width: 700px;
        margin: 80px auto;
        background-color: #ffffff;
        padding: 40px;
        border-radius: 12px;
        box-shadow: 0 6px 18px rgba(0, 0, 0, 0.1);
        text-align: center;
    }

    h1 {
        color: #b8955d;
        font-size: 32px;
        margin-bottom: 20px;
    }

    p {
        font-size: 18px;
        margin: 15px 0;
        color: #444;
    }

    strong {
        color: #222;
    }

    .btn {
        display: inline-block;
        margin-top: 30px;
        padding: 12px 24px;
        background-color: #b8955d;
        color: white;
        border: none;
        border-radius: 8px;
        text-decoration: none;
        font-weight: bold;
        transition: background-color 0.3s ease;
    }

    .btn:hover {
        background-color: #9f7a3a;
    }

    .highlight {
        color: green;
        font-weight: bold;
    }
</style>

</head>
<body>
    <div class="container">
        <h1>Thank You for Your Purchase!</h1>

        <% if (orderId != null) { %>
            <p>Your Order ID is <strong><%= orderId %></strong></p>
        <% } %>

        <p>Payment Method: <strong><%= method %></strong></p>

        <% if (discount > 0) { %>
            <p class="highlight">Discount Applied: RM <%= String.format("%.2f", discount) %></p>
        <% } %>

        <p>Total Paid: <strong>RM <%= String.format("%.2f", amount) %></strong></p>

        <a href="ShopServlet" class="btn">Continue Shopping</a>
    </div>
</body>
</html> 
