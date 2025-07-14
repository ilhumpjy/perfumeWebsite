<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f3f3f3;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .register-box {
            background-color: #ffffff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
            width: 100%;
            max-width: 400px;
        }

        .register-box h2 {
            margin-bottom: 20px;
            color: #333;
            text-align: center;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #aaa;
            border-radius: 5px;
        }

        input[type="submit"] {
            background-color: #111;
            color: white;
            border: none;
            padding: 10px;
            width: 100%;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }

        input[type="submit"]:hover {
            background-color: #444;
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <form class="register-box" action="RegisterServlet" method="post">
        <h2>User Registration</h2>

        <% String error = (String) request.getAttribute("errorMsg"); %>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>

        <input type="text" name="user_name" placeholder="Full Name" required>
        <input type="text" name="user_username" placeholder="Username" required>
        <input type="email" name="user_email" placeholder="Email" required>
        <input type="password" name="user_password" placeholder="Password" required>

        <input type="submit" value="Register">
    </form>
</body>
</html>
