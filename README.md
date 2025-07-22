# Mini SocialVerse  
*A minimal social media web application with features like posts, comments, likes, and image uploads.*

---

## 📌 Project Overview
Mini SocialVerse is a full-stack social media platform where users can register, log in, create posts with images, and interact through likes and comments. It is built using **Spring Boot** for the backend and **HTML, CSS, and JavaScript** for the frontend.

---

## 🚀 Features
- User Registration and Login  
- Post creation with text and images  
- Like and Comment on posts  
- Image upload and dynamic display  
- Email confirmation sent after successful registration  
- MVC architecture for clean separation of concerns  

---

## 🛠️ Tech Stack
**Backend:** Java, Spring Boot, Spring Data JPA (Hibernate), MySQL  
**Frontend:** HTML, CSS, JavaScript  
**Tools:** Eclipse, Postman, Git & GitHub  

---

## ⚙️ Installation & Setup
### 1. Clone the repository
:
```bash
git clone https://github.com/your-username/mini-socialverse.git
cd mini-socialverse
```
### 2. Setup Database:
Create a MySQL database named socialverse.
Update the database credentials in application.properties:
  - spring.datasource.url=jdbc:mysql://localhost:3306/socialverse
  - spring.datasource.username=your_username
  - spring.datasource.password=your_password
### 3. Run the Application
Use the command below or run directly from your IDE:
  - mvn spring-boot:run
### 📧 Email Service
- Configured using JavaMailSender.
- Sends a confirmation email automatically after successful user registration.
## 📂 Project Structure
```
Mini-SocialVerse/
├── src/main/java/com/example/socialverse
│   ├── controller
│   ├── model
│   ├── repository
│   ├── service
│   └── SocialVerseApplication.java
├── src/main/resources
│   ├── templates
│   ├── static
│   └── application.properties
└── README.md


