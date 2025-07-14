<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = (String) session.getAttribute("user_username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome - Luxury Scents</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600&family=Roboto:wght@300;400&display=swap');

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(to right, #fefaf5, #f9f4ec);
            color: #333;
        }

        /* NAVBAR */
        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #fffdf7;
            padding: 16px 40px;
            border-bottom: 1px solid #e8e3d7;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.03);
            position: sticky;
            top: 0;
            z-index: 100;
        }

        .navbar h1 {
            font-family: 'Playfair Display', serif;
            font-size: 24px;
            color: #b8955d;
            letter-spacing: 1px;
        }

        .nav-links {
            display: flex;
            gap: 25px;
        }

        .nav-links a {
            text-decoration: none;
            color: #444;
            font-size: 14px;
            padding: 4px 8px;
            transition: all 0.3s ease;
        }

        .nav-links a:hover {
            color: #b8955d;
            border-bottom: 2px solid #b8955d;
        }

        /* Dropdown */
        .dropdown {
            position: relative;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            top: 32px;
            left: 0;
            background-color: #fff;
            border: 1px solid #eee;
            min-width: 150px;
            box-shadow: 0 6px 18px rgba(0,0,0,0.06);
            z-index: 999;
            border-radius: 6px;
        }

        .dropdown-content a {
            display: block;
            padding: 10px 14px;
            color: #555;
            text-decoration: none;
            font-size: 13px;
        }

        .dropdown-content a:hover {
            background-color: #fef9f1;
            color: #b8955d;
        }

        .dropdown:hover .dropdown-content {
            display: block;
        }

        /* SLIDER */
        .slider {
            width: 100%;
            max-height: 300px;
            overflow: hidden;
            position: relative;
        }

        .slides {
            display: flex;
            width: 300%;
            animation: slide 15s infinite;
        }

        .slides img {
            width: 100%;
            height: 300px;
            object-fit: cover;
        }

        @keyframes slide {
            0% { margin-left: 0; }
            20% { margin-left: 0; }
            25% { margin-left: -100%; }
            45% { margin-left: -100%; }
            50% { margin-left: -200%; }
            70% { margin-left: -200%; }
            75% { margin-left: 0; }
            100% { margin-left: 0; }
        }

        /* Welcome Container */
        .welcome-container {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            min-height: 40vh;
            text-align: center;
            padding: 40px 20px;
        }

        .welcome-container h2 {
            font-family: 'Playfair Display', serif;
            font-size: 32px;
            color: #2c2c2c;
            margin-bottom: 10px;
        }

        .welcome-container h2::after {
            content: "";
            display: block;
            width: 50px;
            height: 2px;
            background: #b8955d;
            margin: 12px auto 0;
            border-radius: 2px;
        }

        .welcome-container p {
            font-size: 16px;
            color: #666;
            max-width: 600px;
            margin-top: 15px;
            line-height: 1.6;
        }

        footer {
            text-align: center;
            padding: 16px;
            color: #999;
            font-size: 13px;
            border-top: 1px solid #eee;
            background-color: #fffdf7;
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
        <a href="OrdersHistoryServlet">Order History</a>
        <a href="LogoutServlet">Logout</a>
    </div>
</div>

<!-- SLIDER -->
<div class="slider">
    <div class="slides">
        <img src="images/Perfume1.jpg" alt="Luxury Perfume 1">
        <img src="images/image2.jpg" alt="Luxury Perfume 2">
        <img src="images/image3.jpg" alt="Luxury Perfume 3">
    </div>
</div>

<!-- WELCOME MESSAGE -->
<div class="welcome-container">
    <h2>Welcome, <%= username %> 🌸</h2>
    <p>
        Discover timeless fragrances curated with elegance. Indulge in exclusive aromas and elevate your personal style with Luxury Scents.
    </p>
</div>

<!-- FOOTER -->
<footer>
    &copy; 2025 Luxury Scents. Curated with elegance, designed for you.
</footer>

</body>
</html>
