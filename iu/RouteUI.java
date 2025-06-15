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
import vu.utms.dao.RouteDAO;
import vu.utms.model.Route;
import vu.utms.model.TransportOfficer;
import vu.utms.model.User;

/**
 *
 * @author phill
 */
public class RouteUI extends JPanel {
    private final RouteDAO dao = new RouteDAO();
    private final User currentUser;
    private final DefaultTableModel model;
    private final JTable table;

    private final JButton btnAdd    = new JButton("Add");
    private final JButton btnEdit   = new JButton("Edit");
    private final JButton btnDelete = new JButton("Delete");
    private final JButton btnRefresh= new JButton("Refresh");
    private final JButton btnRequest= new JButton("Request");

    public RouteUI(User user) {
        this.currentUser = user;
        setLayout(new BorderLayout(5,5));
        setBorder(new EmptyBorder(20,20,20,20));

        // Table setup
        String[] cols = {"ID","Name","Origin","Destination","Time"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button panel
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnRefresh);
        if (user instanceof TransportOfficer) {
            btnPanel.add(btnAdd);
            btnPanel.add(btnEdit);
            btnPanel.add(btnDelete);
        } else {
            btnPanel.add(btnRequest);
        }
        add(btnPanel, BorderLayout.SOUTH);

        // Wire actions
        btnRefresh.addActionListener(e -> loadData());
        btnAdd    .addActionListener(e -> addRoute());
        btnEdit   .addActionListener(e -> editRoute());
        btnDelete .addActionListener(e -> deleteRoute());
        btnRequest.addActionListener(e -> requestRoute());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<Route> list = dao.getAllRoutes();
        for (Route r : list) {
            model.addRow(new Object[]{
                r.getId(), r.getName(),
                r.getOrigin(), r.getDestination(),
                r.getTime()
            });
        }
    }

    private void addRoute() {
        RouteDialog dlg = new RouteDialog(null);
        if (dlg.showDialog(this)) {
            dao.addRoute(dlg.getRoute());
            loadData();
        }
    }

    private void editRoute() {
        int row = table.getSelectedRow();
        if (row<0) return;
        Route r = new Route(
            (int)model.getValueAt(row,0),
            (String)model.getValueAt(row,1),
            (String)model.getValueAt(row,2),
            (String)model.getValueAt(row,3),
            (String)model.getValueAt(row,4)
        );
        RouteDialog dlg = new RouteDialog(r);
        if (dlg.showDialog(this)) {
            dao.updateRoute(dlg.getRoute());
            loadData();
        }
    }

    private void deleteRoute() {
        int row = table.getSelectedRow();
        if (row<0) return;
        int id = (int)model.getValueAt(row,0);
        if (JOptionPane.showConfirmDialog(this,
            "Delete route "+id+"?", "Confirm",
            JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            dao.deleteRoute(id);
            loadData();
        }
    }

    private void requestRoute() {
        int row = table.getSelectedRow();
        if (row<0) return;
        Route r = dao.getRouteById((int)model.getValueAt(row,0));
        currentUser.requestTransport(r);
        JOptionPane.showMessageDialog(this,
            "Requested: " + r.getName());
    }
}
