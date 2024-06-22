import java.io.*;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        //Define array to store customer details
        String[][] queues = new String[3][];
        queues[0]  = new String[2];
        queues[1]  = new String[3];
        queues[2]  = new String[5];

        //Define array to store X and O
        String[][] new_queues = new String[3][5];

        //Maximum burger count in stock
        int burger_count = 50;

        //Create file to store customer's details
        WriteFile(queues);

        System.out.println("<<<<<<<<<<<<<<<<<<< * >>>>>>>>>>>>>>>>>>\n" +
                           "~~~~~~ Foodies Fave Food Center ~~~~~~~~\n" +
                           "<<<<<<<<<<<<<<<<<<< * >>>>>>>>>>>>>>>>>>");
        System.out.println();

        //Creating 'menu' array to display menu options to user
        String[] menu = {
                "_________________ MENU _________________",
                "100/VFQ: View all queues",
                "101/VEQ: View all Empty Queues",
                "102/ACQ: Add customer to a Queue",
                "103/RCQ: Remove a customer from a Queue",
                "104/PCQ: Remove a served customer",
                "105/VCS: View Customers Sorted in alphabetical order",
                "106/SPD: Store Program Data into file",
                "107/LPD: Load Program Data from file",
                "108/STK: View Remaining burgers Stock",
                "109/AFS: Add burgers to Stock",
                "999/EXT: Exit the Program",
                "________________ ****** _________________"
        };

        //Displaying menu array
        for (String choice : menu) {
            System.out.println(choice);
        }

        //Looping user to input one option at each time
        boolean repeat = true;
        while(repeat) {
            Scanner input = new Scanner(System.in);
            System.out.println("000/MNU: Redisplay Menu");
            System.out.print("option : ");
            String option = input.next();
            //Convert option string to uppercase
            option = option.toUpperCase();

            switch (option) {
                case "100", "VFQ" -> {
                    AllQueues(queues, new_queues);
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                case "102", "ACQ" -> {
                    AddCustomer(queues);
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                case "101", "VEQ" -> {
                    EmptyQueues(queues);
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                case "103", "RCQ" -> {
                    RemoveCustomer(queues);
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                case "104", "PCQ" -> {
                    burger_count = RemoveServedCustomer(queues, burger_count);
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                case "105", "VCS" -> {
                    AlphabeticalOrdered(queues);
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                case "106", "SPD" -> {
                    WriteFile(queues);
                    System.out.println("Customer details successfully added to the file...");
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                case "107", "LPD" -> {
                    LoadFile();
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                case "108", "STK" -> {
                    System.out.println("Remaining burgers: " + burger_count);
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                case "109", "AFK" -> {
                    burger_count = AddBurgers(burger_count);
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                case "999", "EXT" -> {
                    DeleteFile();
                    System.out.println("<<<***** Good Bye!! *****>>>\n        ************        ");
                    repeat = false;
                }
                case "000", "MNU" ->{
                    for (String choice : menu) {
                        System.out.println(choice);
                    }
                }
                default -> System.out.println("Invalid Entry. Read menu carefully..");
            }
        }
    }

    // This method takes the original queues array and creates a new_queues array
    // where each element is marked as "X" if it is null in the original array,
    // or "O" if it is not null.
    public static void AllQueues(String[][] queues, String[][] new_queues) {

        // Iterate over the original queues array
        for(int j = 0 ; j < queues.length ; j++) {
            for (int i = 0; i < queues[j].length; i++) {
                // Check if an element in the queue is null
                if (Objects.equals(queues[j][i], null)) {
                    new_queues[j][i] = "X"; // Mark it as "X" in the new_queues array
                } else {
                    new_queues[j][i] = "O"; // Otherwise mark it as "O"
                }
            }
        }

        System.out.println("********************");
        System.out.println("*     Cashiers     *");
        System.out.println("********************");

        // Iterate over the new_queues array to print the queues
        for(int i = 0 ; i < new_queues.length-2 ; i++){
            for(int j = 0 ; j < new_queues[i].length ; j++){
                // Check if an element in the queue is null
                if (new_queues[i][j] == null){
                    new_queues[i][j] = " "; // If null, replace with a space
                }
                if (new_queues[i+1][j] == null){
                    new_queues[i+1][j] = " "; // If null, replace with a space
                }
                if (new_queues[i+2][j] == null){
                    new_queues[i+2][j] = " "; // If null, replace with a space
                }
                System.out.println("  " + new_queues[i][j] + "      " + new_queues[i+1][j] + "       " + new_queues[i+2][j]);
            }
        }
        System.out.println("X - Not Occupied\nO - Occupied");
    }

    // This method checks the queues array to determine if all cashiers are busy or if there are any empty queues.
    // If all cashiers are busy, it prints a message indicating so.
    // Otherwise, it prints the queue numbers (cashier numbers) where queues are empty.
    public static void EmptyQueues(String[][] queues){

        // Check if specific positions in the queues array are not null,
        // indicating that all cashiers are busy.
        if (queues[0][1] != null && queues[1][2] != null && queues[2][4] != null) {
            System.out.println("Wait..! \nAll cashiers are busy right now.");
        } else {
            // Iterate over the queues array to find empty queues
            for (int i = 0; i < queues.length; i++) {
                for (int j = 0; j < queues[i].length; j++) {
                    // Check if an element in the queue is null
                    if (queues[i][j] == null) {
                        System.out.println("Queue " + (i + 1) + " (Cashier " + (i + 1) + ")");
                        break; // Exit the inner loop once an empty queue is found
                    }
                }
            }
        }
    }

    /*
    This method is used to add a new customer to queues array.
    @param queues The array representing the queues for each cashier.
    */
    public static void AddCustomer(String[][] queues) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter cashier number : ");
            int cashier = input.nextInt();
            switch (cashier) {
                case 1, 2, 3 -> {
                    System.out.print("Enter Customer's name : ");
                    String name = input.next();
                    name = name.toUpperCase();
                    for (int j = 0; j < queues[cashier - 1].length; j++) {
                        //Checking whether queue is already full or not
                        if (queues[cashier - 1][j] == null) {
                            queues[cashier - 1][j] = name;
                            System.out.println("Customer " + name +  " added successfully...");
                            break;
                        } else if (queues[cashier - 1][queues[cashier - 1].length - 1] != null) {
                            System.out.println("Queue " + (cashier) +" (Cashier " + cashier + ") is full..!");
                            //Giving available queues at this time
                            System.out.println("< Available queues >");
                            EmptyQueues(queues); //show empty queues at this time
                            break;
                        }
                    }
                }
                default -> {
                    System.out.println("Invalid entry. Cashier " + cashier + " does not exists..");
                    AddCustomer(queues);
                }
            }
        }catch (Exception NumberFormatException){
            System.out.println("Invalid entry. Integer required..");
            AddCustomer(queues);
        }
    }

    /*
    This method is used to remove a customer from queue.
    It get customer's queue and exact position of the queues that customer in as an inputs
    @param queues, array represent the queues for each cashier
    */
    public static void RemoveCustomer(String[][] queues){
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter queue: ");
            String lineInput = input.nextLine();

            int line;
            try {
                line = Integer.parseInt(lineInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry. Integer required.");
                RemoveCustomer(queues);
                return;
            }
            switch (line) {
                case 1, 2, 3 -> {
                    try {
                        System.out.print("Enter position : ");
                        String positionInput = input.nextLine();

                        int position;
                        try{
                            position = Integer.parseInt(positionInput);
                        }catch (NumberFormatException ex){
                            System.out.println("Invalid entry. Integer required..");
                            RemoveCustomer(queues);
                            return;
                        }
                        //checks customer's position that gave by user is in the queue or not.
                        if (queues[line - 1].length + 1 > position && queues[line - 1][position - 1] != null) {
                            for (; position < queues[line - 1].length; position++) {
                                //Replace user given index by value of next index
                                queues[line - 1][position - 1] = queues[line - 1][position];
                            }
                            //Replace last index in queue with null
                            queues[line - 1][queues[line - 1].length - 1] = null;
                            System.out.println("Customer removed successfully...");

                        } else if (position > queues[line - 1].length) {
                            System.out.println("Exceeded position. Try again..");
                            RemoveCustomer(queues);

                        } else {
                            System.out.println("Customer does not exists..");
                        }
                    }catch (NumberFormatException ex){
                        System.out.println("Invalid entry. Integer required..");
                    }
                }
                default -> {
                    System.out.println("Invalid entry. Cashier " + line + " does not exists..");
                    RemoveCustomer(queues);
                }
            }
        }catch (NumberFormatException ex){
            System.out.println("Invalid entry. Integer required..");
            RemoveCustomer(queues);
        }
    }

    /*
    This method is used to remove a served customer
    @param queues, the array represent the queues for each cashier
    @burger count, the burger count at this time
    @return burger count, the burger count after served to the customer
     */
    public static int RemoveServedCustomer(String[][] queues, int burger_count){
        try {
            Scanner input = new Scanner (System.in);
            System.out.print("Enter queue : ");
            String lineInput = input.nextLine();

            int line;
            try {
                line = Integer.parseInt(lineInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry. Integer required.");
                burger_count = RemoveServedCustomer(queues,burger_count);
                return burger_count;
            }
            //checks if burger are in stock or out of stock right now
            if(burger_count > 0) {
                switch (line) {
                    case 1, 2, 3 -> {
                        if (burger_count > 15) {
                            if (queues[line - 1][0] != null) {
                                burger_count -= 5;
                                System.out.println("Customer removed successfully...");
                            } else {
                                System.out.println("Customer does not exists..");
                            }
                        //gives the warning message when burger count is less than or equals to 10
                        } else if (burger_count <= 15 && burger_count > 0) {
                            if (queues[line - 1][0] != null) {
                                if(burger_count-5 > 0) {
                                    burger_count -= 5;
                                    System.out.println("Customer removed successfully...");
                                    System.out.println("Warning! Low amount of burgers..\nRemaining burgers : " + burger_count);
                                }else{
                                    System.out.println("Low amount of Burgers. Can not provide amount of customer required");
                                }
                            } else {
                                System.out.println("Customer does not exists..");
                            }

                        } else if (burger_count == 0) {
                            System.out.println("Burger stock is empty...\nPlease add burgers before add customer to the system..");

                        }
                        //Replace user given index value by value of next index
                        for (int j = 1; j < queues[line - 1].length; j++) {
                            queues[line - 1][j - 1] = queues[line - 1][j];
                        }
                        //Replace last index in queue with null
                        queues[line - 1][queues[line - 1].length - 1] = null;
                    }
                    default -> {
                        System.out.println("Invalid entry. Cashier " + line + " doesn't exists..");
                        burger_count = RemoveServedCustomer(queues, burger_count);
                    }
                }
            }else{
                System.out.println("Burger stock is empty..\nYou can not remove customer..");
            }
        }catch (NumberFormatException ex){
            System.out.println("Invalid entry. Integer required..");
            burger_count = RemoveServedCustomer(queues, burger_count);
        }
        return burger_count;
    }

    /*
    This method is used to add burgers to current burger count
    @param burger_count, current burger count in stock
     */
    public static int AddBurgers(int burger_count){
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Burgers Amount : ");
            int new_burgers = input.nextInt();
            burger_count += new_burgers;
            //checking whether burger count is higher than 50
            if (burger_count > 50) {
                System.out.println("Maximum burger limit has reached..");
            //If burger count is more than 50, reassign burger count to 50
                burger_count = 50;
            }
        } catch (Exception e) {
            System.out.println("Invalid Entry.Integer Required...");
            AddBurgers(burger_count);
        }
        return burger_count;
    }

    /*
    This method is used to sort the names of all the customers in the queues into alphabetical order.
    @param queues The sorted_array representing the queues for each cashier.
    */
    public static void AlphabeticalOrdered(String[][] queues){
        // Create a new sorted_array to store the sorted names
        String[] sortedArray = new String[10];

        // Check if there are any customers in the queues
        if (queues[0][0] != null || queues[1][0] != null || queues[2][0] != null) {
            int index = 0;

            // Merge all the names from queues into sortedArray
            for (int i = 0; i < queues.length; i++) {
                for (int j = 0; j < queues[i].length; j++) {
                    if (queues[i][j] != null) {
                        sortedArray[index] = queues[i][j];
                        index++;
                    }
                }
            }

            // Sort the sortedArray in alphabetical order using selection sort
            for (int i = 0; i < sortedArray.length - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < sortedArray.length; j++) {
                    if (sortedArray[j] != null && (sortedArray[minIndex] == null || sortedArray[j].compareTo(sortedArray[minIndex]) < 0))  {
                        minIndex = j;
                    }
                }
                String temp = sortedArray[i];
                sortedArray[i] = sortedArray[minIndex];
                sortedArray[minIndex] = temp;
            }

            // Print the sorted names
            for (String name : sortedArray) {
                if (name != null) {
                    System.out.println(name);
                }
            }
        } else {
            System.out.println("Customers do not exist...");
        }
    }


    /*
    This method is used to write customer details which are customer name and queue number that customer in
    @param queues The array representing the queues for each cashier.
     */
    public static void WriteFile (String[][] queues){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("BurgerKing.txt"));
            for(int i = 0 ; i < queues.length ; i++){
                for(int j = 0 ; j < queues[i].length ; j++){
                    //check current position in the queue is not null
                    if(queues[i][j] != null){
                        //writes customer details to the file
                        writer.write(queues[i][j]+ " - Cashier " + (i+1) );
                        writer.newLine();
                    }
                }
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    /*
    This method is used to load customer details that were inserted to the file in WriteFile method
     */
    public static void LoadFile (){
        try {
            File reader = new File("BurgerKing.txt");
            Scanner myReader = new Scanner(reader);
            while (myReader.hasNextLine()) {
                String details = myReader.nextLine();
                System.out.println(details);
            }
            System.out.println("Customer details loaded successfully...");
            myReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    /*
    This method is used to delete the file that created in WriteFile method
     */
    public static void DeleteFile(){
        File deleter = new File("BurgerKing.txt");
        deleter.delete();
    }
}


