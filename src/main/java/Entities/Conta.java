package Entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Conta {

    private Integer idConta;
    private Integer codigoConta;
    private List<Produto> produtos;
    private List<Servico> servicos;

    // Constructors
    public Conta(Integer idConta, Integer codigoConta) {
        this.idConta = idConta;
        this.codigoConta = codigoConta;
        setProdutos();
        setServicos();
    }

    // Methods
    public void setDadosConta(Integer codigoConta) {
        this.codigoConta = codigoConta;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void transferirProduto(Produto produto, Conta contaDestino) {
        // Lógica de Transferência.
        System.out.println("Entities.Produto transferido");
    }

    public Integer getCodigoConta() {
        return codigoConta;
    }

    private void setProdutos() {
        this.produtos = new ArrayList<Produto>();
        this.produtos.add(new Produto(1,"ProdutoTeste","ProdutoTeste",new BigDecimal("100.00"),1));
        this.produtos.add(new Produto(2,"ProdutoTeste2","ProdutoTeste2",new BigDecimal("100.00"),1));
    }
    private void setServicos(){
        this.servicos = new ArrayList<Servico>();
        this.servicos.add(new Servico(1,"ServicoTeste","ServicoTeste",1));
        this.servicos.add(new Servico(1,"ServicoTeste2","ServicoTeste2",1));
    }
}
