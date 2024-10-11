package View;

import NewEntities.Account;
import NewEntities.Product;
import NewEntities.User;
import Services.ProductsDAO;
import Utils.Evaluator.EvalReturn;
import Utils.Evaluator.Evaluator;
import Utils.ScreenWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddAccountProductState extends BaseState{
    Account _account;
    ProductsDAO productsDAO = new ProductsDAO();
    Evaluator eval = new Evaluator("Produto");
    List<Product> _availableProducts = new ArrayList<>();
    List<Integer> _selectionlist = new ArrayList<>();

    public AddAccountProductState(User user,Account account){
        setUser(user);
        _account = account;
        GetAvailableProducts();
        Greet();
        initSelection();
    }

    private void initSelection() {
        if (_availableProducts.isEmpty()) {
            ScreenWriter.Write("Nenhum produto disponível!");
            new AccountState(_user,_account, false);
        } else {
            ShowGetSelection();
        }
    }


    public void Greet(){
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.WritePadded("Adição de produtos à conta");
    }

    private void ShowGetSelection() {
        AskSelection();
        ShowSelection();
        GetSelection();
    }

    public void AskSelection(){
        ScreenWriter.Write("Selecione o produto para adicionar. Caso deseje voltar, digite VOLTAR");
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
            new AccountState(_user,_account,true);
            return;
        }
        if (!_ret.valid) {
            _ret.errors.forEach(ScreenWriter::Write);
            ShowGetSelection();
        }
        else {

        }
    }

    private void GetAvailableProducts(){
        _availableProducts = productsDAO.getAvailableProducts(_account.getIdconta());
    }

    private void WriteSelection(Product product, int index) {
        ScreenWriter.Write(String.format("%d. %s - %s",index,product.get_idproduto(),product.get_nomeProduto()));
    }

}
