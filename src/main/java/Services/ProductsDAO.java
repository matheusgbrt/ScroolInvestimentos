package Services;


import NewEntities.Product;
import Utils.DBConnection;
import Utils.ScreenWriter;

import java.math.BigDecimal;
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

    public Double getProductBalance(Integer idconta,Integer idproduto) {
        String sql = "SELECT SALDO  FROM CRIPTO_RELACAO_CONTA_PRODUTO WHERE IDCONTA = ? AND IDPRODUTO = ?";
        Double ret = -1.0;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idconta);
            stmt.setInt(2, idproduto);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ret = rs.getDouble("SALDO");
                }
            }

        } catch (SQLException e) {
            ScreenWriter.Write("Erro! " + e.getMessage());
            ret = -1.0;
        }

        return ret;
    }

    public List<Product> getAccountProducts(Integer idconta) {
        String sql = "SELECT CRIPTO_PRODUTOS.* FROM CRIPTO_PRODUTOS INNER JOIN RM551223.CRIPTO_RELACAO_CONTA_PRODUTO CRCP on CRIPTO_PRODUTOS.IDPRODUTO = CRCP.IDPRODUTO where IDCONTA= ? ";
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

    public boolean AddProductBalance(Integer idconta,Integer idproduto,double balance) {
        String sql = "UPDATE CRIPTO_RELACAO_CONTA_PRODUTO SET SALDO = SALDO + ? WHERE IDPRODUTO = ? AND IDCONTA = ?";
        boolean ret = false;
        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, balance);
            stmt.setInt(2, idproduto);
            stmt.setInt(3, idconta);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ret = true;
            }
        } catch (SQLException e) {
            ScreenWriter.Write("Erro de atualização! " + e.getMessage());
        }
        return ret;
    }

    public boolean RemoveProductBalance(Integer idconta,Integer idproduto,double balance) {
        String sql = "UPDATE CRIPTO_RELACAO_CONTA_PRODUTO SET SALDO = SALDO - ? WHERE IDPRODUTO = ? AND IDCONTA = ?";
        boolean ret = false;
        try (Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, balance);
            stmt.setInt(2, idproduto);
            stmt.setInt(3, idconta);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ret = true;
            }
        } catch (SQLException e) {
            ScreenWriter.Write("Erro de atualização! " + e.getMessage());
        }
        return ret;
    }

}
