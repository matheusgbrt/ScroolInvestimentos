package View;

import NewEntities.Notification;
import NewEntities.User;
import Services.NotificationsDAO;
import Utils.Evaluator.EvalReturn;
import Utils.Evaluator.Evaluator;
import Utils.ScreenWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationState extends BaseState {

    private List<Notification> list;
    NotificationsDAO notificationsDAO = new NotificationsDAO();

    public NotificationState(User user) {
        setUser(user);
        ScreenWriter.WritePadded("Notificações");
        ShowNotifications();
    }

    private void ShowNotifications() {
        getNotifications();
        if (!list.isEmpty()) {
            WriteNotifications();
            AskRead();
        }else{
            ScreenWriter.Write("Nenhuma notificação encontrada!");
            ShowMain();
        }
    }

    private void ShowMain(){
        new  MainState(_user,false);
    }

    private void AskRead() {
        ScreenWriter.Write("Deseja marcar estas notificações como lidas?");
        ScreenWriter.Write("1. Sim");
        ScreenWriter.Write("2. Não");
        Evaluator eval = new Evaluator("seleção",getSelectionPattern(new ArrayList<>(Arrays.asList(0,1, 2, 3, 4))));
        EvalReturn evalReturn = eval.EvalData();
        if (!evalReturn.valid){
            evalReturn.errors.forEach(ScreenWriter::Write);
            AskRead();
        }else{
            Decider(Integer.parseInt(evalReturn.message));
        }
    }

    private void Decider(Integer selection){
        if (selection == 1) {
            notificationsDAO.updateRead(_user.getIdusuario());
        }
        ShowMain();
    }


    private void getNotifications(){
        list= notificationsDAO.getUserNotifications(_user.getIdusuario());
    }

    private void WriteNotifications(){
        list.forEach(this::WriteNotification);
    }

    private void WriteNotification(Notification notification) {
        ScreenWriter.Write(String.format("Mensagem: %s",notification.getText()));
        ScreenWriter.Write(String.format("Data: %s",notification.getDate().toString()));
        ScreenWriter.WriteEmptyLine();
    }

}
