package vu.utms.model;

/**
 *
 * @author phill
 */
public abstract class User {
    
    /*
    Encapsulation is making the instance variables of a class private and providing public getter
    and setter methods to access and modify these variables.
    */
    private int id;
    private String username;
    private String password;

    /* Default constructor with no arguments. Will allow subclasses (or frameworks)
    to create a User without immediately supplying any data
    */
    public User() { }

    //constructor with parameters. Lets us create a fully populated User in one line
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Basic stub for login; in a real system you'd
     * check credentials against the database.
     */
    public void login() {
        System.out.println(username + " logged in.");
    }

    /**
     * Each user type must implement its own way
     * of requesting transport.
     * @param r
     */
    public abstract void requestTransport(Route r);
}


/*
-----OOP Principles in this script-----

Abstraction: User hides the “how” and just defines what a user can do.

Encapsulation: private fields + getters/setters protect internal state.

Inheritance: subclasses share fields/methods from User.

Polymorphism: the requestTransport(...) abstract method ensures each subclass customizes its behavior at runtime.
*/