package Entities;

import java.math.BigDecimal;

public class Produto {

    private Integer idProduto;
    private String nomeProduto;
    private String descricao;
    private BigDecimal saldo;
    private Integer idConta;

    // Constructors
    public Produto(Integer idProduto, String nomeProduto, String descricao, BigDecimal saldo, Integer idConta) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
        this.saldo = saldo;
        this.idConta = idConta;
    }

    // Methods
    public String getNome() {
        return nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Integer getIdConta() {
        return idConta;
    }
}
