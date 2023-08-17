# bookstore-inventory-system

Singleton design pattern

MySQL 8.1 for storing book information

An initial book stock inventory is uploaded via DatabaseSetup.java
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/56686501-4d7f-4da1-8f38-6196608fbad9)

Running Main.java via local terminal:
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/9c6461d1-e8f0-43ff-86d6-72d9b1edc522)
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/64559dfe-a3a5-4d9f-b5bf-1648d4e32338)


## 1. Add a book to the inventory.
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/3a5ea1d7-d01d-420b-9352-b3c40460f0c0)
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/d547accb-8f85-4f3c-a73e-abc1edbb0ea2)


## 2. Remove a book from the inventory.
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/edb54ac9-abdc-409d-87db-62d3f5554c9e)
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/b7b616f3-05f7-47c4-9a5c-235bd794608c)


## 3. Update the quantity in stock for a given book.
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/75fec88f-9457-43b8-9d67-5e238aa82a87)
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/c5fbd172-bbbd-45b4-bcae-d8db126e7226)


## 4. Retrieve the quantity in stock for a given book.
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/9d3d6a4c-5a30-4636-87cf-9e71ec88acbd)

## 5. List all books in the inventory.
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/21f6f7b6-9972-4b4e-8e7b-78e100a45d77)
![image](https://github.com/JanetZhangXiaodan/bookstore-inventory-system/assets/15668158/5e581911-7e8b-4460-99b3-08cfe68b27b9)


## Further improvements on Code.
### 1. Adding a new book with the same ISBN is not possible - by design True as each book should have an unique ISBN
### 2. User SpringBoot JPA data instead of java.sql - less sql injection vulnerabilities 


## Thoughts on additional bonus points:
### 1. Search and Filter Functionality: Not implemented in scripts. Can be done through MySQL client command prompt.
### 2. Authentication and Authorization: Not implemented in scripts.
#### 2a. User Model and Repository: Create a User model class to store user information, such as username, password, and roles. Implement a repository to manage user data.
#### 2b. User Service: Create a service class that interacts with the user repository. Implement methods for user registration and fetching user details by username.
#### 2c. Security Configuration: Configure Spring Security to manage user authentication and authorization. Create a security configuration class to define authentication providers, password encoders, and security rules.
#### 2d. Front-End Integration: Update front-end to handle user registration and login forms. 
### 3. Error Handling and Logging: Use of logger and catch exceptions
### 4. Performance Optimization: Use of in-memory inventory over communicating with MySQL db all the time
