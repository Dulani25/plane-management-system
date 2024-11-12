package w2051819_PlaneManagement ;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PlaneManagement {
    static String[] A ={"O","O","O","O","O","O","O","O","O","O","O","O","O","O"};
    static String[] B ={"O","O","O","O","O","O","O","O","O","O","O","O"};
    static String[] C ={"O","O","O","O","O","O","O","O","O","O","O","O"};
    static String[] D ={"O","O","O","O","O","O","O","O","O","O","O","O","O","O"};

    static Ticket[] tickets = new Ticket[52];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int userOption = 1;
        while (userOption != 0) {
            System.out.println("Welcome to the Plane Management application");
            System.out.println("*******************************************");
            System.out.println("*              MENU OPTIONS               *");
            System.out.println("*******************************************");

            String[] menu = {
                    "1) Buy a seat",
                    "2) Cancel a seat",
                    "3) Find first available seat",
                    "4) Show seating plan",
                    "5) Print tickets information and total sales",
                    "6) Search ticket",
                    "0) Quit"
            };
            // Printing the menu
            for (String temp : menu) {
                System.out.println(temp);
            }
            System.out.println("*******************************************");
            System.out.println("Please select an option: ");

            try {
                userOption = input.nextInt();
                switch (userOption) {
                    case 1:
                        buy_seat();
                        break;
                    case 2:
                        cancel_seat();
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_ticket_info_and_total_sales();
                        break;
                    case 6:
                        search_tickets();
                        break;
                    case 0:
                        System.out.println("Quitting the program...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (java.util.InputMismatchException e) {
                // Clear the invalid input from the scanner
                input.next();
                System.out.println("Invalid input. Please enter a valid option.");
            }
        }
        input.close(); // Close the scanner when done
    }


    public static void buy_seat() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the row letter:");
        String rowletter = input.next().toUpperCase();

        if (!rowletter.matches("[A-D]")) {
            System.out.println("Invalid row letter. Please enter a single uppercase letter (A-D).");
            return; // Exits the method early if the row letter is invalid.
        }

        System.out.println("Enter the seat number:");
        while (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter a positive integer for the seat number.");
            input.next(); // Consume the non-integer input
        }
        int seatNum = input.nextInt();
        if (seatNum <= 0) {
            System.out.println("Seat number must be a positive integer.");
            return; // Exits the method early if the seat number is invalid.
        }
        String[] row;
        switch (rowletter) {
            case "A":
                row = A;
                break;
            case "B":
                row = B;
                break;
            case "C":
                row = C;
                break;
            case "D":
                row = D;
                break;
            default:
                System.out.println("This shouldn't happen if input validation is correct.");
                return;
        }
        if (row[seatNum - 1].equals("O")) {
            row[seatNum - 1] = "X";
            System.out.println("Seat iS successful.");
        } else {
            System.out.println("Sorry, the seat is not available. Please choose another seat.");
            return ;
        }

        // Collect Person details and create a Ticket
        System.out.println("Enter name:");
        String name = input.next();
        System.out.println("Enter surname:");
        String surname = input.next();
        System.out.println("Enter email:");
        String email = input.next();

        double price = calculatePrice(rowletter, seatNum);
        Person person = new Person(name, surname, email);
        Ticket ticket = new Ticket(rowletter, seatNum, price, person);

        // Attempt to book a seat
        boolean seatBooked = false;
        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i] == null) {
                tickets[i] = ticket;
                System.out.println("Seat booked successfully.");
                ticket.save(); // Save ticket to a file
                seatBooked = true;
                break;
            }
        }
        if (!seatBooked) {
            System.out.println("Sorry, no available seats.");
        }
    }
    public static double calculatePrice(String row, int seatNum) {
        double price = 0.0;
        switch (row) {
            case "A":
            case "B":
                price = 200.0; // Premium rows
                break;
            case "C":
            case "D":
                price = 150.0; // Economy rows
                break;
            default:
                System.out.println("Invalid row letter. Price set to 0.");
                break;
        }
        return price;
    }
    public static void cancel_seat() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the row letter:");
        String rowLetter = input.next().toUpperCase();

        if (!rowLetter.matches("[A-D]")) {
            System.out.println("Invalid row letter. Please enter a single uppercase letter (A-D).");
            return; // Exits the method early if the row letter is invalid.
        }

        System.out.println("Enter the seat number:");
        while (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter a positive integer for the seat number.");
            input.next(); // Consume the non-integer input
        }
        int seatNum = input.nextInt();
        if (seatNum <= 0 || seatNum > A.length) { // Assuming all rows have the same max length as A for simplification
            System.out.println("Seat number must be a positive integer within the valid range.");
            return; // Exits the method early if the seat number is invalid.
        }

        try {
            String[] row;
            switch (rowLetter) {
                case "A":
                    row = A;
                    break;
                case "B":
                    row = B;
                    break;
                case "C":
                    row = C;
                    break;
                case "D":
                    row = D;
                    break;
                default:
                    System.out.println("This shouldn't happen if input validation is correct.");
                    return;
            }

            if (row[seatNum - 1].equals("X")) {
                row[seatNum - 1] = "O";
                System.out.println("Seat is cancelled.");
            } else {
                System.out.println("Seat is free.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid data.");
            // This catch block might not be reached due to input handling above.
        }
    }

    public static void find_first_available(){

        for (int i = 0; i < A.length; i++) {
            if (A[i].equals("O")) {
                System.out.println("Seat found.");
                System.out.println("First available seat is " + (i + 1) + " at Row A.");
                return;
            }
        }
        for (int i = 0; i < B.length; i++) {
            if (B[i].equals("O")){
                System.out.println("Seat found.");
                System.out.println("First available seat is " + (i + 1) + " at Row B.");
                return;
            }
        }
        for (int i = 0; i < C.length; i++) {
            if  (C[i].equals("O")){
                System.out.println("Seat found.");
                System.out.println("First available seat is " + (i + 1) + " at Row C.");
                return;
            }
        }
        for (int i = 0; i < D.length; i++) {
            if (D[i].equals("O")){
                System.out.println("Seat found.");
                System.out.println("First available seat is " + (i + 1) + " at Row D.");
                return;
            }
        }
        System.out.println("No available seats found.");
    }


    public static void show_seating_plan(){
        System.out.println("Seating Plan: ");

        for (int i = 0; i < A.length; i++) {
            System.out.print(A[i]);
        }
        System.out.println();
        for (int i = 0; i < B.length; i++) {
            System.out.print(B[i]);
        }
        System.out.println();
        for (int i = 0; i < C.length; i++) {
            System.out.print(C[i]);
        }
        System.out.println();
        for (int i = 0; i < D.length; i++) {
            System.out.print(D[i]);
        }
        System.out.println();
    }
    public static void print_ticket_info_and_total_sales(){
        double totalSales = 0;
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                ticket.printTicketInfo();
                totalSales += ticket.getPrice();
            }
        }
        System.out.println("Total sales: £" + totalSales);

    }
    public static void search_tickets() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the row letter:");
        String rowLetter = input.next().toUpperCase();
        System.out.println("Enter the seat number:");
        int seatNum = input.nextInt();

        boolean found = false;
        for (Ticket ticket : tickets) {
            if (ticket != null && ticket.getRow().equals(rowLetter) && ticket.getSeat() == seatNum) {
                System.out.println("This seat is sold. Ticket details:");
                ticket.printTicketInfo();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("This seat is available.");
        }
    }


}
