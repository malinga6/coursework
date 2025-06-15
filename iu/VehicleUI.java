/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vu.utms.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import vu.utms.dao.VehicleDAO;
import vu.utms.model.Vehicle;

/**
 *
 * @author phill
 */
public class VehicleUI extends JPanel {
    private final VehicleDAO dao = new VehicleDAO();
    private final DefaultTableModel model;
    private final JTable table;
    private final JButton btnRefresh = new JButton("Refresh");
    private final JButton btnAdd     = new JButton("Add");
    private final JButton btnEdit    = new JButton("Edit");
    private final JButton btnDelete  = new JButton("Delete");

    public VehicleUI() {
        setLayout(new BorderLayout(5,5));
        setBorder(new EmptyBorder(20,20,20,20));
        
        model = new DefaultTableModel(
            new String[]{"ID","Plate","Capacity","Type"},0) {
            @Override public boolean isCellEditable(int r, int c){return false;}
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bp = new JPanel();
        bp.add(btnRefresh); bp.add(btnAdd);
        bp.add(btnEdit);    bp.add(btnDelete);
        add(bp, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());
        btnAdd    .addActionListener(e -> addVehicle());
        btnEdit   .addActionListener(e -> editVehicle());
        btnDelete .addActionListener(e -> deleteVehicle());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<Vehicle> list = dao.getAllVehicles();
        for (Vehicle v : list) {
            model.addRow(new Object[]{
                v.getId(), v.getPlateNumber(),
                v.getCapacity(), v.getType()
            });
        }
    }

    private void addVehicle() {
        VehicleDialog dlg = new VehicleDialog(null);
        if (dlg.showDialog(this)) {
            dao.addVehicle(dlg.getVehicle());
            loadData();
        }
    }

    private void editVehicle() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        int id = (int)model.getValueAt(row, 0);
        Vehicle v = dao.getVehicleById(id);
        if (v == null) return;
        VehicleDialog dlg = new VehicleDialog(v);
        if (dlg.showDialog(this)) {
            dao.updateVehicle(dlg.getVehicle());
            loadData();
        }
    }

    private void deleteVehicle() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        int id = (int)model.getValueAt(row,0);
        if (JOptionPane.showConfirmDialog(this,
            "Delete vehicle "+id+"?", "Confirm",
            JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            dao.deleteVehicle(id);
            loadData();
        }
    }
}