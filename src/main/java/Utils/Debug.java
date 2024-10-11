package Utils;

import Entities.*;

import java.util.List;

public class Debug {

    static void PrintOut(Usuario usuario){
        List<Conta> contas = usuario.getContas();
        List<Notificacao> notificacoes = usuario.getNotificacoes();
        System.out.println("User email: " + usuario.getEmail());
        System.out.println("User name:"+ usuario.getNome());
        System.out.println("Phone:"+usuario.getTelefone());
        System.out.println();
        System.out.println("Notifications:");

        for(Notificacao notificacaoAux : notificacoes) {
            System.out.println("    Notification Date:" + notificacaoAux.getData().toString());
            System.out.println("    Notification Text:" + notificacaoAux.getTexto());
        }
        System.out.println();
        System.out.println("Accounts:");
        for(Conta contaAux : contas) {
            System.out.println();
            System.out.println("AccountCode:" + contaAux.getCodigoConta());
            System.out.println("Products from Account: "+contaAux.getCodigoConta());
            for(Produto produtoAux : contaAux.getProdutos()) {
                System.out.println();
                System.out.println("    Name:" + produtoAux.getNome());
                System.out.println("    Description:" + produtoAux.getDescricao());
                System.out.println("    Balance:" + produtoAux.getSaldo().toString());
            }
            System.out.println();

            System.out.println("Services from Account: "+contaAux.getCodigoConta());
            for(Servico servicoAux : contaAux.getServicos()) {
                System.out.println();
                System.out.println("    Name:" + servicoAux.getNome());
                System.out.println("    Description:" + servicoAux.getDescricao());
            }
        }
    }
}
