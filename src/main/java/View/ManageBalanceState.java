package View;

import NewEntities.Account;
import NewEntities.Product;
import NewEntities.User;
import Services.ProductsDAO;
import Utils.BalanceManager;
import Utils.Evaluator.EvalReturn;
import Utils.Evaluator.Evaluator;
import Utils.ScreenWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ManageBalanceState extends BaseState{
    Account _account;
    ProductsDAO productsDAO = new ProductsDAO();
    Evaluator eval = new Evaluator("Produto");
    List<Product> _availableProducts = new ArrayList<>();
    List<Integer> _selectionlist = new ArrayList<>();
    Product _Product;
    Integer _Operation;

    public ManageBalanceState(User user, Account account){
        setUser(user);
        _account = account;
        Greet();
        InitManagement();
    }

    public void InitManagement() {
        GetAccountProducts();
        initSelection();
    }

    public void Greet() {
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.WritePadded("Gerência de saldos");
    }

    private void initSelection() {
        if (_availableProducts.isEmpty()) {
            ScreenWriter.Write("Nenhum produto disponível!");
            new AccountState(_user, _account, false);
        } else {
            ShowGetSelection();
        }
    }

    private void GetAccountProducts() {
        _availableProducts = productsDAO.getAccountProducts(_account.getIdconta());
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
            _Product = _availableProducts.get(Integer.parseInt(_ret.message));
            AskOperation();
        }
    }

    private void ShowProduct(){
        ScreenWriter.Write(String.format("Produto Selecionado: %s",_Product.get_nomeProduto()));
        ScreenWriter.WriteEmptyLine();
    };

    private void ShowOperation(){
        String Operation = _Operation == 1 ?"Adicionar Saldo" : "Transferir Saldo";
        ScreenWriter.Write(String.format("Operação Selecionada: %s",Operation));
    }

    private void AskOperation() {
        ShowProduct();
        ScreenWriter.WritePadded("Que operação deseja efetuar?");
        ShowGetOperation();
    }

    private void ShowGetOperation(){
        ScreenWriter.Write("1. Adicionar saldo de produto");
        ScreenWriter.Write("2. Remover saldo de Produto");
        GetOperation();
    }

    private void GetOperation() {
        eval.setField("operação");
        eval.setPattern(getSelectionPattern(new ArrayList<Integer>(Arrays.asList(1, 2))));
        EvalReturn _ret = eval.EvalData();
        if (Objects.equals(_ret.message, "VOLTAR")) {
            new AccountState(_user, _account, true);
            return;
        }
        if (!_ret.valid) {
            _ret.errors.forEach(ScreenWriter::Write);
            ShowGetOperation();
        } else {
            _Operation = Integer.parseInt(_ret.message);
            Decider();
        }
    }

    private void Decider(){

        switch(_Operation){
            case 1:
                InitAddBalance();
                break;
            case 2:
                InitRemoveBalance();
        }
    }

    private void InitAddBalance(){
        ShowProduct();
        WriteCurrentBalance();
        ShowOperation();
        GetBalance();
    }

    private void InitRemoveBalance(){
        ShowProduct();
        WriteCurrentBalance();
        ShowOperation();
        GetBalance();
    }

    private void Decider2(Double balance){

        switch(_Operation){
            case 1:
                PerformAddBalance(balance);
                break;
            case 2:
                PerformRemoveBalance(balance);
        }
    }

    private void GetBalance(){
        eval.setField("saldo");
        eval.setPattern(GetBalancePattern(_Operation ==1 ? 0 : productsDAO.getProductBalance(_account.getIdconta(),_Product.get_idproduto())));
        ScreenWriter.Write("Digite a quantia. Caso queira voltar digite VOLTAR");
        ScreenWriter.WriteEmptyLine();
        EvalReturn _ret = eval.EvalData();
        if (Objects.equals(_ret.message, "VOLTAR")) {
            new AccountState(_user, _account, true);
            return;
        }
        if (!_ret.valid) {
            _ret.errors.forEach(ScreenWriter::Write);
            GetBalance();
        } else {
            Decider2(Double.parseDouble(_ret.message));
        }

    }

    private void PerformAddBalance(double balance){
        BalanceManager balanceManager = new BalanceManager(_account,_Product);
        boolean ret = balanceManager.AddBalance(balance);
        if (ret){
            ScreenWriter.Write("Adição efetuada com sucesso!");
        }else{
            ScreenWriter.Write("Algo errado ocorreu. Tente novamente!");
        }
        ScreenWriter.WriteEmptyLine();
        new AccountState(_user, _account, true);
    }

    private void PerformRemoveBalance(double balance){
        BalanceManager balanceManager = new BalanceManager(_account,_Product);
        boolean ret = balanceManager.RemoveBalance(balance);
        if (ret){
            ScreenWriter.Write("Remoção efetuada com sucesso!");
        }else{
            ScreenWriter.Write("Algo errado ocorreu. Tente novamente!");
        }
        new AccountState(_user, _account, true);
    }

    private void WriteCurrentBalance(){
        Double balance = productsDAO.getProductBalance(_account.getIdconta(),_Product.get_idproduto());
        ScreenWriter.Write(String.format("Saldo atual: %.2f",balance));
    }
    private void WriteSelection(Product product, int index) {
        ScreenWriter.Write(String.format("%d. %s - %s", index, product.get_idproduto(), product.get_nomeProduto()));
    }


}
