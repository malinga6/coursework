/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vu.utms.model;

/**
 *
 * @author phill
 */
public class TransportOfficer extends User {

    public TransportOfficer() { }
    public TransportOfficer(int id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public void requestTransport(Route r) {
        System.out.println("Transport Officer " + getUsername()
            + " manages booking for route " + r.getName());
    }
}
