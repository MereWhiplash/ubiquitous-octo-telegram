package intercom;

import intercom.customers.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static intercom.App.*;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    File file;

    private static final double LATITUDE = 53.2451022;
    private static final double LONGITUDE = -6.238335;


    List<Customer> createCustomers() {
        Customer customer1 = new Customer("Bob", 1, 50, 50);
        Customer customer2 = new Customer("Ann", 2, 50, 50);
        return List.of(customer1, customer2);
    }

    @BeforeEach
    void before() {
        file = new File("Test.txt");
    }

    @AfterEach
    void after() {
        file.deleteOnExit();
    }

    @Test
    void fileSelectedTrue() {
        assertTrue(fileSelected(file));
    }

    @Test
    void fileSelectedFalse() {
        assertFalse(fileSelected(null));
    }

    @Test
    void distanceBetweenTwoPointsExpectedRange() {
        assertEquals(1543.7166547203278, distanceBetweenTwoPoints(10.0, 10.0, 20.0, 20.0));
    }

    @Test
    void distanceBetweenTwoPointsMaxRange() {
        assertEquals(9120.356036722245, distanceBetweenTwoPoints(Double.MAX_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_VALUE));
    }

    @Test
    void calculateLocalCustomersNoneInRange() {
        List<Customer> customers = createCustomers();
        assertEquals(emptyList(), calculateLocalCustomersToLocation(customers, HOME_LATITUDE, HOME_LONGITUDE));
    }

    @Test
    void calculateLocalCustomersOneInRange() {
        List<Customer> customers = createCustomers();
        customers.get(0).setLongitude(LONGITUDE);
        customers.get(0).setLatitude(LATITUDE);
        assertEquals(List.of(customers.get(0)), calculateLocalCustomersToLocation(List.of(customers.get(0)), HOME_LATITUDE, HOME_LONGITUDE));
    }

    @Test
    void calculateLocalCustomersIsNull() {
        assertEquals(emptyList(), calculateLocalCustomersToLocation(null, HOME_LATITUDE, HOME_LONGITUDE));
    }

}
