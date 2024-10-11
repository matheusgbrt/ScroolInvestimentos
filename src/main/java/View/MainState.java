package View;

import NewEntities.User;
import Utils.Evaluator.EvalReturn;
import Utils.Evaluator.Evaluator;
import Utils.ScreenWriter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainState extends BaseState {

    public MainState(User user,boolean greet){
        setUser(user);
        InitMain(greet);
    }

    public void getSelection(){
        Evaluator eval = new Evaluator("Registro",getSelectionPattern(new ArrayList<>(Arrays.asList(0,1, 2, 3, 4, 5))));
        EvalReturn evalReturn = eval.EvalData();
        if (!evalReturn.valid){
            evalReturn.errors.forEach(ScreenWriter::Write);
            InitMain(false);
        }else{
            Decider(Integer.parseInt(evalReturn.message));
        }
    }


    public void InitMain(boolean greet){
        if(greet){
            AskMain();
        }
        WriteSelection();
        getSelection();
    }

    public void AskMain(){
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.WritePadded(String.format("Bem vindo %s!",_user.get_nome()));
        ScreenWriter.Write("Como posso ajuda-lo hoje?");
        ScreenWriter.WriteEmptyLine();
    }

    public void WriteSelection(){
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.Write("Selecione a opção abaixo:");
        ScreenWriter.Write("1. Visualizar dados do Usuário");
        ScreenWriter.Write("2. Visualizar Notificações do Usuario");
        ScreenWriter.Write("3. Selecionar Conta");
        ScreenWriter.Write("4. Cadastrar nova Conta");
        ScreenWriter.Write("0. Sair");
        ScreenWriter.WriteEmptyLine();
    }

    private void Decider(Integer selection){
        switch(selection){
            case 0:
                ScreenWriter.Write("Até mais!");
                break;
            case 1:
                _user.print_userData();
                InitMain(false);
                break;
            case 2:
                new NotificationState(_user);
                break;
            case 3:
                new SelectAccountState(_user);
                break;
            case 4:
                new RegisterAccountState(_user);
                break;
        }
    }

}
