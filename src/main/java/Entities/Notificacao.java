package Entities;

import java.util.Date;

public class Notificacao {

    private Integer idNotificacao;
    private Date data;
    private String texto;

    // Constructors
    public Notificacao(Integer idNotificacao, Date data, String texto) {
        this.idNotificacao = idNotificacao;
        this.data = data;
        this.texto = texto;
    }

    // Methods
    public String getTexto() {
        return texto;
    }

    public Date getData() {
        return data;
    }
}
