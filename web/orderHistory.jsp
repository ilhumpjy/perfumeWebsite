<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.OrderBean, model.OrderItem" %>
<%
    List<OrderBean> orders = (List<OrderBean>) request.getAttribute("orders");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Order History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            padding: 30px;
        }

        h1 {
            color: #b8955d;
            margin-bottom: 20px;
        }

        .btn-home {
            display: inline-block;
            margin-bottom: 30px;
            padding: 10px 20px;
            background-color: #e91e63;
            color: white;
            text-decoration: none;
            font-weight: bold;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }

        .btn-home:hover {
            background-color: #d81b60;
        }

        .order-section {
            margin-bottom: 50px;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.08);
        }

        .order-header {
            font-size: 18px;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            padding: 10px;
            border: 1px solid #eee;
            text-align: left;
        }

        th {
            background-color: #f5f5f5;
            color: #444;
        }

        td {
            color: #555;
        }

        .total, .status {
            margin-top: 12px;
            font-weight: bold;
        }

        .coupon {
            font-style: italic;
            color: #777;
            margin-top: 5px;
        }
    </style>
</head>
<body>

<h1>Your Order History</h1>

<a href="dashboard.jsp" class="btn-home">🏠 Back to Home</a>

<% if (orders != null && !orders.isEmpty()) {
    for (OrderBean o : orders) { %>
        <div class="order-section">
            <div class="order-header">Order #<%= o.getId() %> — <%= o.getDate() %></div>

            <% if (o.getCouponCode() != null) { %>
                <div class="coupon">Coupon Used: <%= o.getCouponCode() %></div>
            <% } %>

            <table>
                <thead>
                    <tr>
                        <th>Perfume</th>
                        <th>Quantity</th>
                        <th>Price (RM)</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (OrderItem item : o.getItems()) { %>
                        <tr>
                            <td><%= item.getPerfumeName() %></td>
                            <td><%= item.getQuantity() %></td>
                            <td><%= String.format("%.2f", item.getPrice()) %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <p class="total">Total: RM <%= String.format("%.2f", o.getAmount()) %></p>
            <p class="status">Status: <%= o.getStatus() %></p>
        </div>
<%  }
} else { %>
    <p>No orders found.</p>
<% } %>

</body>
</html>
