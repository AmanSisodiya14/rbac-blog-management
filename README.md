
#   About the Project

This project is a blog management system that implements Role-Based Access Control (RBAC) using React for the frontend and Spring Boot for the backend. The application defines two roles:

Admin: Can create, edit, and delete blogs.

User: Can only read blogs.


## Technologies Used

**Frontend:**  React, Axios\
**Backend:**  Spring Boot, Hibernate, Spring Security (with JWT for authentication)\
**Database:**  MySQL \
 **Others:**  Postman (for API testing), Swagger (for API documentation)


 


 # Features
- Admin can:
    - Create new blogs
    - Edit existing blogs
    - Delete blogs
- Users can:
    - View all blogs
- Authentication and Authorization using JWT
- Role-Based Access Control with Spring Security


# Getting Started
Prerequisites
- Node.js (v14+ recommended)
- Java 17
- Maven
## Installation

1.Clone the repository:

```bash
git clone https://github.com/AmanSisodiya14/rbac-blog-management.gitrbac-blog-management.git
cd rbac-blog-management

```
2.Frontend Setup:

```bash
cd frontend
npm install
npm start


```
3.Backend Setup:

```bash
cd backend
mvn install
mvn spring-boot:run

```
4.Set up the database:\
   ```bash
 I use cloud database so you donot need to setup db.
 ```
 
## Usage/Examples

 
- Navigate to the frontend at http://localhost:5713 for the user interface.
- Access the backend API at http://localhost:8080/api for testing or integrations.
- Use Postman or any API client to test endpoints.



# API Endpoints

## Registration
- POST /api/auth/register 
## Authentication

- POST /api/auth/login - Login and obtain a JWT token

## Blogs
- Admin-Only:
    - POST /api/blogs - Create a blog
    - PUT /api/blogs/{id} - Edit a blog
    - DELETE /api/blogs/{id} - Delete a blog
- User & Admin:
    - GET /api/blogs - View all blogs


# Role-Based Access Control
- Admin Role:\
    Can access all API endpoints for managing blogs.

- User Role:\
    Restricted to read-only access (GET /api/blogs).

JWT tokens are issued during login and must be included in the Authorization header (Bearer <token>) of each API request.


# Contact
GitHub: AmanSisodiya14\
Email: sisodiya.aman07@gmail.com