package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class NotificationSender {
    /**
     * Inserts a new notification.
     *
     * @param message        the notification text
     * @param recipientType  e.g. "student", "faculty", "admin", or "all"
     * @param sender         who is sending it (e.g. "Admin", an email, or "System")
     * @return true on success, false on error
     */
    public static boolean sendNotification(String message, String recipientType, String sender) {
        String sql = "INSERT INTO notifications (message, recipient_type, sender) VALUES (?, ?, ?)";
        try (Connection con = connectionprovider.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, message);
            ps.setString(2, recipientType.toLowerCase());
            ps.setString(3, sender);
            ps.executeUpdate();
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
