# 💎 Luxury Scents - Perfume Shop E-Commerce System

Welcome to **Luxury Scents**, a Java-based e-commerce web application that allows users to browse and purchase perfumes online. This system features user-friendly shopping and powerful admin management for products, categories, users, and promotional coupons.

---

## 📦 Features

### 🛍️ User (Customer)
- Browse perfumes by category
- Add perfumes to cart
- Apply coupon codes during checkout
- Simulate payment during checkout
- Manage and review cart contents

### 🛠️ Admin
- Admin login dashboard
- Add, edit, delete perfumes (with image upload)
- Manage categories
- View and manage registered users
- Add, view, delete promotional coupons

---

## 🖥️ System Requirements

| Requirement  | Details                          |
|-------------|----------------------------------|
| Platform     | Java Web Application (Servlet + JSP) |
| IDE          | NetBeans 8.2 or higher           |
| Database     | Java DB (Derby) - Built-in       |
| DB Name      | `perfumeDB`                      |
| Server       | Apache Tomcat 8.5 (recommended)  |
| Browser      | Google Chrome, Firefox, etc.     |

---

## 🚀 How to Run the Project

### 🔧 Step 1: Import Project
1. Open **NetBeans**.
2. Go to `File > Open Project`.
3. Select the `Perfume_Website` folder.

### 🗃️ Step 2: Setup Database
1. Open **Services** tab in NetBeans.
2. Expand `Databases > Java DB`.
3. Start the Java DB server.
4. Create a new database named `perfumeDB`.
5. Run the provided SQL scripts from the project folder to create necessary tables:  
   - `perfume`, `category`, `coupon`, `users`, `orders`, etc.

> 🔒 Database connections use `DriverManager`, `Connection`, and `PreparedStatement` inside Servlet classes.

### ▶️ Step 3: Run the Project
1. Right-click the project > **Run**.
2. Access the site at:  
http://localhost:8080/Perfume_Website

---

## 🧑‍💼 Admin Panel

- Access URL: http://localhost:8080/PerfumeShop/admin_login.html


- Login with provided admin credentials.

### Admin Features

#### ✅ Manage Perfumes
- **Add**: Upload image (stored in `web/images/`), fill details, submit.
- **Edit**: Update existing perfume data.
- **Delete**: Remove perfumes from the database.

#### 📂 Manage Categories
- Add or delete perfume categories as needed.

#### 👥 Manage Users
- View list of all registered users.
- Remove users if needed.

#### 🎟️ Manage Coupons
- Add: Define code, description, discount %, start/end dates.
- View/Delete: See all active coupons and remove them if expired or invalid.

---

## 🧰 Error Handling

| Issue                     | Solution                                                                 |
|--------------------------|--------------------------------------------------------------------------|
| **Database Connection Error** | Check `DBConfig.java` for correct DB URL, username, and password.         |
| **Image Not Displaying**      | Ensure images are inside `web/images/` folder and `imageName` field is valid. |
| **Coupon Not Valid**          | Check if coupon code exists, is not expired, and correctly applied.        |

---

## 📬 Contact & Support

Need help? Reach out to us:

- 📧 Email: [LuxuryScents.support@gmail.com](mailto:LuxuryScents.support@gmail.com)  
- 📞 Phone: +6012-3456789

---

> © 2025 Luxury Scents. All rights reserved.

