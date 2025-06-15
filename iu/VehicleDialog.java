/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vu.utms.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import vu.utms.model.Vehicle;

/**
 *
 * @author phill
 */
public class VehicleDialog extends JDialog {
    private final JTextField txtId       = new JTextField(5);
    private final JTextField txtPlate    = new JTextField(10);
    private final JTextField txtCapacity = new JTextField(5);
    private final JComboBox<Vehicle.Type> cmbType =
        new JComboBox<>(Vehicle.Type.values());
    private boolean confirmed = false;

    public VehicleDialog(Vehicle v) {
        setModal(true);
        setTitle(v == null ? "Add Vehicle" : "Edit Vehicle");
        JPanel p = new JPanel(new GridLayout(4,2,5,5));
        p.setBorder(new EmptyBorder(20,20,20,20));
        p.add(new JLabel("ID:"));       p.add(txtId);
        p.add(new JLabel("Plate#:"));   p.add(txtPlate);
        p.add(new JLabel("Capacity:")); p.add(txtCapacity);
        p.add(new JLabel("Type:"));     p.add(cmbType);
        add(p, BorderLayout.CENTER);

        JButton ok = new JButton("OK"), cancel = new JButton("Cancel");
        JPanel bp = new JPanel(); bp.add(ok); bp.add(cancel);
        add(bp, BorderLayout.SOUTH);

        if (v != null) {
            txtId.setText(String.valueOf(v.getId()));
            txtId.setEnabled(false);
            txtPlate.setText(v.getPlateNumber());
            txtCapacity.setText(String.valueOf(v.getCapacity()));
            cmbType.setSelectedItem(v.getType());
        }

        ok.addActionListener(e -> { confirmed = true; dispose(); });
        cancel.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(null);
    }

    public boolean showDialog(Component parent) {
        setLocationRelativeTo(parent);
        setVisible(true);
        return confirmed;
    }

    public Vehicle getVehicle() {
        int id = Integer.parseInt(txtId.getText().trim());
        String plate = txtPlate.getText().trim();
        int cap = Integer.parseInt(txtCapacity.getText().trim());
        Vehicle.Type type = (Vehicle.Type)cmbType.getSelectedItem();
        return new Vehicle(id, plate, cap, type);
    }
}