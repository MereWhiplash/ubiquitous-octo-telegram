package intercom;

import com.fasterxml.jackson.databind.ObjectMapper;
import intercom.customers.Customer;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 * <p>
 * Requirements:
 * 1. Reads list of customers(customers.txt), as JSON, one per line.
 * 2. Return list of customers(customersInRange.txt) plain text, with ID + Name.
 * <p>
 * QoL:
 * File Picking, Output to console, Read from URL, Customizable Max Range, Set Home Location.
 */
public final class App {

    static JFileChooser fileChooser;

    //Home
    static double HOME_LATITUDE = 53.339428;
    static double HOME_LONGITUDE = -6.257664;

    private App() {
    }

    public static void main(String[] args) {
        int option = 0;
        while (true) {
            option = menu();
            switch (option) {
                case 1:
                    outputToTxt(pickFile());
                    break;
                case 2:
                    outputToConsole(pickFile());
                    break;
                case 3:
                    inputLatitude();
                    inputLongitude();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Please enter a valid option.");

            }
        }
    }

    private static int menu() {
        Scanner input = new Scanner(System.in);
        String homeBase = String.format("-- lat: %s -- long: %s --", HOME_LATITUDE, HOME_LONGITUDE);

        System.out.println("----Intercom Hookups----");
        System.out.println("------------------------");
        System.out.println(homeBase);
        System.out.println("1 - Select List and Output file to .txt");
        System.out.println("2 - Select List and Output to Console");
        System.out.println("3 - Input Latitude and Longitude");
        System.out.println("4 - Quit");

        while (!input.hasNextInt()) {
            System.out.println("Please enter a integer option. \n");
            input.next();
        }
        return input.nextInt();
    }

    private static void outputToConsole(File file) {
        if (!fileSelected(file)) {
            System.out.println("File not selected.");
        } else {
            List<Customer> customers = calculateLocalCustomersToLocation(readFileAsCustomers(file), HOME_LATITUDE, HOME_LONGITUDE);
            if (customers.isEmpty()) {
                System.out.println("Customers file did not contain users in range.");
                return;
            }
            for (Customer customer : customers) {
                System.out.println(customer.toString());
            }
        }

    }

    private static void inputLatitude() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a Latitude option. \n");
        while (!input.hasNextDouble()) {
            input.next();
        }
        double latitude = input.nextDouble();
        if (!isValidLatitude(latitude)){
            inputLatitude();
        }else{
            HOME_LATITUDE = latitude;
        }
    }

    private static void inputLongitude(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a Longitude option. \n");
        while (!input.hasNextDouble()) {
            input.next();
        }
        double longitude = input.nextDouble();
        if (!isValidLongitude(longitude)){
            inputLongitude();
        }else{
            HOME_LONGITUDE = longitude;
        }
    }

    private static void outputToTxt(File file) {
        if (!fileSelected(file)) {
            System.out.println("File not selected.");
        } else {
            List<Customer> customers = calculateLocalCustomersToLocation(readFileAsCustomers(file), HOME_LATITUDE, HOME_LONGITUDE);
            writeCustomersToFile(customers);
        }
    }

    private static void writeCustomersToFile(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("Customers file did not contain users in range.");
            return;
        }
        try {
            File customerFile = new File("customersInRange.txt");
            if (customerFile.createNewFile()) {
                System.out.println("File created: " + customerFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter fileWriter = new FileWriter("customersInRange.txt");
            for (Customer c : customers) {
                fileWriter.write("Name: " + c.getName() + ", UserID: " + c.getUserId() + "\n");
            }
            fileWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static FilenameFilter setupFileFilter() {
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        };

        return filenameFilter;
    }

    public static boolean isValidLongitude(double value){
        return Math.abs(value) <= 180;
    }

    public static boolean isValidLatitude(double value){
        return Math.abs(value) <= 90;
    }

    public static boolean fileSelected(File file) {
        if (file == null) {
            System.out.println("Please Select a file...");
            return false;
        }
        return true;
    }

    public static double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        //Convert to Radians
        x1 = Math.toRadians(x1);
        y1 = Math.toRadians(y1);
        x2 = Math.toRadians(x2);
        y2 = Math.toRadians(y2);
        //Great Circle Distance in Radians
        double angle = Math.acos(Math.sin(x1) * Math.sin(x2)
            + Math.cos(x1) * Math.cos(x2) * Math.cos(y1 - y2));
        //Return to Degrees
        angle = Math.toDegrees(angle);
        //Distance in Nautical Miles
        double distanceInNauticalMiles = 60 * angle;
        //1.852 is the conversion to kilometers. We arent on a cruise after all. Intercom cruise? Sold.
        return distanceInNauticalMiles * 1.852;
    }

    private static File pickFile() {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        FileDialog fileDialog = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
        fileDialog.setFilenameFilter(setupFileFilter());
        fileDialog.setVisible(true);
        String filename = fileDialog.getFile();

        if (filename == null) {
            System.out.println("You cancelled the choice");
        }
        else{
                System.out.println("You chose " + filename);
                return fileDialog.getFiles()[0];
        }
        return null;
    }

    public static List<Customer> calculateLocalCustomersToLocation(List<Customer> customers, double homeLatitude, double homeLongitude) {
        if (customers == null) {
            return Collections.emptyList();
        }

        customers = customers.stream()
            .filter(customer -> distanceBetweenTwoPoints(homeLatitude, homeLongitude, customer.getLatitude(), customer.getLongitude()) < 100.00)
            .collect(Collectors.toList());
        Collections.sort(customers);
        return customers;
    }

    private static List<Customer> readFileAsCustomers(File file) {
        List<String> strings = new ArrayList<>();
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                strings.add(input.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        }

        return createCustomerList(strings);
    }

    private static List<Customer> createCustomerList(List<String> customerStrings) {
        List<Customer> customers = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String customerString : customerStrings) {
            if (customerString.isEmpty()) {
                return Collections.emptyList();
            } else {
                try {
                    customers.add(objectMapper.readValue(customerString, Customer.class));
                } catch (Exception e) {
                    System.err.println("File not formatted correctly: " + e.getLocalizedMessage());
                }
            }
        }
        return customers;
    }
}
