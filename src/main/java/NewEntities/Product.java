package NewEntities;

public class Product {
    private final String _nomeProdutao;
    private final String _DescricaoProduto;
    private final int _idproduto;

    public Product(int _idproduto, String _nomeProdutao, String _DescricaoProduto) {
        this._idproduto = _idproduto;
        this._nomeProdutao = _nomeProdutao;
        this._DescricaoProduto = _DescricaoProduto;
    }

    public int get_idproduto() {
        return _idproduto;
    }


    public String get_nomeProduto() {
        return _nomeProdutao;
    }

    public String get_DescricaoProduto() {
        return _DescricaoProduto;
    }
}

