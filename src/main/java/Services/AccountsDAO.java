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

    public List<Account> getContas(Integer idusuario) {
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

    public boolean initInsertAccount(int idusuario,String NomeConta){

        int idconta = insertAccount(NomeConta);
        if(idconta == -1){
            return false;
        }
        return insertRelacaoConta(idconta,idusuario);
    }


    private int insertAccount(String NomeConta) {
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

    private boolean insertRelacaoConta(int idConta, int idUsuario) {
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

}
