/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vu.utms.model;

/**
 *
 * @author phill
 */
/**
 * Single interface to demonstrate service, tracking,
 * and scheduling behaviour on all vehicles.
 */
public interface VehicleOperations {
    void service();
    void trackLocation();
    void scheduleRoute(Route r);
}