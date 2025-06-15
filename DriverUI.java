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
import vu.utms.dao.DriverDAO;
import vu.utms.model.Driver;

/**
 *
 * @author phill
 */
public class DriverUI extends JPanel {
    private final DriverDAO dao = new DriverDAO();
    private final DefaultTableModel model;
    private final JTable table;
    private final JButton btnRefresh = new JButton("Refresh");
    private final JButton btnAdd     = new JButton("Add");
    private final JButton btnEdit    = new JButton("Edit");
    private final JButton btnDelete  = new JButton("Delete");

    public DriverUI() {
        setLayout(new BorderLayout(5,5));
        setBorder(new EmptyBorder(20,20,20,20));
        model = new DefaultTableModel(new String[]{"ID","Name","License"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bp = new JPanel();
        bp.add(btnRefresh); bp.add(btnAdd);
        bp.add(btnEdit);    bp.add(btnDelete);
        add(bp, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());
        btnAdd    .addActionListener(e -> addDriver());
        btnEdit   .addActionListener(e -> editDriver());
        btnDelete .addActionListener(e -> deleteDriver());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<Driver> list = dao.getAllDrivers();
        for (Driver d : list) {
            model.addRow(new Object[]{
                d.getId(), d.getName(), d.getLicenseNumber()
            });
        }
    }

    private void addDriver() {
        DriverDialog dlg = new DriverDialog(null);
        if (dlg.showDialog(this)) {
            dao.addDriver(dlg.getDriver());
            loadData();
        }
    }

    private void editDriver() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        int id = (int)model.getValueAt(row, 0);
        Driver d = dao.getDriverById(id);
        if (d == null) return;
        DriverDialog dlg = new DriverDialog(d);
        if (dlg.showDialog(this)) {
            dao.updateDriver(dlg.getDriver());
            loadData();
        }
    }

    private void deleteDriver() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        int id = (int)model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this,
            "Delete driver "+id+"?", "Confirm",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dao.deleteDriver(id);
            loadData();
        }
    }
}