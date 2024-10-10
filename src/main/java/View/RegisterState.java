package View;

import Services.UsersDAO;
import Utils.Evaluator.DataTypes;
import Utils.Evaluator.EvalPatterns;
import Utils.Evaluator.EvalReturn;
import Utils.Evaluator.Evaluator;
import Utils.ScreenWriter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

public class RegisterState {
    Evaluator eval = new Evaluator("Usuário");
    String _username;
    String _inputPassword;
    String _nome;
    String _telefone;
    String _email;

    public RegisterState() {
        ScreenWriter.WritePadded("Cadastro do usuário");
        ScreenWriter.Write("Digite as informações requisitadas. Caso deseje cancelar a operação, digite SAIR a qualquer momento.");
    }

    public void GetUserData() {
        GetNome();
        GetUsername();
        ValidateUserExists();
        GetPassword();
        GetTelefone();
        GetEmail();
        RegisterUser();
    }

    private void RegisterUser(){
        UsersDAO usersDAO = new UsersDAO();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(usersDAO.insertUser(_nome,_username,passwordEncoder.encode(_inputPassword),_telefone,_email)){
            ScreenWriter.Write(String.format("Usuário %s cadastrado com sucesso",_username));
            InitialState main = new InitialState();
            main.AskMain();
        }else{
            ScreenWriter.Write("Erro! Usuário não foi cadastrado. Tente novamente");
            GetUserData();
        };
    }

    private void ValidateUserExists() {
        UsersDAO usersDAO = new UsersDAO();
        if (usersDAO.getUserExists(_username) > 0) {
            ScreenWriter.Write("Este nome de usuário já existe!");
            GetUsername();
        }
    }

    private void GetNome() {
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.Write("Nome:");
        eval.setField("Nome");
        eval.setPattern(getNomePattern());
        EvalReturn _ret = eval.EvalData();
        if (Objects.equals(_ret.message, "SAIR")) {
            InitialState state = new InitialState();
            state.AskMain();
            return;
        }
        if (!_ret.valid) {
            _ret.errors.forEach(ScreenWriter::Write);
            GetNome();

        } else {
            _nome = _ret.message;
        }
    }


    private void GetUsername() {
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.Write("Usuário:");
        eval.setField("Usuário");
        eval.setPattern(getUsernamePattern());
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
        eval.setField("Senha");
        eval.setPattern(getPasswordPattern());
        EvalReturn _ret = eval.EvalData();
        if (Objects.equals(_ret.message, "SAIR")) {
            InitialState state = new InitialState();
            state.AskMain();
            return;
        }
        if (!_ret.valid) {
            _ret.errors.forEach(ScreenWriter::Write);
            GetPassword();
        } else {
            _inputPassword = _ret.message;
        }
    }

    private void GetTelefone() {
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.Write("Telefone: Padrão:(99)99999-9999");
        eval.setField("Telefone");
        eval.setPattern(getTelefonePattern());
        EvalReturn _ret = eval.EvalData();
        if (Objects.equals(_ret.message, "SAIR")) {
            InitialState state = new InitialState();
            state.AskMain();
            return;
        }
        if (!_ret.valid) {
            _ret.errors.forEach(ScreenWriter::Write);
            GetTelefone();
        }
        else {
            _telefone = _ret.message;
        }
    }

    private void GetEmail() {
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.Write("Email:");
        eval.setField("Email");
        eval.setPattern(getEmailPattern());
        EvalReturn _ret = eval.EvalData();
        if (Objects.equals(_ret.message, "SAIR")) {
            InitialState state = new InitialState();
            state.AskMain();
            return;
        }
        if (!_ret.valid) {
            _ret.errors.forEach(ScreenWriter::Write);
            GetEmail();
        }
        else {
            _email = _ret.message;
        }
    }


    private EvalPatterns getNomePattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 300;
        pattern.setOptions(options);
        return pattern;
    }


    private EvalPatterns getUsernamePattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 50;
        pattern.setOptions(options);
        return pattern;
    }

    private EvalPatterns getPasswordPattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 20;
        pattern.setOptions(options);
        return pattern;
    }

    private EvalPatterns getTelefonePattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 30;
        options.regexpattern = "\\(\\d{2}\\)\\d{5}-\\d{4}";
        pattern.setOptions(options);
        return pattern;
    }

    private EvalPatterns getEmailPattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 300;
        options.regexpattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        pattern.setOptions(options);
        return pattern;
    }

}
