package Services;

import NewEntities.User;
import Utils.DBConnection;
import Utils.ScreenWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {
    public Integer getUserExists(String username) {
        String sql = "SELECT count(*) AS Count FROM DBO_USUARIOS WHERE USERNAME = ?";
        int ret = 0;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ret = rs.getInt("Count");
                }
            }

        } catch (SQLException e) {
            ScreenWriter.Write("Erro de seleção!" + e.getMessage());
            ret = 0;
        }

        return ret;
    }

    public String getUserPassword(String username) {
        String sql = "SELECT PASSWORD  FROM DBO_USUARIOS WHERE USERNAME = ?";
        String ret = "";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ret = rs.getString("PASSWORD");
                }
            }

        } catch (SQLException e) {
            ScreenWriter.Write("Erro!");
            ret = "";
        }

        return ret;
    }

    public boolean insertUser(String nome, String username, String password, String telefone, String email) {
        String sql = "INSERT INTO DBO_USUARIOS (NOME,USERNAME, PASSWORD,TELEFONE,EMAIL) VALUES (?, ?, ?, ?, ?)";
        boolean isInserted = false;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, telefone);
            stmt.setString(5, email);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isInserted = true;
            }

        } catch (SQLException e) {
            ScreenWriter.Write("Erro!" + e.getMessage());
            isInserted = false;
        }

        return isInserted;
    }

    public void GetUserData(User user) {
        String sql = "SELECT *  FROM DBO_USUARIOS WHERE USERNAME = ? AND PASSWORD = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.get_username());
            stmt.setString(2, user.get_Password());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    user.set_nome(rs.getString("NOME"));
                    user.set_telefone(rs.getString("TELEFONE"));
                    user.set_email(rs.getString("EMAIL"));
                    user.set_dataCadastro(rs.getDate("DATACADASTRO"));
                    user.set_idusuario(rs.getInt("IDUSUARIO"));
                }
            }
        } catch (SQLException e) {
            ScreenWriter.Write("Erro!");
        }

    }
}
