package NewEntities;

import Services.UsersDAO;

import java.util.Date;

public class User {
    String _username;
    String _password;
    Integer _idusuario;
    String _nome;
    String _telefone;
    String _email;
    Date _dataCadastro;
    UsersDAO usersDAO = new UsersDAO();


    public User(String username, String password) {
        _username = username;
        _password = password;
        SetUserData();
    }

    private void SetUserData() {
        usersDAO.GetUserData(this);
    }

    public String getUsername() {
        return _username;
    }

    public String get_email() {
        return _email;
    }

    public Integer getIdusuario() {
        return _idusuario;
    }

    public String get_Password() {
        return _password;
    }

    public String get_nome() {
        return _nome;
    }

    public Date get_dataCadastro() {
        return _dataCadastro;
    }
    public String get_username(){
        return _username;
    }
    public String get_telefone(){
        return _telefone;
    }

    public void set_idusuario(Integer idusuario){
        _idusuario = idusuario;
    }
    public void set_password(String password){
        _password = password;
    }
    public void set_nome(String nome){
        _nome = nome;
    }
    public void set_dataCadastro(Date dataCadastro){
        _dataCadastro = dataCadastro;
    }
    public void set_username(String username){
        _username = username;
    }
    public void set_telefone(String telefone){
        _telefone = telefone;
    }
    public void set_email(String email){
        _email = email;
    }


}
