package NewEntities;

public class Account {
    int _idconta;
    String _codigoConta;
    String _nomeConta;

    public Account(int idconta, String codigoConta, String nomeConta) {
        this._idconta = idconta;
        this._codigoConta = codigoConta;
        this._nomeConta = nomeConta;
    }
    public int getIdconta() {
        return _idconta;
    }
    public void setIdconta(int idconta) {
        this._idconta = idconta;
    }
    public String getCodigoConta() {
        return _codigoConta;
    }
    public String getNomeConta() {
        return _nomeConta;
    }
}
