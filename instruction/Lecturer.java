/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vu.utms.model;

/**
 *
 * @author phill
 */
public class Lecturer extends User {
    private String staffId;

    public Lecturer() { }
    public Lecturer(int id, String username, String password, String staffId) {
        super(id, username, password);
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    @Override
    public void requestTransport(Route r) {
        System.out.println("Lecturer " + getUsername()
            + " (Staff ID: " + staffId + ") books priority seat on route " + r.getName());
    }
}
