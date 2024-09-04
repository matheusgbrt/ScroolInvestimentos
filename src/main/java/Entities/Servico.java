package Entities;

public class Servico {

    private Integer idServico;
    private String nomeServico;
    private String descricaoServico;
    private Integer idConta;

    // Constructors
    public Servico(Integer idServico, String nomeServico, String descricaoServico, Integer idConta) {
        this.idServico = idServico;
        this.nomeServico = nomeServico;
        this.descricaoServico = descricaoServico;
        this.idConta = idConta;
    }

    // Methods
    public String getNome() {
        return nomeServico;
    }

    public String getDescricao() {
        return descricaoServico;
    }

    public Integer getIdConta() {
        return idConta;
    }
}
