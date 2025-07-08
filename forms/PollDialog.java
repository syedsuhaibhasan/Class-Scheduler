package forms;

import dao.connectionprovider;
import dao.NotificationSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.Timer;

public class PollDialog extends JDialog {
    private final JProgressBar progressBar;
    private final JRadioButton rbYes;
    private final JRadioButton rbNo;
    private final JButton btnSubmit;
    private final JButton btnNewPoll;
    private Timer refreshTimer;
    private final int pollId = 1;
    private final int totalStudents;
    private boolean hasVoted;

    public PollDialog(Frame owner, boolean modal) {
        super(owner, modal);
        // determine total students once
        this.totalStudents = fetchTotalStudents();
        this.progressBar = new JProgressBar(0, totalStudents);
        this.rbYes = new JRadioButton("Yes");
        this.rbNo = new JRadioButton("No");
        this.btnSubmit = new JButton("Submit Vote");
        this.btnNewPoll = new JButton("Start New Poll");

        initDialog();
        updateProgress(); // initial bar
    }

    private void initDialog() {
        setTitle("Change Class Poll");
        setSize(500, 250);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10,10));

        // Progress bar shows vote count
        progressBar.setStringPainted(true);
        progressBar.setString("0 / " + totalStudents);
        add(progressBar, BorderLayout.NORTH);

        // center panel: question + radio
        JPanel center = new JPanel(new GridLayout(2, 1, 5,5));
        center.add(new JLabel("Do you agree to change class?"));
        JPanel radios = new JPanel();
        ButtonGroup group = new ButtonGroup();
        group.add(rbYes);
        group.add(rbNo);
        radios.add(rbYes);
        radios.add(rbNo);
        center.add(radios);
        add(center, BorderLayout.CENTER);

        // bottom panel with submit and reset buttons
        JPanel southPanel = new JPanel(new FlowLayout());
        btnSubmit.addActionListener(e -> onSubmit());
        btnNewPoll.addActionListener(e -> clearPollVotes());
        southPanel.add(btnSubmit);
        southPanel.add(btnNewPoll);
        add(southPanel, BorderLayout.SOUTH);
    }

    private int fetchTotalStudents() {
        try (Connection con = connectionprovider.getCon();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM studentdetails")) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void onSubmit() {
        if (hasVoted) {
            JOptionPane.showMessageDialog(this, "You have already voted.",
                    "Already Voted", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!rbYes.isSelected() && !rbNo.isSelected()) {
            JOptionPane.showMessageDialog(this, "Select Yes or No first.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String desiredClass = JOptionPane.showInputDialog(this,
                "Enter the class you want to shift to:",
                "Desired Class", JOptionPane.PLAIN_MESSAGE);
        if (desiredClass == null || desiredClass.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "You must enter a desired class to submit your vote.",
                    "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String vote = rbYes.isSelected() ? "yes" : "no";
        String email = ((StudentDashboard)getOwner()).getLoggedInEmail();

        try (Connection con = connectionprovider.getCon()) {
            // check existing vote
            PreparedStatement chk = con.prepareStatement(
                    "SELECT 1 FROM poll_votes WHERE poll_id=? AND voter_email=?");
            chk.setInt(1, pollId);
            chk.setString(2, email);
            try (ResultSet rs = chk.executeQuery()) {
                if (rs.next()) {
                    hasVoted = true;
                    JOptionPane.showMessageDialog(this, "You have already voted.",
                            "Already Voted", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            chk.close();

            // insert vote
            PreparedStatement ins = con.prepareStatement(
                    "INSERT INTO poll_votes(poll_id, voter_email, vote, desired_class) VALUES(?,?,?,?)");
            ins.setInt(1, pollId);
            ins.setString(2, email);
            ins.setString(3, vote);
            ins.setString(4, desiredClass.trim());
            ins.executeUpdate();
            ins.close();
            hasVoted = true;

            // send notification to admin
            String msg = String.format("%s voted %s, desires %s", email, vote, desiredClass);
            NotificationSender.sendNotification(msg, "admin", email);

            updateProgress();

            if (getVotedCount() >= totalStudents) {
                JOptionPane.showMessageDialog(this,
                        "All students have voted. Admin has been notified.",
                        "Poll Complete", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error recording vote.",
                    "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearPollVotes() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to start a new poll? All previous votes will be cleared.",
                "Confirm Reset", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = connectionprovider.getCon();
                 PreparedStatement ps = con.prepareStatement("DELETE FROM poll_votes WHERE poll_id=?")) {
                ps.setInt(1, pollId);
                ps.executeUpdate();
                hasVoted = false;
                rbYes.setSelected(false);
                rbNo.setSelected(false);
                updateProgress();
                JOptionPane.showMessageDialog(this, "Poll has been reset.", "Reset", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to reset poll.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateProgress() {
        int voted = getVotedCount();
        progressBar.setMaximum(totalStudents);
        progressBar.setValue(voted);
        progressBar.setString(voted + " / " + totalStudents);
    }

    private int getVotedCount() {
        try (Connection con = connectionprovider.getCon();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) FROM poll_votes WHERE poll_id=?")) {
            ps.setInt(1, pollId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
