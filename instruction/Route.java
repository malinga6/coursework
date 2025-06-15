/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vu.utms.model;

/**
 *
 * @author phill
 */
public class Route {
    private int id;
    private String name;
    private String origin;
    private String destination;
    private String time;  // e.g. "08:00"

    public Route() { }
    public Route(int id, String name, String origin, String destination, String time) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.destination = destination;
        this.time = time;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}