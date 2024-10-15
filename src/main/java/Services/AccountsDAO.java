package Services;

import NewEntities.Account;
import Utils.DBConnection;
import Utils.ScreenWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountsDAO {

    public List<Account> GetContas(Integer idusuario) {
        String sql = "SELECT CC.* FROM DBO_USUARIOS USR inner join RM551223.CRIPTO_RELACAO_USUARIO_CONTA CRUC on USR.IDUSUARIO = CRUC.IDUSUARIO inner join RM551223.CRIPTO_CONTA CC on CRUC.IDCONTA = CC.IDCONTA where USR.IDUSUARIO = ? AND CC.Ativo = '1' order by CC.CODIGOCONTA";
        List<Account> ret = new ArrayList<Account>();

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idusuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Account _account = new Account(rs.getInt("IDCONTA"),rs.getString("CODIGOCONTA"),rs.getString("NOMECONTA"));
                    ret.add(_account);
                }
            }
        } catch (SQLException e) {
            ScreenWriter.Write("Erro de seleção! " + e.getMessage());
            ret.clear();
        }
        return ret;
    }

    public int GetIDUsuarioConta(Integer idconta) {
        String sql = "SELECT IDUSUARIO FROM CRIPTO_RELACAO_USUARIO_CONTA WHERE IDCONTA = ?";
        int ret = 0;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idconta);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ret = rs.getInt("IDUSUARIO");
                }
            }
        } catch (SQLException e) {
            ScreenWriter.Write("Erro de seleção! " + e.getMessage());
        }
        return ret;
    }

    public boolean InitInsertAccount(int idusuario, String NomeConta){

        int idconta = InsertAccount(NomeConta);
        if(idconta == -1){
            return false;
        }
        return InsertRelacaoConta(idconta,idusuario);
    }


    private int InsertAccount(String NomeConta) {
        String sql = "INSERT INTO CRIPTO_CONTA (CODIGOCONTA, ATIVO, NOMECONTA) VALUES ('1-' || LPAD(seq_codigoconta.NEXTVAL, 4, '0'), '1', ?)";
        int generatedIdConta = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"IDCONTA"})) {

            stmt.setString(1, NomeConta);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedIdConta = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            ScreenWriter.Write("Erro!" + e.getMessage());
            generatedIdConta = -1;
        }

        return generatedIdConta;
    }

    private boolean InsertRelacaoConta(int idConta, int idUsuario) {
        String sql = "INSERT INTO CRIPTO_RELACAO_USUARIO_CONTA (IDUSUARIO,IDCONTA) VALUES (?, ?)";
        boolean isInserted = false;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idConta);
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

    public boolean InsertNewProductRelation(int idConta, int idProduto){
        String sql = "INSERT INTO CRIPTO_RELACAO_CONTA_PRODUTO (IDCONTA,IDPRODUTO,ATIVO,SALDO) VALUES (?, ?,'1',0)";
        boolean isInserted = false;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            stmt.setInt(2, idProduto);
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

    public boolean RemoveProductRelation(int idConta, int idProduto){
        String sql = "DELETE CRIPTO_RELACAO_CONTA_PRODUTO WHERE IDCONTA = ? AND IDPRODUTO = ?";
        boolean isDeleted = false;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            stmt.setInt(2, idProduto);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isDeleted = true;
            }

        } catch (SQLException e) {
            ScreenWriter.Write("Erro!" + e.getMessage());
            isDeleted = false;
        }
        return isDeleted;
    }

}
