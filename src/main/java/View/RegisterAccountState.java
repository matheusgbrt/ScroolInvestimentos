package View;

import NewEntities.User;
import Services.AccountsDAO;
import Services.NotificationsDAO;
import Utils.Evaluator.EvalReturn;
import Utils.Evaluator.Evaluator;
import Utils.ScreenWriter;

import java.util.Objects;

public class RegisterAccountState extends BaseState{

    AccountsDAO accountsDAO = new AccountsDAO();
    NotificationsDAO notificationsDAO = new NotificationsDAO();

    public RegisterAccountState(User user){
        setUser(user);
        initRegister();
    }

    private void initRegister(){
        AskInsertion();
        AskNomeConta();
    }

    private void AskInsertion(){
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.WritePadded("INSERÇÃO DE NOVA CONTA");
        ScreenWriter.Write("Digite os dados perguntados. Digite SAIR caso deseje retornar ao menu.");
        ScreenWriter.WriteEmptyLine();
    }

    private void AskNomeConta(){
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.Write("Nome da conta: ");
        Evaluator eval = new Evaluator("Nome da conta",getNomeContaPattern());
        EvalReturn evalReturn = eval.EvalData();
        if (Objects.equals(evalReturn.message, "SAIR")) {
            InitialState state = new InitialState();
            state.AskMain();
            return;
        }
        if (!evalReturn.valid){
            evalReturn.errors.forEach(ScreenWriter::Write);
            AskNomeConta();
        }else{
            insertAccount(evalReturn.message);
        }
    }

    private void insertAccount(String nomeConta){
        boolean result = accountsDAO.InitInsertAccount(_user.getIdusuario(),nomeConta);
        if(!result){
            ScreenWriter.Write("Ocorreu um erro! Tente novamente.");
        }else{
            notificationsDAO.insertNotification(String.format("Nova conta inserida. Nome: %s",nomeConta),_user.getIdusuario());
            ScreenWriter.Write("Registro efetuado com sucesso!");
        }
        new MainState(_user,false);
    }

}
