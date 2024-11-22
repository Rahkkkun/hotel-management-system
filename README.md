# Hotel Reservation System

## 📋 Introduction
The **Hotel Reservation System** is a Java-based console application that manages hotel bookings. It integrates with a MySQL database to perform CRUD operations, such as reserving a room, viewing reservations, updating details, and deleting reservations.

## 🛠 Features
- **Reserve a Room**: Add guest details, including name, room number, and contact number.
- **View Reservations**: Display all existing reservations in a formatted table.
- **Retrieve Room Number**: Find the room number using reservation ID and guest name.
- **Update Reservations**: Modify guest details for an existing reservation.
- **Delete Reservations**: Remove a reservation by ID.
- **Exit System**: Gracefully exits the application.

---

## 🚀 Technologies Used
- **Java**: Core programming language.
- **JDBC**: For database connectivity.
- **MySQL**: Backend database.
- **Scanner**: For user input handling.

---

## 📂 Database Setup
1. Create a MySQL database named `hotel_db`:
    ```sql
    CREATE DATABASE hotel_db;
    ```
2. Create a table `reservations`:
    ```sql
    CREATE TABLE reservations (
        reservation_id INT AUTO_INCREMENT PRIMARY KEY,
        guest_name VARCHAR(100),
        room_number INT,
        contact_number VARCHAR(15),
        reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    ```
3. Update the database connection details in the program:
    - **URL**: `jdbc:mysql://127.0.0.1:3306/hotel_db`
    - **Username**: `root`
    - **Password**: Replace `"Rahul@2004"` with your MySQL root password.

---

## 📜 How to Run
1. Ensure Java and MySQL are installed and configured on your system.
2. Compile the Java program:
    ```bash
    javac HotelReservationSystem.java
    ```
3. Run the application:
    ```bash
    java HotelReservationSystem
    ```

---

## 🖥️ Application Workflow
1. Upon launch, the system displays a menu:
    ```
    ----HOTEL-MANAGEMENT-SYSTEM----
    1. Reserve a room
    2. View reservation
    3. Get room number
    4. Update reservations
    5. Delete reservations
    6. Exit
    ```
2. Users select an option by entering the corresponding number.
3. Based on user input, the program performs the selected database operation.

---

## ⚠️ Error Handling
- Invalid database credentials or missing JDBC driver will display error messages.
- Database operations are encapsulated in `try-catch` blocks to handle `SQLException`.
- Checks for reservation existence before performing updates or deletions.

---

## 🔑 Sample SQL Queries Used
- **Insert**:
    ```sql
    INSERT INTO reservations (guest_name, room_number, contact_number)
    VALUES ('John Doe', 101, '1234567890');
    ```
- **Select**:
    ```sql
    SELECT * FROM reservations;
    ```
- **Update**:
    ```sql
    UPDATE reservations SET guest_name = 'Jane Doe', room_number = 102 WHERE reservation_id = 1;
    ```
- **Delete**:
    ```sql
    DELETE FROM reservations WHERE reservation_id = 1;
    ```

---

## 🛡 Security Considerations
- **SQL Injection**: Use prepared statements in a production environment to avoid SQL injection vulnerabilities.
- **Password Management**: Replace hardcoded passwords with environment variables or secure vaults.

---

## 🛠 Future Enhancements
- Add a graphical user interface (GUI).
- Integrate email notifications for booking confirmations.
- Add advanced search and filter options.
- Implement user authentication.

---

### 👤 Author
**Rahkkkun**  
- CSE Undergraduate | Developer | Designer  
- [GitHub](https://github.com/) | [LinkedIn](https://linkedin.com/)  

---

Feel free to modify and enhance the program based on your requirements! ✨
