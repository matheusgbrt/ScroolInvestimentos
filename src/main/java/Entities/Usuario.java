package Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {

    private Integer idUsuario;
    private String nome;
    private String telefone;
    private String email;
    private String username;
    private Date dataCadastro;
    private List<Notificacao> notificacoes;
    private List<Conta> contas;

    // Constructors
    public Usuario(Integer idUsuario, String nome, String telefone, String email, Date dataCadastro) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = dataCadastro;
        setContas();
        setNotificacoes();
    }


    // Methods
    public void login() {
        // Lógica do login
        if(authenticate()){
            System.out.println("Login successful");
        }else{
            System.out.println("Login failed");
        }

    }

    public void setUserData(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    private boolean authenticate() {
        // Lógica de autenticação.
        if(1==1){
            return true;
        }else{
            return false;
        }
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public String getTelefone(){
        return telefone;
    }

    private void setContas() {
        this.contas = new ArrayList<>();
        this.contas.add(new Conta(1,12));
        this.contas.add(new Conta(2,13));
        this.contas.add(new Conta(3,14));
    }

    private void setNotificacoes() {
        this.notificacoes = new ArrayList<>();
        this.notificacoes.add(new Notificacao(1,new Date(),"Notificação de Teste"));
        this.notificacoes.add(new Notificacao(2,new Date(),"Notificação de Teste 2"));

    }
}
