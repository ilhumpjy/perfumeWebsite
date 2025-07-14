<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Login</title>
    <script>
        window.onload = function() {
            const params = new URLSearchParams(window.location.search);
            if (params.get("error") === "1") {
                alert("Invalid username or password. Please try again.");
            }
        };
    </script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@500;700&family=Roboto&display=swap');

        body {
            margin: 0;
            padding: 0;
            height: 100vh;
            background: linear-gradient(to right, #f8f4f0, #e6e2dd);
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Roboto', sans-serif;
        }

        .login-box {
            background: white;
            padding: 50px 40px;
            border-radius: 20px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
            position: relative;
            transition: transform 0.3s ease-in-out;
        }

        .login-box:hover {
            transform: translateY(-5px);
        }

        .login-box h2 {
            font-family: 'Playfair Display', serif;
            font-size: 28px;
            margin-bottom: 25px;
            color: #2d2d2d;
            position: relative;
        }

        .login-box h2::after {
            content: "";
            display: block;
            width: 50px;
            height: 3px;
            background: #cba135;
            margin: 10px auto 0;
            border-radius: 2px;
        }

        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px;
            margin: 10px 0 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 15px;
            transition: border 0.3s;
        }

        input[type=text]:focus,
        input[type=password]:focus {
            outline: none;
            border-color: #cba135;
            box-shadow: 0 0 5px rgba(203, 161, 53, 0.4);
        }

        input[type=submit] {
            background: #cba135;
            color: white;
            border: none;
            padding: 12px;
            width: 100%;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        input[type=submit]:hover {
            background: #a9831c;
        }

        .extra-buttons {
            margin-top: 25px;
            display: flex;
            justify-content: space-between;
            gap: 10px;
        }

        .extra-buttons a {
            flex: 1;
            text-decoration: none;
            padding: 10px 0;
            border-radius: 6px;
            background-color: #f2f2f2;
            font-size: 14px;
            color: #444;
            border: 1px solid #ccc;
            transition: background-color 0.3s, color 0.3s;
            text-align: center;
        }

        .extra-buttons a:hover {
            background-color: #cba135;
            color: #fff;
            border-color: #cba135;
        }

        .login-box::before {
            content: "";
            position: absolute;
            top: -20px;
            right: -20px;
            width: 80px;
            height: 80px;
            opacity: 0.1;
        }
    </style>
</head>
<body>
    <form class="login-box" action="LoginServlet" method="post">
        <h2>Welcome Back</h2>
        <input type="text" name="user_username" placeholder="Username" required>
        <input type="password" name="user_password" placeholder="Password" required>
        <input type="submit" value="Login">

        <!-- Extra Buttons -->
        <div class="extra-buttons">
            <a href="admin_login.html">Admin Login</a>
            <a href="register.jsp">Register</a>
        </div>
    </form>
</body>
</html>
    