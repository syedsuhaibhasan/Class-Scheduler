package forms;

import dao.connectionprovider;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class TimetableFrameEdit extends JFrame {
    private static final Map<String, String> INSTRUCTOR_MAP = new LinkedHashMap<>();
    static {
        INSTRUCTOR_MAP.put("CS",    "Communication Skills  – Rafiq");
        INSTRUCTOR_MAP.put("LA",    "Linear Algebra       – Sidra");
        INSTRUCTOR_MAP.put("OOP",   "Object Oriented Prog – Manan");
        INSTRUCTOR_MAP.put("IS",    "Islamic Studies      – Nazia");
        INSTRUCTOR_MAP.put("DLD",   "Digital Logic Design – Nusrat");
        INSTRUCTOR_MAP.put("DLD(L)","Digital Logic Lab    – Anum");
        INSTRUCTOR_MAP.put("FM-II", "Foundation Maths II  – Memona");
        INSTRUCTOR_MAP.put("PS",    "Pakistan Studies     – Zunera");
    }

    private final String sectionCode;
    private final DefaultTableModel model;
    private final JTable table;

    public TimetableFrameEdit(String sectionCode) {
        super("Section " + sectionCode + " Timetable");
        this.sectionCode = sectionCode;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5,5));

        // --- Top toolbar
        JToolBar toolbar = new JToolBar();
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(e -> dispose());
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> saveToDatabase());
        JButton btnInstructors = new JButton("View Instructors");
        btnInstructors.addActionListener(e -> showInstructorDialog());
        toolbar.add(btnExit);
        toolbar.addSeparator();
        toolbar.add(new JLabel(" Section: " + sectionCode + " "));
        toolbar.addSeparator();
        toolbar.add(btnInstructors);
        toolbar.add(btnSave);
        add(toolbar, BorderLayout.NORTH);

        // --- Table model & JTable
        String[] cols = { "Time", "Mon", "Tue", "Wed", "Thu", "Fri" };
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) {
                return col > 0;  // only days editable
            }
        };
        table = new JTable(model);
        table.setRowHeight(50);
        table.getTableHeader().setReorderingAllowed(false);
        // center renderer
        DefaultTableCellRenderer ctr = new DefaultTableCellRenderer();
        ctr.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, ctr);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // load & show
        loadFromDatabase();
    }

    private void loadFromDatabase() {
        model.setRowCount(0);
        String sql = "SELECT slot_time, slot1, slot2, slot3, slot4, slot5 "
                   + "FROM  ClassschedulerJFramebd.timetable WHERE section=? ORDER BY slot_time";
        try (Connection con = connectionprovider.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sectionCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vector<String> row = new Vector<>();
                    for (int i = 1; i <= 6; i++)
                        row.add(rs.getString(i));
                    model.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Load failed: " + ex.getMessage(),
                "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToDatabase() {
        String sql = "UPDATE  ClassschedulerJFramebd.timetable "
                   + "SET slot1=?, slot2=?, slot3=?, slot4=?, slot5=? "
                   + "WHERE section=? AND slot_time=?";
        try (Connection con = connectionprovider.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            for (int r = 0; r < model.getRowCount(); r++) {
                for (int c = 1; c <= 5; c++)
                    ps.setString(c, (String)model.getValueAt(r, c));
                ps.setString(6, sectionCode);
                ps.setString(7, (String)model.getValueAt(r, 0));
                ps.addBatch();
            }
            int[] counts = ps.executeBatch();
            con.commit();

            int updated = 0;
            for (int x : counts) if (x > 0) updated += x;
            JOptionPane.showMessageDialog(this,
                "Saved! (" + updated + " cells updated.)");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Save failed: " + ex.getMessage(),
                "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showInstructorDialog() {
        StringBuilder sb = new StringBuilder("Instructors:\n\n");
        INSTRUCTOR_MAP.forEach((k,v) -> sb.append(k).append(" → ").append(v).append("\n"));
        JOptionPane.showMessageDialog(this, 
            new JTextArea(sb.toString()), 
            "View Instructors", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            for (String sec : new String[]{"A","B","C","D"})
                new TimetableFrameEdit(sec).setVisible(true);
        });
    }
}
