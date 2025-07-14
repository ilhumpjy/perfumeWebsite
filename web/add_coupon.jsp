<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Coupon</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            background-color: #f9f9f9;
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
        }

        .form-box {
            background-color: #ffffff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
            width: 100%;
            max-width: 400px;
        }

        .form-box h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
        }

        input[type="text"],
        input[type="number"],
        input[type="date"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        textarea {
            resize: vertical;
        }

        input[type="submit"] {
            background-color: #111;
            color: #fff;
            padding: 10px;
            width: 100%;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #444;
        }

        .back-button {
            margin-top: 20px;
            text-align: center;
        }

        .back-button a {
            text-decoration: none;
            background-color: #000;
            color: #fff;
            padding: 10px 20px;
            border-radius: 5px;
            font-weight: bold;
        }

        .back-button a:hover {
            background-color: #444;
        }
    </style>
</head>
<body>

    <form class="form-box" action="AddCouponServlet" method="post">
        <h2>Add New Coupon</h2>

        <input type="text" name="coupon_code" placeholder="Coupon Code" required>

        <textarea name="coupon_description" placeholder="Description" rows="3" required></textarea>

        <input type="number" name="discount_percentage" placeholder="Discount %" step="0.01" required>

        <label>Start Date:</label>
        <input type="date" name="start_date" required>

        <label>End Date:</label>
        <input type="date" name="end_date" required>

        <input type="submit" value="Add Coupon">
    </form>

    <div class="back-button">
        <a href="admin.jsp">Back to Dashboard</a>
    </div>

</body>
</html>
