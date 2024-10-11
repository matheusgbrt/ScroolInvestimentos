package View;

import NewEntities.User;
import Utils.Evaluator.DataTypes;
import Utils.Evaluator.EvalPatterns;
import java.util.List;

public class BaseState {
    protected static User _user;

    public void setUser(User user) {
        _user = user;
    }


    protected EvalPatterns getSelectionPattern(List<Integer> allowedValues ){
        EvalPatterns pattern = new EvalPatterns(DataTypes.INTEGER);
        EvalPatterns.IntegerOptions options = new EvalPatterns.IntegerOptions();
        options.allowZero=true;
        options.allowedValues = allowedValues;
        options.allowEmpty=false;
        pattern.setOptions(options);
        return pattern;
    }

    protected EvalPatterns getUsernamePattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 50;
        pattern.setOptions(options);
        return pattern;
    }

    protected EvalPatterns getPasswordPattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 50;
        pattern.setOptions(options);
        return pattern;
    }

    protected EvalPatterns getNomePattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 300;
        pattern.setOptions(options);
        return pattern;
    }

    protected EvalPatterns getNomeContaPattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 50;
        pattern.setOptions(options);
        return pattern;
    }


    protected EvalPatterns getTelefonePattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 30;
        options.regexpattern = "\\(\\d{2}\\)\\d{5}-\\d{4}";
        pattern.setOptions(options);
        return pattern;
    }

    protected EvalPatterns getEmailPattern() {
        EvalPatterns pattern = new EvalPatterns(DataTypes.STRING);
        EvalPatterns.StringOptions options = new EvalPatterns.StringOptions();
        options.allowEmpty = false;
        options.maxLength = 300;
        options.regexpattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        pattern.setOptions(options);
        return pattern;
    }


}
