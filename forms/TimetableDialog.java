/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forms;

/**
 *
 * @author Syeds
 */

import javax.swing.*;
import java.awt.*;

public class TimetableDialog extends JDialog {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public TimetableDialog(Frame owner, boolean modal) {
        super(owner, modal);
        initDialog();
    }

    private void initDialog() {
        setTitle("Timetable");
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public void setTimetableText(String text) {
        textArea.setText(text);
    }
}

