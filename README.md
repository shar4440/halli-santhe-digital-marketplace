# 🌾 Halli Santhe Digital Marketplace

Halli Santhe Digital Marketplace is an Android application built using **Kotlin + Jetpack Compose + Room Database** that enables rural farmers/sellers to directly sell their products to buyers through a digital platform.

The project aims to digitize traditional village markets by eliminating middlemen and creating direct farmer-to-customer communication.

---

## 📌 Problem Statement

Traditional rural marketplaces face several issues:

- Farmers depend on middlemen
- Limited customer reach
- Manual price negotiation
- No digital visibility for products
- Difficult inventory management

This application solves these issues by creating a **digital marketplace platform** where sellers can upload products and buyers can directly purchase them.

---

## 🎯 Objectives

- Digitize village markets
- Help farmers reach more customers
- Enable direct buying/selling
- Reduce dependency on middlemen
- Provide easy product management

---

# 🚀 Features

## Seller Module
- Seller Registration/Login
- Add Products
- Update Products
- Delete Products
- View Own Products
- Product Inventory Management
- Product Image Upload
- Seller Dashboard

---

## Buyer Module
- Buyer Registration/Login
- Browse Products
- Search Products
- Product Filtering
- Product Details View
- Buy Products
- Buyer Dashboard

---

## Negotiation Module
- Buyer sends price offer
- Seller receives offer
- Accept/Reject negotiation
- Offer tracking

---

## Authentication Module
- Role-based login
- Session handling
- User registration

---

# 🛠 Tech Stack

### Frontend
- Kotlin
- Jetpack Compose
- Material UI

### Backend
- Room Database
- SQLite

### Architecture
- MVVM Architecture

### Libraries
- Navigation Compose
- Coil
- ViewModel
- Coroutines
- Room

---

# 📂 Project Structure

```bash
HalliSantheDigital/
│
├── app/
│   ├── src/main/java/com/example/hallisanthedigital/
│   │
│   ├── data/
│   │   ├── dao/
│   │   │   ├── UserDao.kt
│   │   │   ├── ProductDao.kt
│   │   │   └── NegotiationDao.kt
│   │   │
│   │   ├── database/
│   │   │   └── AppDatabase.kt
│   │   │
│   │   ├── model/
│   │   │   ├── User.kt
│   │   │   ├── Product.kt
│   │   │   └── Negotiation.kt
│   │
│   ├── repository/
│   │   └── AppRepository.kt
│   │
│   ├── ui/
│   │   ├── screens/
│   │   │   ├── LoginScreen.kt
│   │   │   ├── RegisterScreen.kt
│   │   │   ├── BuyerDashboardScreen.kt
│   │   │   ├── SellerDashboardScreen.kt
│   │   │   ├── ProductDetailsScreen.kt
│   │   │   └── ProfileScreen.kt
│   │
│   ├── navigation/
│   │   ├── NavGraph.kt
│   │   └── Routes.kt
│   │
│   ├── viewmodel/
│   │   ├── AuthViewModel.kt
│   │   ├── ProductViewModel.kt
│   │   └── NegotiationViewModel.kt
│   │
│   └── MainActivity.kt
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

# 🔄 Application Workflow

### Seller Flow
Register → Login → Add Product → Manage Products → Receive Orders

### Buyer Flow
Register → Login → Browse Products → Search → Purchase Product

---

# 🗄 Database Tables

## User Table
- userId
- name
- email
- password
- role

## Product Table
- productId
- productName
- description
- price
- quantity
- image
- sellerId

## Negotiation Table
- negotiationId
- buyerId
- sellerId
- offeredPrice
- status

---

# ⚙ Installation Steps

### Clone Repository

```bash
git clone https://github.com/shar4440/halli-santhe-digital-marketplace.git
```

### Open in Android Studio

Open the project folder.

### Sync Gradle

Let dependencies install.

### Run Application

```bash
Shift + F10
```

---

# 📱 Screens

- Login Screen
- Registration Screen
- Buyer Dashboard
- Seller Dashboard
- Product Details
- Negotiation Screen
- Profile Screen

---

# Future Enhancements

- Firebase Integration
- Online Payments
- Real-time Chat
- AI Product Recommendation
- Delivery Tracking
- Multi-language Support
- Voice Search

---

# Advantages

- Helps rural farmers
- Reduces middlemen dependency
- Easy product selling
- Improves digital accessibility

---

# Author

### Sharanayya Hiremath
BE Student | Android Developer | AI Enthusiast

---

# GitHub Repository

https://github.com/shar4440/halli-santhe-digital-marketplace
