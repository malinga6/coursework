/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vu.utms.ui;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import vu.utms.dao.UserDAO;
import vu.utms.model.Lecturer;
import vu.utms.model.Student;
import vu.utms.model.User;

/**
 *
 * @author phill
 */
public class UserUI extends JPanel {
    private final UserDAO dao = new UserDAO();
    private final DefaultTableModel model;
    private final JTable table;
    private final JButton btnRefresh = new JButton("Refresh");
    private final JButton btnAdd     = new JButton("Add");
    private final JButton btnEdit    = new JButton("Edit");
    private final JButton btnDelete  = new JButton("Delete");

    public UserUI() {
        setLayout(new BorderLayout(5,5));
        setBorder(new EmptyBorder(20,20,20,20));
        
        model = new DefaultTableModel(
            new String[]{"ID","Username","Role","Matric#","StaffID"}, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bp = new JPanel();
        bp.add(btnRefresh); bp.add(btnAdd);
        bp.add(btnEdit);    bp.add(btnDelete);
        add(bp, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());
        btnAdd    .addActionListener(e -> addUser());
        btnEdit   .addActionListener(e -> editUser());
        btnDelete .addActionListener(e -> deleteUser());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<User> list = dao.getAllUsers();
        for (User u : list) {
            String role = u.getClass().getSimpleName();
            String mat = u instanceof Student ? ((Student)u).getMatricNumber() : "";
            String stf = u instanceof Lecturer ? ((Lecturer)u).getStaffId() : "";
            model.addRow(new Object[]{
                u.getId(), u.getUsername(), role, mat, stf
            });
        }
    }

    private void addUser() {
        UserDialog dlg = new UserDialog(null);
        if (dlg.showDialog(this)) {
            dao.addUser(dlg.getUser());
            loadData();
        }
    }

    private void editUser() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        int id = (int)model.getValueAt(row,0);
        User u = dao.getUserById(id);
        if (u == null) return;
        UserDialog dlg = new UserDialog(u);
        if (dlg.showDialog(this)) {
            dao.updateUser(dlg.getUser());
            loadData();
        }
    }

    private void deleteUser() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        int id = (int)model.getValueAt(row,0);
        if (JOptionPane.showConfirmDialog(this,
            "Delete user "+id+"?", "Confirm",
            JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            dao.deleteUser(id);
            loadData();
        }
    }
}
