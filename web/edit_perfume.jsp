<%@ page import="model.Perfume" %>
<%@ page import="java.util.*" %>
<%
    List<Perfume> perfumeList = (List<Perfume>) request.getAttribute("perfumeList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Perfume</title>
    <style>
        body { font-family: Arial; margin: 30px; }
        table { width: 90%; margin: auto; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #333; text-align: center; }
        th { background: black; color: white; }
        input[type=submit] { background: black; color: white; border: none; padding: 6px 12px; cursor: pointer; }
        .delete-btn { background: red; color: white; border: none; padding: 6px 12px; cursor: pointer; }
        .back { margin-top: 30px; text-align: center; }
    </style>
</head>
<body>
<h1 style="text-align:center;">Manage Perfumes</h1>

<table>
<tr>
    <th>ID</th>
    <th>Name</th>
    <th>Category</th>
    <th>Price</th>
    <th>Stock</th>
    <th>Edit</th>
    <th>Delete</th>
</tr>

<% for (Perfume perfume : perfumeList) { %>
<tr>
    <td><%= perfume.getPerfumeId() %></td>
    <td><%= perfume.getPerfumeName() %></td>
    <td><%= perfume.getCategoryId() %></td> <!-- Kalau ada getCategoryName() boleh guna nama kategori -->
    <td><%= perfume.getPrice() %></td>
    <td><%= perfume.getStock() %></td>

    <!-- Edit Button -->
    <td>
        <form method="post" action="EditPerfumeDetailsServlet">
            <input type="hidden" name="perfume_id" value="<%= perfume.getPerfumeId() %>">
            <input type="submit" value="Edit">
        </form>
    </td>

    <!-- Delete Button -->
    <td>
        <form method="post" action="DeletePerfumeServlet" onsubmit="return confirm('Are you sure you want to delete this perfume?');">
            <input type="hidden" name="perfume_id" value="<%= perfume.getPerfumeId() %>">
            <input type="hidden" name="old_image" value="<%= perfume.getImageUrl() != null ? perfume.getImageUrl() : "" %>">
            <input type="submit" class="delete-btn" value="Delete">
        </form>
    </td>
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
