<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.Perfume" %>
<%
    List<Perfume> perfumeList = (List<Perfume>) request.getAttribute("perfumeList");
    String selectedCategory = (String) request.getAttribute("selectedCategory");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Shop - Luxury Scents</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600&family=Roboto:wght@300;400&display=swap');

        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Roboto', sans-serif; background-color: #fefefe; color: #b8955d; }

        .navbar {
            display: flex; justify-content: space-between; align-items: center;
            background-color: #fff; padding: 20px 60px;
            border-bottom: 1px solid #ddd; box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            position: sticky; top: 0; z-index: 100;
        }

        .navbar h1 {
            font-family: 'Playfair Display', serif;
            font-size: 26px; color: #b8955d;
        }

        .nav-links { display: flex; gap: 30px; }
        .nav-links a {
            text-decoration: none; color: #444; font-size: 15px; padding: 4px 8px;
            transition: color 0.3s;
        }
        .nav-links a:hover { color: #b8955d; }

        .page-title {
            text-align: center;
            font-family: 'Playfair Display', serif;
            font-size: 32px;
            margin: 40px 0 20px;
            color: #222;
        }

        .filter-buttons {
            text-align: center;
            margin: 30px 0 20px;
        }

        .filter-buttons form {
            display: inline-block;
            margin: 0 10px;
        }

        .filter-buttons button {
            background-color: #b8955d;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }

        .filter-buttons button:hover {
            background-color: #9f7a3a;
        }

        .container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 30px;
            padding: 0 60px 80px;
        }

        .card {
            border: 1px solid #eee;
            border-radius: 12px;
            padding: 15px;
            text-align: center;
            background-color: #fff;
            box-shadow: 0 2px 10px rgba(0,0,0,0.03);
            transition: transform 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 15px rgba(0,0,0,0.08);
        }

        .card img {
            max-width: 100%;
            height: 180px;
            object-fit: cover;
            border-radius: 8px;
        }

        .card h3 { margin: 12px 0 5px; font-size: 18px; font-family: 'Playfair Display', serif; }
        .card p { margin: 5px 0; font-size: 14px; color: #555; }
        .price { color: #b8955d; font-weight: bold; margin-top: 10px; }

        .btn {
            margin-top: 15px;
            padding: 10px 20px;
            background-color: #b8955d;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn:hover { background-color: #9f7a3a; }

        footer {
            text-align: center;
            padding: 30px 0;
            font-size: 14px;
            color: #aaa;
        }

        #loadingOverlay {
            display: none;
            position: fixed;
            top: 0; left: 0; width: 100%; height: 100%;
            background-color: rgba(255,255,255,0.8);
            z-index: 9999;
            justify-content: center;
            align-items: center;
        }

        .spinner {
            border: 8px solid #eee;
            border-top: 8px solid #b8955d;
            border-radius: 50%;
            width: 60px;
            height: 60px;
            animation: spin 1s linear infinite;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>
<body>

<div id="loadingOverlay"><div class="spinner"></div></div>

<div class="navbar">
    <h1>Luxury Scents</h1>
    <div class="nav-links">
        <a href="CustomerDashboardServlet">Home</a>
        <a href="ShopServlet">Shop</a>
        <a href="CartServlet">Cart</a>
        <a href="OrdersHistoryServlet">Order History</a>
        <a href="LogoutServlet">Logout</a>
    </div>
</div>

<h2 class="page-title">
    <%= selectedCategory != null ? selectedCategory + " Perfumes" : "Explore Our Signature Perfume Collection" %>
</h2>

<div class="filter-buttons">
    <form method="get" action="ShopServlet">
        <input type="hidden" name="category" value="Men">
        <button type="submit">Men</button>
    </form>
    <form method="get" action="ShopServlet">
        <input type="hidden" name="category" value="Women">
        <button type="submit">Women</button>
    </form>
    <form method="get" action="ShopServlet">
        <input type="hidden" name="category" value="Unisex">
        <button type="submit">Couples</button>
    </form>
</div>

<div class="container">
<% if (perfumeList != null && !perfumeList.isEmpty()) {
    for (Perfume p : perfumeList) { %>
    <div class="card">
        <img src="<%= request.getContextPath() %>/<%= p.getImageUrl() %>" alt="Perfume Image">
        <h3><%= p.getPerfumeName() %></h3>
        <p>Category: <%= p.getCategoryName() %></p>
        <p class="price">RM <%= String.format("%.2f", p.getPrice()) %></p>
        <p>Stock: <%= p.getStock() %></p>
        <form onsubmit="addToCart(event, <%= p.getPerfumeId() %>)">
            <button type="submit" class="btn">Add to Cart</button>
        </form>
    </div>
<%  }
} else { %>
    <p style="text-align:center;">No perfumes found.</p>
<% } %>
</div>

<footer>
    &copy; 2025 Luxury Scents. All rights reserved.
</footer>

<script>
function addToCart(event, perfumeId) {
    event.preventDefault();
    document.getElementById("loadingOverlay").style.display = "flex";

    fetch('AddToCartServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: 'perfume_id=' + encodeURIComponent(perfumeId)
    })
    .then(res => {
        if (!res.ok) throw new Error("Failed");
        return res.text();
    })
    .then(() => {
        document.getElementById("loadingOverlay").style.display = "none";
        Swal.fire({
            icon: 'success',
            title: 'Added to cart!',
            timer: 1300,
            showConfirmButton: false,
            background: '#fff0f5',
            customClass: { popup: 'swal2-sm' }
        });
    })
    .catch(() => {
        document.getElementById("loadingOverlay").style.display = "none";
        Swal.fire({ icon: 'error', text: 'Failed to add to cart!' });
    });
}
</script>

</body>
</html>
