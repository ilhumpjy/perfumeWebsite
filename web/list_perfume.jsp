<%@ page import="java.util.*, java.text.DecimalFormat" %>
<%
    List<Map<String, Object>> perfumes = (List<Map<String, Object>>) request.getAttribute("perfumes");
    DecimalFormat df = new DecimalFormat("0.00");
%>
<!DOCTYPE html>
<html>
<head>
    <title>All Perfumes</title>
    <style>
        body { font-family: Arial; margin: 30px; }
        table { width: 90%; margin: auto; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #333; text-align: center; }
        th { background: black; color: white; }
        img { width: 100px; }
        .back { margin-top: 30px; text-align: center; }
        .back button { background: black; color: white; padding: 10px 20px; border-radius: 5px; font-weight: bold; cursor: pointer; }
    </style>
</head>
<body>
<h1 style="text-align:center;">All Perfumes</h1>
<table>
<tr>
    <th>ID</th><th>Name</th><th>Category ID</th><th>Price</th><th>Stock</th><th>Description</th><th>Image</th>
</tr>
<% for (Map<String, Object> p : perfumes) { %>
<tr>
    <td><%= p.get("id") %></td>
    <td><%= p.get("name") %></td>
    <td><%= p.get("category") %></td>
    <td>RM <%= df.format(p.get("price")) %></td>
    <td><%= p.get("stock") %></td>
    <td><%= p.get("description") %></td>
    <td><img src="<%= request.getContextPath() %>/<%= p.get("image") %>" alt="Image"></td>
</tr>
<% } %>
</table>

<div class="back">
    <form action="admin.jsp" method="get">
        <button type="submit">Back to Dashboard</button>
    </form>
</div>
</body>
</html>
