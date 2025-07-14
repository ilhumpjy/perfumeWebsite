<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Users</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff;
            color: #000;
            margin: 40px;
        }
        h1 {
            text-align: center;
            color: #000;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 30px;
        }
        th, td {
            padding: 12px;
            border: 1px solid black;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        form {
            display: inline;
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
            margin-top: 30px;
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

<h1>Manage Users</h1>

<%
    List<Map<String, Object>> users = (List<Map<String, Object>>) request.getAttribute("users");
    if (users != null && !users.isEmpty()) {
%>
    <table>
        <tr>
            <th>User ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Password</th>
            <th>Action</th>
        </tr>
        <%
            for (Map<String, Object> user : users) {
        %>
        <tr>
            <td><%= user.get("id") %></td>
            <td><%= user.get("username") %></td>
            <td><%= user.get("email") %></td>
            <td><%= user.get("password") %></td>
            <td>
                <form method="post" action="DeleteUserServlet">
                    <input type="hidden" name="user_id" value="<%= user.get("id") %>">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>
<%
    } else {
%>
    <p style="text-align: center;">No users found.</p>
<%
    }
%>

<div class="back-button">
    <a href="admin.jsp">Back to Dashboard</a>
</div>

</body>
</html>
