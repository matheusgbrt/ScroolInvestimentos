package View;

import NewEntities.User;
import Utils.Authentication;
import Utils.Evaluator.DataTypes;
import Utils.Evaluator.EvalPatterns;
import Utils.Evaluator.EvalReturn;
import Utils.Evaluator.Evaluator;
import Utils.ScreenWriter;


import java.util.Objects;

public class LoginState extends BaseState   {
    Evaluator eval = new Evaluator("Usuário");
    String _username;
    String _inputPassword;

    public LoginState() {
        ScreenWriter.WritePadded("Login do usuário");
        ScreenWriter.Write("Digite as informações requisitadas. Caso deseje cancelar a operação, digite SAIR.");
    }

    public void GetUserData() {
        GetUsername();
        GetPassword();
        Authentication auth = new Authentication(_username, _inputPassword);
        if (!auth.Validate()) {
            ScreenWriter.Write("Usuário não encontrado!");
            GetUserData();
        }
        if(!auth.Authenticate()) {
            ScreenWriter.Write("Senha incorreta!");
            GetUserData();
        }else{
            User user = new User(_username,auth.getHashPassword());
            MainState mainState = new MainState(user,true);
            mainState.setUser(user);
        }
    }


    private void GetUsername() {
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.Write("Usuário:");
        eval.setPattern(getUsernamePattern());
        eval.setField("Usuário");
        EvalReturn _ret = eval.EvalData();
        if (Objects.equals(_ret.message, "SAIR")) {
            InitialState state = new InitialState();
            state.AskMain();
            return;
        }
        if (!_ret.valid) {
            _ret.errors.forEach(ScreenWriter::Write);
            GetUsername();

        } else {
            _username = _ret.message;
        }
    }


    private void GetPassword() {
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.Write("Senha:");
        eval.setPattern(getPasswordPattern());
        eval.setField("Senha");
        EvalReturn _ret = eval.EvalData();
        if (!_ret.valid) {
            _ret.errors.forEach(ScreenWriter::Write);
            GetPassword();
        } else {
            _inputPassword = _ret.message;
        }
    }

}
