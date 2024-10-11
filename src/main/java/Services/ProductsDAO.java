package Services;


import NewEntities.Product;
import Utils.DBConnection;
import Utils.ScreenWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO {
    public List<Product> getAvailableProducts(Integer idconta) {
        String sql = "SELECT CRIPTO_PRODUTOS.* FROM CRIPTO_PRODUTOS WHERE IDPRODUTO NOT IN (SELECT IDPRODUTO FROM CRIPTO_RELACAO_CONTA_PRODUTO WHERE IDCONTA = ?) AND ATIVO =1";
        List<Product> ret = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idconta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product _product = new Product(rs.getInt("IDPRODUTO"),rs.getString("NOMEPRODUTO"),rs.getString("DESCRICAOPRODUTO"));
                    ret.add(_product);
                }
            }
        } catch (SQLException e) {
            ScreenWriter.Write("Erro de seleção! " + e.getMessage());
            ret.clear();
        }
        return ret;
    }

}
