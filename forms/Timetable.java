/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Syeds
 */

/**
 * TimetableFrame shows a compact timetable (short form + room only).
 * A “View Instructors” button opens a dialog listing each course’s instructor.
 */
public class Timetable extends JFrame {

    // Mapping from short form (e.g. "CS") to full course name + instructor
    private static final Map<String, String> INSTRUCTOR_MAP = new LinkedHashMap<>();
    static {
        INSTRUCTOR_MAP.put("CS",  "Communication Skills  – Sir Rafique");
        INSTRUCTOR_MAP.put("LA",  "Linear Algebra  – Dr. Sidra Khan");
        INSTRUCTOR_MAP.put("OOP", "Object Oriented Programming  – Ms. Navera");
        INSTRUCTOR_MAP.put("IS",  "Islamic Studies  – Dr. Nazia");
        INSTRUCTOR_MAP.put("DLD", "Digital Logic Design  – Ms. Nusrat");
        INSTRUCTOR_MAP.put("DLD(L)", "Digital Logic Design Lab  – Dr. Anum");
        INSTRUCTOR_MAP.put("FM-II", "Foundation Maths-II  – Dr. Maria");
        INSTRUCTOR_MAP.put("PS", "Pakistan Studies  – Ms. Zunera Memon");
        // Add additional short forms & instructors here if needed
    }

    public Timetable(String sectionCode) {
        super("Timetable – Section " + sectionCode);
        setUndecorated(true);
        initFrame(sectionCode);
    }

        private void initFrame(String sectionCode) {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(750, 500);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(5, 5));

    // 1) Top panel with Section Label, Exit, and View Instructors
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(new Color(230, 230, 250));
    topPanel.setPreferredSize(new Dimension(750, 50));

    // Left side: Section label
    JLabel lblSection = new JLabel("Timetable – Section " + sectionCode);
    lblSection.setFont(new Font("SansSerif", Font.BOLD, 18));
    lblSection.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
    topPanel.add(lblSection, BorderLayout.WEST);

    // Right side: Exit and Instructor buttons
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    JButton btnViewInstructors = new JButton("View Instructors");
    btnViewInstructors.setFont(new Font("SansSerif", Font.BOLD, 12));
    btnViewInstructors.addActionListener(e -> showInstructorDialog());

    JButton btnExit = new JButton("Exit");
    btnExit.setFont(new Font("SansSerif", Font.BOLD, 12));
    btnExit.setForeground(Color.RED);
    btnExit.addActionListener(e -> dispose());

    rightPanel.setOpaque(false);
    rightPanel.add(btnViewInstructors);
    rightPanel.add(btnExit);
    topPanel.add(rightPanel, BorderLayout.EAST);

    add(topPanel, BorderLayout.NORTH);

    // 2) Table setup (no change to this part from your code)
    String[] columns = { "Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
    String[][] slotTimes = {
        { "9:00–9:55",    "",      "",    "",    "",    "" },
        { "10:00–10:55",  "",      "",    "",    "",    "" },
        { "11:00–11:55",  "",      "",    "",    "",    "" },
        { "12:00–12:55",  "",      "",    "",    "",    "" },
        { "1:00–1:55",    "",      "",    "",    "",    "" },
        { "2:00–2:55",    "",      "",    "",    "",    "" },
        { "3:00–3:55",    "",      "",    "",    "",    "" },
        { "4:00–4:55",    "",      "",    "",    "",    "" }
    };

    DefaultTableModel model = new DefaultTableModel(slotTimes, columns) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };

    switch (sectionCode) {
        case "A": fillSectionA(model); break;
        case "B": fillSectionB(model); break;
        case "C": fillSectionC(model); break;
        case "D": fillSectionD(model); break;
    }

    JTable table = new JTable(model);
    table.setRowHeight(60);
    table.setFont(new Font("SansSerif", Font.PLAIN, 14));
    table.setIntercellSpacing(new Dimension(1, 1));
    table.setGridColor(new Color(220, 220, 220));
    table.setShowGrid(true);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setResizingAllowed(false);
    table.setRowSelectionAllowed(false);
    table.setCellSelectionEnabled(false);

    DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer)
        table.getTableHeader().getDefaultRenderer();
    headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
    table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 40));

    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
    cellRenderer.setVerticalAlignment(SwingConstants.CENTER);
    cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    cellRenderer.setFont(new Font("SansSerif", Font.PLAIN, 14));
    table.setDefaultRenderer(Object.class, cellRenderer);

    JScrollPane scroll = new JScrollPane(table);
    add(scroll, BorderLayout.CENTER);
}


    private void showInstructorDialog() {
        // Build a text area with each “short form – full instructor” on its own line
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("SansSerif", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();
        sb.append("Course Instructors:\n\n");
        for (Map.Entry<String, String> entry : INSTRUCTOR_MAP.entrySet()) {
            sb.append(entry.getKey())
              .append("  →  ")
              .append(entry.getValue())
              .append("\n\n");
        }
        area.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Instructor List",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    private void fillSectionA(DefaultTableModel m) {
        // Slot 1
        m.setValueAt("FMII-M101",  0, 1); // Foundation Math II, Room 101
        m.setValueAt("CS-M101",    0, 2); // Communication Skills, Room 101
        m.setValueAt("CS-M101",    0, 3);
        m.setValueAt("DLD-M101",   0, 4); // Digital Logic Design, Room 101
        m.setValueAt("OOP-T4",     0, 5); // OOP Lab, T4

        // Slot 2
        m.setValueAt("PS-M101",    1, 1); // Pakistan Studies, Room 101
        m.setValueAt("PS-M101",    1, 2);
        m.setValueAt("IS-M101",    1, 3); // Islamic Studies, Room 101
        m.setValueAt("DLD(L)-M101",1, 4); // DLD Lab, Room 101
        m.setValueAt("LA-M101",    1, 5); // Linear Algebra, Room 101

        // Slot 3
        m.setValueAt("DLD-M101",   2, 1);
        m.setValueAt("LA-M101",    2, 2);
        m.setValueAt("OOP-M101",   2, 3);
        m.setValueAt("OOP-M101",   2, 4);
        m.setValueAt("IS-M101",    2, 5);

        // Slot 4
        m.setValueAt("OOP-M101",   3, 1);
        m.setValueAt("OOP-M101",   3, 2);
        m.setValueAt("IS-M101",    3, 3);
        m.setValueAt("LA-M101",    3, 4);
        m.setValueAt("LA-M101",    3, 5);

        // Slot 5: Lunch—leave blank

        // Slot 6
        m.setValueAt("DLD(L)-M101", 5, 1);
        m.setValueAt("ESD-M101",    5, 2); // Embedded System Design, Room 101
        // Wednesday, Thursday, Friday slots 6 remain blank

        // Slot 7
        m.setValueAt("OOP-M101",   6, 1);
        m.setValueAt("OOP-M101",   6, 2);
        m.setValueAt("OOP-M101",   6, 3);
        m.setValueAt("IS-M101",    6, 4);
        m.setValueAt("LA-M101",    6, 5);

        // Slot 8
        m.setValueAt("LA-M101",    7, 1);
        m.setValueAt("DLD-M101",   7, 2);
        m.setValueAt("DLD-M101",   7, 3);
        m.setValueAt("DLD(L)-M101",7, 4);
        m.setValueAt("ESD-M101",   7, 5);
    }

    private void fillSectionB(DefaultTableModel m) {
        m.setValueAt("LA-M101",    0, 1);
        m.setValueAt("IS-M101",    0, 2);
        m.setValueAt("IS-M101",    0, 3);
        m.setValueAt("FMII-M101",  0, 4);
        m.setValueAt("PS-M101",    0, 5);

        m.setValueAt("PS-M101",    1, 1);
        m.setValueAt("CS-M101",    1, 2);
        m.setValueAt("CS-M101",    1, 3);
        m.setValueAt("OOP-T4",     1, 4);
        m.setValueAt("OOP-M101",   1, 5);

        m.setValueAt("OOP-M101",   2, 1);
        m.setValueAt("LA-M101",    2, 2);
        m.setValueAt("DLD-M101",   2, 3);
        m.setValueAt("DLD-M101",   2, 4);
        m.setValueAt("DLD(L)-M101",2, 5);

        m.setValueAt("DLD(L)-M101",3, 1);
        m.setValueAt("DLD-M101",   3, 2);
        m.setValueAt("CS-M101",    3, 3);
        m.setValueAt("PS-M101",    3, 4);
        m.setValueAt("PS-M101",    3, 5);

        // Slot 5: Lunch

        m.setValueAt("LA-M101",    5, 1);
        m.setValueAt("DLD(L)-T4",  5, 2);
        // Wednesday & Thursday slot 6 blank
        m.setValueAt("DLD-T4",     5, 4);
        m.setValueAt("DLD(L)-T4",  5, 5);

        m.setValueAt("IS-M101",    6, 1);
        m.setValueAt("IS-M101",    6, 2);
        m.setValueAt("OOP-HPC1",   6, 3);
        m.setValueAt("OOP-HPC1",   6, 4);
        m.setValueAt("OOP-HPC1",   6, 5);

        m.setValueAt("OOP-HPC1",   7, 1);
        m.setValueAt("DLD-M101",   7, 2);
        m.setValueAt("DLD-M101",   7, 3);
        m.setValueAt("DLD(L)-HPC1",7, 4);
        m.setValueAt("EC-309",     7, 5); // Electronic Circuits Lab
    }

    private void fillSectionC(DefaultTableModel m) {
        m.setValueAt("CS-309",     0, 1);
        m.setValueAt("LA-309",     0, 2);
        m.setValueAt("FMII-309",   0, 3);
        m.setValueAt("IS-309",     0, 4);
        m.setValueAt("IS-309",     0, 5);

        m.setValueAt("LA-309",     1, 1);
        m.setValueAt("DLD-309",    1, 2);
        m.setValueAt("CS-309",     1, 3);
        m.setValueAt("PS-309",     1, 4);
        m.setValueAt("DLD(L)-309", 1, 5);

        m.setValueAt("OOP-HPC1",   2, 1);
        m.setValueAt("OOP-HPC1",   2, 2);
        m.setValueAt("DLD-309",    2, 3);
        m.setValueAt("CS-309",     2, 4);
        m.setValueAt("PS-309",     2, 5);

        m.setValueAt("PS-309",     3, 1);
        m.setValueAt("OOP-HPC1",   3, 2);
        m.setValueAt("OOP-HPC1",   3, 3);
        m.setValueAt("DLD(L)-309", 3, 4);
        m.setValueAt("ASL-309",    3, 5); // Advanced Simulation Lab

        // Slot 5: Lunch

        m.setValueAt("CS-309",     5, 1);
        m.setValueAt("DLD-309",    5, 2);
        m.setValueAt("DLD-309",    5, 3);
        m.setValueAt("DLD(L)-309", 5, 4);
        m.setValueAt("CS-309",     5, 5);

        m.setValueAt("PS-309",     6, 1);
        m.setValueAt("PS-309",     6, 2);
        m.setValueAt("FMII-309",   6, 3);
        m.setValueAt("IS-309",     6, 4);
        m.setValueAt("IS-309",     6, 5);

        m.setValueAt("LA-309",     7, 1);
        m.setValueAt("LA-309",     7, 2);
        m.setValueAt("DLD-309",    7, 3);
        m.setValueAt("OOP-HPC1",   7, 4);
        m.setValueAt("OOP-HPC1",   7, 5);
    }

    private void fillSectionD(DefaultTableModel m) {
        m.setValueAt("DLD-309",    0, 1);
        m.setValueAt("CS-309",     0, 2);
        m.setValueAt("—",          0, 3);  // no class at Wed Slot1 for D
        m.setValueAt("PS-309",     0, 4);
        m.setValueAt("LA-309",     0, 5);

        // Continue transcribing Section D from PDF in same short-form pattern
        // e.g., m.setValueAt("FMII-309", 1, 1); etc.
    }
}

