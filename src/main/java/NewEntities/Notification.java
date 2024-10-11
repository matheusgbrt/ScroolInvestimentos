package NewEntities;

import java.util.Date;

public class Notification {
    Date _date;
    String _text;

    public Notification(Date data, String text) {
        _date = data;
        _text = text;
    }

    public Date getDate() {
        return _date;
    }
    public String getText() {
        return _text;
    }

}
