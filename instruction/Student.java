/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vu.utms.model;

/**
 *
 * @author phill
 */
public class Student extends User {
    private String matricNumber;

    public Student() { }
    public Student(int id, String username, String password, String matricNumber) {
        super(id, username, password);
        this.matricNumber = matricNumber;
    }

    public String getMatricNumber() {
        return matricNumber;
    }
    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    @Override
    public void requestTransport(Route r) {
        System.out.println("Student " + getUsername()
            + " (Matric No: " + matricNumber + ") requests seat on route " + r.getName());
    }
}