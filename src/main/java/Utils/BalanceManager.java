package Utils;

import NewEntities.Account;
import NewEntities.Product;
import NewEntities.User;
import Services.AccountsDAO;
import Services.NotificationsDAO;
import Services.ProductsDAO;
import Services.TransactionsDAO;

public class BalanceManager {
    ProductsDAO productsDAO = new ProductsDAO();
    NotificationsDAO notificationsDAO = new NotificationsDAO();
    AccountsDAO accountsDAO = new AccountsDAO();
    TransactionsDAO transactionsDAO = new TransactionsDAO();
    Account _account;
    Product _product;
    public BalanceManager(Account account, Product product){
        _account = account;
        _product = product;
    }



    public boolean AddBalance(double balance){
        boolean ret = productsDAO.AddProductBalance(_account.getIdconta(),_product.get_idproduto(),balance);
        if (ret){
            transactionsDAO.InsertTransaction(1,0,_account.getIdconta(),_product.get_idproduto(),balance);
            notificationsDAO.insertNotification(String.format("%f.2f adicionado ao produto %s da conta %s. Saldo atual: %f.2f",balance,_product.get_nomeProduto(),_account.getCodigoConta(),productsDAO.getProductBalance(_account.getIdconta(),_product.get_idproduto())),accountsDAO.GetIDUsuarioConta(_account.getIdconta()));
        }
        return ret;
    }

    public boolean RemoveBalance(double balance){
        boolean ret = productsDAO.RemoveProductBalance(_account.getIdconta(),_product.get_idproduto(),balance);
        if (ret){
            transactionsDAO.InsertTransaction(2,0,_account.getIdconta(),_product.get_idproduto(),balance);
            notificationsDAO.insertNotification(String.format("%f.2f removido do produto %s da conta %s. Saldo atual: %f.2f",balance,_product.get_nomeProduto(),_account.getCodigoConta(),productsDAO.getProductBalance(_account.getIdconta(),_product.get_idproduto())),accountsDAO.GetIDUsuarioConta(_account.getIdconta()));
        }
        return ret;
    }




}
