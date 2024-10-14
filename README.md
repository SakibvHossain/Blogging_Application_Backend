# Blog REST Application

A comprehensive Blog REST API built with Spring Boot that allows users to create, manage, and interact with posts, comments, and categories. The project includes user-based filtering, sorting, searching, pagination, and implements role-based access control with JWT authentication.

## Features

- **User Registration & Authentication**
    - JWT-based authentication
    - Role-based access control (e.g., Admin, User)

- **Posts & Comments**
    - Users can create, update, delete, and view posts
    - Any user can comment on posts
    - Filter posts by user or category

- **Categories**
    - Posts can be organized into categories
    - Users can retrieve posts based on categories

- **Sorting, Searching & Pagination**
    - Search for posts by title or content
    - Sort posts by different fields (e.g., date, popularity)
    - Paginate through results

- **Exception Handling**
    - Custom exception handling with detailed error messages
    - Hibernate Validation for request payloads

- **Custom Response Handling**
    - Utilizes a custom `ApiResponse<T>` class for API responses instead of `ResponseEntity<T>`

- **File Service**
    - Handles file uploads related to posts

- **Database**
    - MySQL used as the database
    - Relationships implemented:
        - One-to-Many: A user can have multiple posts, and a post can have multiple comments
        - Many-to-Many: Posts can belong to multiple categories, and a category can have multiple posts

- **Architecture**
    - 3-layer architecture: Controller, Service, DAO layers
    - Data Transfer Objects (DTOs) for cleaner communication between layers

- **Documentation**
    - API is documented using **Swagger**

- **Testing**
    - Unit and integration tests using **JUnit5**

## Technologies Used

- **Backend**: Spring Boot
- **Security**: JWT Authentication, Role-based access control
- **Database**: MySQL
- **ORM**: Hibernate
- **Validation**: Hibernate Validator
- **Testing**: JUnit5
- **API Documentation**: Swagger
- **File Handling**: Custom File Service

## Installation

1. Clone the repository:
   ```bash
   https://github.com/SakibvHossain/Blogging_Application_Backend.git
   ```
2. Navigate to the project directory:
   ```bash
   cd blog-rest-api
   ```
3. Configure the database in `application.properties`:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/your-database
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```
   
## API Endpoints

### Authentication:
*  `POST /api/auth/login`: Authenticate and receive JWT
*  `POST /api/auth/register`: Register a new user

### User
*  `GET /api/users/all`: Get all users (Admin user only)
*  `PUT /api/users/updateUser/{id}`: Update user (authenticated user only)
*  `DELETE /api/users/deleteUser/{id}`: Delete user (Admin user or authenticated user only)
*  `GET /api/users/{id}`: Get specific User (Admin user only)
*  `PATCH /api/users/update/specific/{id}`: Update specific field (Admin or authenticated user only)

### Posts
*  `GET /api/post/all?pageNumber=1&pageSize=3`: Get all posts (with pagination, sorting, and searching)
*  `POST /api/user/{userID}/category/{categoryID}`: Create a new post (authenticated users only)
*  `PUT /api/post/update/{id}`: Update a post (authenticated users only)
*  `DELETE /api/post/delete/{id}`: Delete a post (admin users only)
*  `GET /api/postBy/category/{id}?pageNumber=1&pageSize=2`: Get post by ID also based on page number and size (authenticated users only)
*  `GET /api/postBy/author/{id}`: Get post based on author (authenticated user only)
*  `GET /api/post/{id}`: Get post by ID (authenticated user only)
*  `GET /api/post/search/{title}`: Get post by title (authenticated user only)
*  `PUT /api/post/upload/image/{postID}`: Update post image by ID (authenticated user only)

### Categories
*  `GET /api/category/all`: Get all categories
*  `POST /api/category/create`: Create a new category (admin users only)
*  `PUT /api/category/update/{id}`: Update a category (admin users only)
*  `DELETE /api/category/delete/{id}`: Delete a category (admin users only)
*  `GET /api/category/byId/{id}`: Get categories by ID (authenticated users only)

### Comments
*  `POST /api/comment/add/post/{id}`: Add a comment to a post (authenticated users only)
*  `DELETE /api/comment/delete/{id}`: Delete post specific post (authenticated users only)

### Contributing
Feel free to fork this repository, create a new branch, and submit a pull request. All contributions are welcome!