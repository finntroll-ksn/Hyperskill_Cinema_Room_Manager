import java.util.Scanner;

public class Cinema {
    private static Scanner scanner = new Scanner(System.in);

    private static String[][] theatreRoom;

    private static int rows = 0;
    private static int seats = 0;

    private static final String FREE_SEAT = "S";
    private static final String SOLD_SEAT = "B";

    public static void main(String[] args) {
        boolean exit = false;

        initTheatre();

        while (!exit) {
            printMenu();

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    printTheatre();
                    break;
                case 2:
                    printTicketPrice();
                    break;
                case 3:
                    printStatistics();
                    break;
                case 0:
                    exit = true;
                    break;
            }
        }

    }

    private static void printMenu() {
        System.out.printf(
                "%s\n%s\n%s\n%s\n",
                "1. Show the seats",
                "2. Buy a ticket",
                "3. Statistics",
                "0. Exit"
        );
    }

    private static void initTheatre() {
        System.out.println("Enter the number of rows:");

        rows = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the number of seats in each row:");

        seats = Integer.parseInt(scanner.nextLine());

        theatreRoom = new String[rows][seats];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                theatreRoom[i][j] = FREE_SEAT;
            }
        }
    }

    private static void printTheatre() {
        System.out.println("Cinema:");

        System.out.print(" ");

        for (int i = 1; i <= seats; i++) {
            System.out.print(" " + i);
        }

        System.out.println();

        int rowNumber = 1;

        for (String[] line : theatreRoom) {
            System.out.printf("%d %s\n", rowNumber, String.join(" ", line));
            rowNumber++;
        }
    }

    private static void printTicketPrice() {
        int rowNumber;
        int seatNumber;
        boolean freeSeat;

        do {
            freeSeat = true;

            System.out.println("Enter a row number:");
            rowNumber = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter a seat number in that row:");
            seatNumber = Integer.parseInt(scanner.nextLine());

            if (rowNumber > rows || seatNumber > seats) {
                System.out.println("Wrong input! ");
                freeSeat = false;
            }

            if (freeSeat && !theatreRoom[rowNumber - 1][seatNumber - 1].equals(FREE_SEAT)) {
                System.out.println("That ticket has already been purchased");
                freeSeat = false;
            }
        } while (!freeSeat);

        int ticketCoast = 0;

        theatreRoom[rowNumber - 1][seatNumber - 1] = SOLD_SEAT;

        if (rows * seats > 60) {
            ticketCoast = 8;

            if (rowNumber <= rows / 2) {
                ticketCoast += 2;
            }
        } else {
            ticketCoast = 10;
        }

        System.out.printf("Ticket price: $%d\n", ticketCoast);
    }

    private static void printStatistics() {
        ticketStatistic();
        countCurrentIncome();
        countTotalIncome();
    }

    private static void ticketStatistic() {
        int sold = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                if (theatreRoom[i][j].equals(SOLD_SEAT)) {
                    sold++;
                }
            }
        }

        double percent = sold == 0 ? 0.00 : rows * seats * 1.0 / sold;

        System.out.printf("Number of purchased tickets: %d\n", sold);
        System.out.printf("Percentage: %.2f%c\n", percent, '%');
    }

    private static void countCurrentIncome() {
        int currentIncome = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                if (theatreRoom[i][j].equals(SOLD_SEAT)) {
                    currentIncome += 10;

                    if (i > rows / 2 && rows * seats > 60) {
                        currentIncome -= 2;
                    }
                }
            }
        }

        System.out.printf("Current income: $%d\n", currentIncome);
    }

    private static void countTotalIncome() {
        int totalIncome;

        if (rows * seats > 60) {
            int frontHalf = rows / 2;
            totalIncome = frontHalf * seats * 2 + (rows * seats * 8);
        } else {
            totalIncome = rows * seats * 10;
        }

        System.out.printf("Total income: $%d\n", totalIncome);
    }
}