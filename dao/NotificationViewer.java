package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificationViewer {
    /**
     * Fetches distinct notifications for recipientType or 'all',
     * grouping on message/sender/timestamp to eliminate duplicates.
     */
    public static List<String> getNotifications(String recipientType) {
        List<String> notifications = new ArrayList<>();
        String sql = ""
            + "SELECT message, sender, timestamp "
            + "FROM notifications "
            + "WHERE recipient_type = ? OR recipient_type = 'all' "
            + "GROUP BY message, sender, timestamp "
            + "ORDER BY timestamp DESC";

        try (Connection con = connectionprovider.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, recipientType.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String time = rs.getTimestamp("timestamp").toString();
                    String from = rs.getString("sender");
                    String msg  = rs.getString("message");
                    notifications.add(String.format(
                        "[%s] — From: %s%n%s", time, from, msg
                    ));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return notifications;
    }
}
