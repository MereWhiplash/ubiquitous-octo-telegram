package intercom.customers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer implements Comparable<Customer> {

    private double latitude;
    private double longitude;
    private int userId;
    private String name;

    public Customer() {

    }

    public Customer(String name, int userId, double longitude, double latitude) {
        setName(name);
        setUserId(userId);
        setLongitude(longitude);
        setLatitude(latitude);
    }

    @Override
    public String toString() {
        return "UserID: " + getUserId() + " Name: " + getName();
    }

    @JsonProperty("latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int compareTo(Customer o) {
        return this.userId - o.getUserId();
    }

}
