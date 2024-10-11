package View;

import NewEntities.Account;
import NewEntities.User;
import Services.AccountsDAO;
import Utils.Evaluator.EvalReturn;
import Utils.Evaluator.Evaluator;
import Utils.ScreenWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectAccountState extends BaseState {
    Evaluator eval = new Evaluator("Conta");
    AccountsDAO accountsDAO = new AccountsDAO();
    List<Account> _accounts;
    List<Integer> _selectionlist = new ArrayList<Integer>();

    public SelectAccountState(User user) {
        setUser(user);
        GetAccounts();
        initSelection();
    }

    private void initSelection() {
        if (_accounts.isEmpty()) {
            ScreenWriter.Write("Nenhuma conta encontrada!");
            new MainState(_user, false);
        } else {
            ShowGetSelection();
        }
    }

    private void ShowGetSelection() {
        AskAccounts();
        ShowSelection();
        GetSelection();
    }

    private void AskAccounts() {
        ScreenWriter.WriteEmptyLine();
        ScreenWriter.WritePadded("Seleção de contas");
        ScreenWriter.Write("Selecione uma das contas abaixo. Digite VOLTAR caso deseje voltar.");
        ScreenWriter.WriteEmptyLine();
    }

    private void ShowSelection() {
        _selectionlist.clear();
        for (int i = 0; i < _accounts.size(); i++) {
            Account account = _accounts.get(i);
            WriteSelection(account, i);
            _selectionlist.add(i);
        }
    }

    private void GetSelection() {
        eval.setPattern(getSelectionPattern(_selectionlist));
            EvalReturn _ret = eval.EvalData();
            if (Objects.equals(_ret.message, "VOLTAR")) {
                new MainState(_user,true);
                return;
            }
            if (!_ret.valid) {
                _ret.errors.forEach(ScreenWriter::Write);
                ShowGetSelection();
            }
            else {
                new AccountState(_user, _accounts.get(Integer.parseInt(_ret.message)),true);
            }
    }


    private void WriteSelection(Account account, int index) {
        ScreenWriter.Write(String.format("%d. %s - %s",index,account.getCodigoConta(),account.getNomeConta()));
    }


    private void GetAccounts() {
        _accounts = accountsDAO.GetContas(_user.getIdusuario());
    }

}
