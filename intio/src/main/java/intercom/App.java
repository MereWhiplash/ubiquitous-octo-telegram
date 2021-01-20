package intercom;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import com.fasterxml.jackson.databind.ObjectMapper;

import intercom.customers.Customer;

/**
 * Hello world!
 */
public final class App {

    static JFileChooser fileChooser = new JFileChooser();

    private App() {
    }

    public static void main(String[] args) {
        filePicker();
    }

    public static void filePicker() {
        int filestream = fileChooser.showOpenDialog(null);
        if (filestream == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            List<String> customerStrings = readFileInputStrings(file);
            if (customerStrings.isEmpty()) {
                System.out.println("No Customers Found in Files");
            } else {
                createCustomerList(customerStrings);
            }

        }
    }

    private static List<String> readFileInputStrings(File file) {
        List<String> strings = new ArrayList<>();
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                strings.add(input.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        }

        return strings;
    }

    private static List<Customer> createCustomerList(List<String> customerStrings) {
        List<Customer> customers = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String customerString : customerStrings) {
            if (customerString.isEmpty() || customerString.length() <= 0) {
                continue;
            } else {
                try {
                    customers.add(objectMapper.readValue(customerString, Customer.class));
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }

        return customers;
    }
}
