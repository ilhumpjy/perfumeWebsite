<%@ page import="model.Perfume" %>
<%@ page import="java.util.*" %>
<%
    Perfume perfume = (Perfume) request.getAttribute("perfume");
    List<Perfume> categories = (List<Perfume>) request.getAttribute("categories");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Perfume</title>
    <style>
        body {
            font-family: Arial;
            background-color: #f8f8f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .edit-box {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
            width: 400px;
        }
        h2 {
            text-align: center;
            margin-bottom: 25px;
        }
        input, textarea, select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #aaa;
            border-radius: 5px;
        }
        input[type=submit], .back-button {
            background-color: black;
            color: white;
            cursor: pointer;
            border: none;
            padding: 10px;
            width: 100%;
            border-radius: 5px;
            font-weight: bold;
        }
        input[type=submit]:hover, .back-button:hover {
            background-color: #333;
        }
        .image-preview {
            text-align: center;
            margin-bottom: 15px;
        }
        .image-preview img {
            width: 120px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <form class="edit-box" action="UpdatePerfumeServlet" method="post" enctype="multipart/form-data">
        <h2>Edit Perfume</h2>
        <input type="hidden" name="perfume_id" value="<%= perfume.getPerfumeId() %>">
        <input type="hidden" name="old_image" value="<%= perfume.getImageUrl() %>">

        <input type="text" name="perfume_name" value="<%= perfume.getPerfumeName() %>" placeholder="Name" required>

        <select name="category_id" required>
            <% for (Perfume category : categories) { 
                String selected = category.getCategoryId() == perfume.getCategoryId() ? "selected" : "";
            %>
                <option value="<%= category.getCategoryId() %>" <%= selected %>><%= category.getCategoryName() %></option>
            <% } %>
        </select>

        <input type="text" name="price" value="<%= perfume.getPrice() %>" placeholder="Price" required>
        <input type="number" name="stock" value="<%= perfume.getStock() %>" placeholder="Stock" required>
        <textarea name="description" rows="3" placeholder="Description"><%= perfume.getDescription() %></textarea>

        <div class="image-preview">
            <p>Current Image:</p>
            <img src="<%= request.getContextPath() + "/" + perfume.getImageUrl() %>" alt="Perfume Image">
        </div>

        <input type="file" name="image_file" accept="image/*">
        <small>Leave blank if you don't want to change the image.</small>

        <input type="submit" value="Update Perfume">
        <br><br>
        <button class="back-button" onclick="window.location.href='EditPerfumeServlet'; return false;">Back</button>
    </form>
</body>
</html>
