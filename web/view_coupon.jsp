<%@ page import="java.util.*, model.Coupon" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Coupons</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff;
            color: #000;
            margin: 40px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 90%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        th, td {
            padding: 12px;
            border: 1px solid black;
            text-align: center;
        }

        th {
            background-color: #000;
            color: white;
        }

        input[type="submit"] {
            padding: 6px 12px;
            border: none;
            background-color: black;
            color: white;
            cursor: pointer;
            border-radius: 4px;
        }

        input[type="submit"]:hover {
            background-color: #333;
        }

        .back-button {
            text-align: center;
            margin-top: 10px;
        }

        .back-button a {
            text-decoration: none;
            background-color: black;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            font-weight: bold;
        }

        .back-button a:hover {
            background-color: #333;
        }
    </style>
</head>
<body>

    <h2>All Coupons</h2>

    <%
        List<Coupon> coupons = (List<Coupon>) request.getAttribute("coupons");
        if (coupons != null && !coupons.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Code</th>
                <th>Description</th>
                <th>Discount %</th>
                <th>Min Total Price (RM)</th>
                <th>Start</th>
                <th>End</th>
                <th>Action</th>
            </tr>
            <%
                for (Coupon coupon : coupons) {
            %>
            <tr>
                <td><%= coupon.getCouponCode() %></td>
                <td><%= coupon.getCouponDescription() %></td>
                <td><%= coupon.getDiscountPercentage() %></td>
                <td><%= String.format("%.2f", coupon.getTotalPrice()) %></td>
                <td><%= coupon.getStartDate() %></td>
                <td><%= coupon.getEndDate() %></td>
                <td>
                    <form method="post" action="DeleteCouponServlet">
                        <input type="hidden" name="coupon_id" value="<%= coupon.getCouponId() %>">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>No coupons found.</p>
    <% } %>

    <div class="back-button">
        <a href="admin.jsp">Back to Dashboard</a>
    </div>

</body>
</html>
