package View;

import NewEntities.Account;
import NewEntities.Product;
import NewEntities.User;
import Services.AccountsDAO;
import Services.ProductsDAO;
import Utils.Evaluator.EvalReturn;
import Utils.Evaluator.Evaluator;
import Utils.ScreenWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountState extends BaseState{
    AccountsDAO accountsDAO = new AccountsDAO();
    ProductsDAO productsDAO = new ProductsDAO();
    Account _account;

    public AccountState(User user,Account account,boolean greet){
        setUser(user);
        _account = account;
        if(greet){
            WriteAccountInfo();
        }
        InitSelection();
    }

    private void InitSelection(){
        WriteSelection();
        GetSelection();
    }

    private void WriteAccountInfo(){
        ScreenWriter.WritePadded("CONTA SELECIONADA");
        ScreenWriter.Write(String.format("Usuário: %s",_user.get_nome()));
        ScreenWriter.Write(String.format("Código Conta: %s",_account.getCodigoConta()));
        ScreenWriter.Write(String.format("Nome Conta: %s",_account.getNomeConta()));
        ScreenWriter.WriteEmptyLine();
    }

    public void WriteSelection(){
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.Write("Selecione a opção abaixo:");
        ScreenWriter.Write("1. Adicionar Produto à Conta");
        ScreenWriter.Write("2. Visualizar Notificações do usuário");
        ScreenWriter.Write("3. Gerenciar Saldo de Produtos");
        ScreenWriter.Write("4. Remover Produto da Conta");
        ScreenWriter.Write("5. Visualizar Saldo de Produtos");
        ScreenWriter.Write("0. Voltar");
        ScreenWriter.WriteEmptyLine();
    }

    public void GetSelection(){
        Evaluator eval = new Evaluator("Registro",getSelectionPattern(new ArrayList<>(Arrays.asList(0,1, 2, 3, 4,5))));
        EvalReturn evalReturn = eval.EvalData();
        if (!evalReturn.valid){
            evalReturn.errors.forEach(ScreenWriter::Write);
            InitSelection();
        }else{
            Decider(Integer.parseInt(evalReturn.message));
        }
    }

    private void Decider(Integer selection){
        switch(selection){
            case 0:
                new MainState(_user,true);
                break;
            case 1:
                new AccountProductState(_user,_account,true);
                break;
            case 2:
                new NotificationState(_user,_account);
                break;
            case 3:
                new ManageBalanceState(_user,_account);
                break;
            case 4:
                new AccountProductState(_user,_account,false);
                break;
            case 5:
                break;
        }
    }

    private void ShowBalances(){
        List<Product> products = productsDAO.getAccountProducts(_account.getIdconta());
    }

    private void WriteProductBalance(Product product){

    }

}
