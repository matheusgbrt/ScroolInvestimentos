package View;

import NewEntities.Account;
import NewEntities.Product;
import NewEntities.User;
import Services.AccountsDAO;
import Services.NotificationsDAO;
import Services.ProductsDAO;
import Utils.Evaluator.EvalReturn;
import Utils.Evaluator.Evaluator;
import Utils.ScreenWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountProductState extends BaseState {
    Account _account;
    ProductsDAO productsDAO = new ProductsDAO();
    AccountsDAO accountsDAO = new AccountsDAO();
    NotificationsDAO notificationsDAO = new NotificationsDAO();
    Evaluator eval = new Evaluator("Produto");
    List<Product> _availableProducts = new ArrayList<>();
    List<Integer> _selectionlist = new ArrayList<>();
    boolean _add;

    public AccountProductState(User user, Account account, boolean add) {
        setUser(user);
        _account = account;
        _add = add;
        Greet(add);
        if (add) {
            InitAdd();
        } else {
            InitRemove();
        }
    }

    public void InitRemove() {
        GetAccountProducts();
        initSelection();
    }

    private void InitAdd() {
        GetAvailableProducts();
        initSelection();
    }


    private void initSelection() {
        if (_availableProducts.isEmpty()) {
            ScreenWriter.Write("Nenhum produto disponível!");
            new AccountState(_user, _account, false);
        } else {
            ShowGetSelection();
        }
    }


    public void Greet(boolean add) {
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.WritePadded(add ? "Adição" : "Remoção" + " de produtos da conta");
    }

    private void ShowGetSelection() {
        AskSelection();
        ShowSelection();
        GetSelection();
    }

    public void AskSelection() {
        ScreenWriter.Write("Selecione o produto. Caso deseje voltar, digite VOLTAR");
        ScreenWriter.WriteEmptyLine();
    }

    private void ShowSelection() {
        _selectionlist.clear();
        for (int i = 0; i < _availableProducts.size(); i++) {
            Product product = _availableProducts.get(i);
            WriteSelection(product, i);
            _selectionlist.add(i);
        }
    }

    private void GetSelection() {
        eval.setPattern(getSelectionPattern(_selectionlist));
        EvalReturn _ret = eval.EvalData();
        if (Objects.equals(_ret.message, "VOLTAR")) {
            new AccountState(_user, _account, true);
            return;
        }
        if (!_ret.valid) {
            _ret.errors.forEach(ScreenWriter::Write);
            ShowGetSelection();
        } else {
            if (_add) {
                InsertNewRelation(_ret.message);
            } else {
                InitRemoveRelation(_ret.message);
            }
        }
    }

    private void InitRemoveRelation(String message) {
        Product product = _availableProducts.get(Integer.parseInt(message));
        Double saldo = productsDAO.getProductBalance(_account.getIdconta(), product.get_idproduto());
        if (saldo == -1.0) {
            ScreenWriter.Write("Erro ao consultar saldo! Tente novamente.");
        } else if (saldo >= 1.0) {
            ScreenWriter.Write("Produto não pode ser removido pois possui saldo! Saldo atual: %.2f");
        } else if (saldo >= 0.0) {
            if (accountsDAO.RemoveProductRelation(_account.getIdconta(), product.get_idproduto())) {
                notificationsDAO.insertNotification(String.format("Produto removido da conta. Nome: %s", product.get_nomeProduto()), _user.getIdusuario());
                ScreenWriter.Write("Produto removido com sucesso!");
            } else {
                ScreenWriter.Write("Erro ao efetuar a operação! Tente novamente");
            }
        }
        new AccountState(_user, _account, true);
    }

    private void InsertNewRelation(String message) {
        Product product = _availableProducts.get(Integer.parseInt(message));
        boolean insert = accountsDAO.InsertNewProductRelation(_account.getIdconta(), product.get_idproduto());
        if (!insert) {
            ScreenWriter.Write("Erro ao adicionar produto!");
        } else {
            notificationsDAO.insertNotification(String.format("Produto adicionado à conta. Nome: %s", product.get_nomeProduto()), _user.getIdusuario());
            ScreenWriter.Write("Produto adicionado com sucesso!");
        }
        new AccountState(_user, _account, false);
    }

    private void GetAvailableProducts() {
        _availableProducts = productsDAO.getAvailableProducts(_account.getIdconta());
    }

    private void GetAccountProducts() {
        _availableProducts = productsDAO.getAccountProducts(_account.getIdconta());
    }

    private void WriteSelection(Product product, int index) {
        ScreenWriter.Write(String.format("%d. %s - %s", index, product.get_idproduto(), product.get_nomeProduto()));
    }

}
