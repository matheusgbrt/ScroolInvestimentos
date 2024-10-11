package Services;

import NewEntities.Notification;
import Utils.DBConnection;
import Utils.ScreenWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationsDAO {
    public List<Notification> getUserNotifications(Integer idusuario) {
        String sql = "SELECT TEXTO,DATA  FROM DBO_NOTIFICACOES WHERE IDUSUARIO = ? AND READ = '0'";
        List<Notification> ret = new ArrayList<Notification>();

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idusuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Date d = rs.getDate("DATA");
                    String text = rs.getString("TEXTO");
                    Notification notification = new Notification(d,text);
                    ret.add(notification);
                }
            }
        } catch (SQLException e) {
            ScreenWriter.Write("Erro de seleção!" + e.getMessage());
            ret.clear();
        }
        return ret;
    }

    public boolean updateRead(Integer idusuario) {
        String sql = "UPDATE DBO_NOTIFICACOES SET READ = '1' WHERE IDUSUARIO = ?";
        boolean success = false;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idusuario);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }

        } catch (SQLException e) {
            ScreenWriter.Write("Erro de atualização!" + e.getMessage());
        }
        return success;
    }

    public void insertNotification(String text,Integer idusuario) {
        String sql = "INSERT INTO DBO_NOTIFICACOES (TEXTO,IDUSUARIO, DATA,READ) VALUES (?, ?, ?,'0')";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, text);
            stmt.setInt(2, idusuario);
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            stmt.setDate(3, currentDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            ScreenWriter.Write("Erro!" + e.getMessage());
        }
    }

}
