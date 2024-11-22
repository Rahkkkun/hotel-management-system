
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class HotelReservationSystem {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "Rahul@2004";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Scanner scan = new Scanner(System.in);
        int choice;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            do {
                System.err.println("\n----HOTEL-MANAGEMENT-SYSTEM----");
                System.err.println("\n1. Reserve a room");
                System.err.println("2. View reservation");
                System.err.println("3. Get romm number");
                System.err.println("4. Update reservations");
                System.err.println("5. Delete reservations");
                System.err.println("6. Exit");
                System.err.print("\nchoose an option: ");
                choice = scan.nextInt();

                switch (choice) {
                    case 1:
                        reserveRoom(connection, scan);
                        break;
                    case 2:
                        viewReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, scan);
                        break;
                    case 4:
                        updateReservations(connection, scan);
                        break;
                    case 5:
                        deleteReservation(connection, scan);
                        break;
                    case 6:
                        System.err.println("\nThank You for using our system!");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");

                }
            } while (choice != 6);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void reserveRoom(Connection connection, Scanner scan) {
        try {
            System.out.print("Enter guest name: ");
            String guestName = scan.next();
            scan.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber = scan.nextInt();
            System.out.print("Enter contact number: ");
            String contactNumber = scan.next();

            String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) "
                    + "VALUES ('" + guestName + "', " + roomNumber + ", '" + contactNumber + "')";

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation successful!");
                } else {
                    System.out.println("Reservation failed!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void viewReservations(Connection connection) throws SQLException {

        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            System.err.println("\nCurrent Reservations: ");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            while (resultSet.next()) {
                int reservationid = resultSet.getInt("reservation_id");
                String guestname = resultSet.getString("guest_name");
                int roomnumber = resultSet.getInt("room_number");
                String contactnumber = resultSet.getString("contact_number");
                String reservationdate = resultSet.getTimestamp("reservation_date").toString();

                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        reservationid, guestname, roomnumber, contactnumber, reservationdate);
            }
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
        }
    }

    private static void getRoomNumber(Connection connection, Scanner scan) {
        try {
            System.out.print("Enter reservation ID: ");
            int reservationId = scan.nextInt();
            System.out.print("Enter guest name: ");
            String guestName = scan.next();

            String sql = "SELECT room_number FROM reservations "
                    + "WHERE reservation_id = " + reservationId
                    + " AND guest_name = '" + guestName + "'";

            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room number for Reservation ID " + reservationId
                            + " and Guest " + guestName + " is: " + roomNumber);
                } else {
                    System.out.println("Reservation not found for the given ID and guest name.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservations(Connection connection, Scanner scan) {
        try {
            System.out.print("Enter reservation ID to update: ");
            int reservationId = scan.nextInt();
            scan.nextLine(); // Consume the newline character

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }
            System.out.print("Enter new guest name: ");
            String newGuestName = scan.nextLine();
            System.out.print("Enter new room number: ");
            int newRoomNumber = scan.nextInt();
            System.out.print("Enter new contact number: ");
            String newContactNumber = scan.next();

            String sql = "UPDATE reservations SET guest_name = '" + newGuestName + "', "
                    + "room_number = " + newRoomNumber + ", "
                    + "contact_number = '" + newContactNumber + "' "
                    + "WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation updated successfully!");
                } else {
                    System.out.println("Reservation update failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void deleteReservation(Connection connection, Scanner scan) {
        try {
            System.out.print("Enter reservation ID to delete: ");
            int reservationId = scan.nextInt();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation deleted successfully!");
                } else {
                    System.out.println("Reservation deletion failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection connection, int reservationId) {
        try {
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
