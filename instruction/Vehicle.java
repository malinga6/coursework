package vu.utms.model;

/**
 *
 * @author phill
 */
public class Vehicle implements VehicleOperations {

    //enumeration is a special data type that represents a fixed set of constant values
    public enum Type { BUS, VAN }

    private int id;
    private String plateNumber;
    private int capacity;
    private Type type;
    private Driver assignedDriver;

    public Vehicle() { }
    public Vehicle(int id, String plateNumber, int capacity, Type type) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.capacity = capacity;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPlateNumber() {
        return plateNumber;
    }
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Driver getAssignedDriver() {
        return assignedDriver;
    }

    // vii) Method overloading for assignDriver
    public void assignDriver(Driver d) {
        this.assignedDriver = d;
        System.out.println("Assigned driver " + d.getName()
            + " to vehicle " + plateNumber);
    }
    public void assignDriver(Driver d, String shift) {
        this.assignedDriver = d;
        System.out.println("Assigned driver " + d.getName()
            + " on " + shift + " shift to vehicle " + plateNumber);
    }

    // v) Interface implementations
    @Override
    public void service() {
        System.out.println(type + " " + plateNumber + " is sent for service.");
    }
    @Override
    public void trackLocation() {
        System.out.println(type + " " + plateNumber + " current location: [lat,long] stub.");
    }
    @Override
    public void scheduleRoute(Route r) {
        System.out.println(type + " " + plateNumber
            + " scheduled on route " + r.getName());
    }
}