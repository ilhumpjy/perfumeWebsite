<%@ page import="java.util.*" %>
<%
    List<Map<String, Object>> categories = (List<Map<String, Object>>) request.getAttribute("categories");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Categories</title>
    <style>
        body {
            font-family: Arial;
            background-color: #fff;
            color: #111;
            padding: 30px;
        }
        h1 {
            color: #000;
            text-align: center;
        }
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
            background: #f9f9f9;
        }
        th, td {
            border: 1px solid #444;
            padding: 10px;
            text-align: left;
        }
        form {
            margin: 20px auto;
            text-align: center;
        }
        input[type="text"] {
            padding: 8px;
            width: 250px;
        }
        input[type="submit"] {
            padding: 8px 20px;
            background: black;
            color: white;
            border: none;
            margin-left: 10px;
            border-radius: 4px;
        }
        .delete-btn {
            background-color: black;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
        }
        .delete-btn:hover {
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

<h1>Manage Categories</h1>

<!-- Add Category Form -->
<form method="post" action="ManageCategoriesServlet">
    <label>New Category Name:</label>
    <input type="text" name="category_name" required>
    <input type="submit" value="Add Category">
</form>

<!-- Category Table -->
<table>
    <tr>
        <th>ID</th>
        <th>Category Name</th>
        <th>Action</th>
    </tr>
    <%
        for (Map<String, Object> cat : categories) {
    %>
    <tr>
        <td><%= cat.get("id") %></td>
        <td><%= cat.get("name") %></td>
        <td>
            <form method="post" action="DeleteCategoryServlet" style="display:inline;">
                <input type="hidden" name="category_id" value="<%= cat.get("id") %>">
                <input type="submit" value="Delete" class="delete-btn">
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>

<!-- Back to Dashboard Button -->
<div class="back-button">
    <a href="admin.jsp">Back to Dashboard</a>
</div>

</body>
</html>
