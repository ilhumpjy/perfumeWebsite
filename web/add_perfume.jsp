<%@ page import="java.util.*" %>
<%
    List<String[]> categoryList = (List<String[]>) request.getAttribute("categoryList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Perfume</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f3f3f3; display: flex; justify-content: center; align-items: center; padding-top: 50px; }
        .form-box { background: white; padding: 30px 40px; border-radius: 10px; box-shadow: 0 0 10px #ccc; width: 100%; max-width: 500px; }
        input, select, textarea { width: 100%; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #aaa; }
        input[type=submit], .back-button { background: black; color: white; border: none; cursor: pointer; padding: 10px; font-weight: bold; border-radius: 5px; width: 100%; }
        .back-button { margin-top: 10px; text-align: center; }
    </style>
</head>
<body>
<div class="form-box">
    <h2>Add New Perfume</h2>
    <form action="AddPerfumeServlet" method="post" enctype="multipart/form-data">
        <input type="text" name="perfume_name" placeholder="Perfume Name" required>

        <select name="category_id" required>
            <option value="">-- Select Category --</option>
            <% for (String[] cat : categoryList) { %>
                <option value="<%= cat[0] %>"><%= cat[1] %></option>
            <% } %>
        </select>

        <input type="text" name="price" placeholder="Price (e.g. 99.99)" required>
        <input type="text" name="stock" placeholder="Stock (e.g. 100)" required>
        <textarea name="description" placeholder="Description" rows="3" required></textarea>

        <!-- Image upload choose file -->
        <input type="file" name="image_file" accept="image/*" required>

        <input type="submit" value="Add Perfume">
    </form>

    <form action="admin.jsp" method="get">
        <button type="submit" class="back-button">Back to Dashboard</button>
    </form>
</div>
</body>
</html>
