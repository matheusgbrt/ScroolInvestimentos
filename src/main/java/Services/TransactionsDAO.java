package Services;

import Utils.DBConnection;
import Utils.ScreenWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionsDAO {

    public void InsertTransaction(int tipoTransacao,int idContaSaida, int idContaEntrada, int idProduto,double Quantidade) {
        String sql = "INSERT INTO CRIPTO_TRANSACOES (TIPOTRANSACAO, IDCONTASAIDA, IDCONTAENTRADA, IDPRODUTO, QUANTIDADE) values (?,?,?,?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, tipoTransacao);
            stmt.setInt(2, idContaSaida);
            stmt.setInt(3, idContaEntrada);
            stmt.setInt(4, idProduto);
            stmt.setDouble(5, Quantidade);

            stmt.execute();

        } catch (SQLException e) {
            ScreenWriter.Write("Erro!" + e.getMessage());
        }
    }

}
