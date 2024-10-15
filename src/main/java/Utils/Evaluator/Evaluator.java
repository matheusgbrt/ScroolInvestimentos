package Utils.Evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Evaluator {
    Scanner scanner = new Scanner(System.in);
    List<String> errorList = new ArrayList<>();

    String _field = "";
    EvalPatterns _pattern;

    public Evaluator(String field, EvalPatterns pattern) {
        _field = field.toLowerCase();
        _pattern = pattern;
    }

    public Evaluator(String field) {
        _field = field.toLowerCase();
    }

    public void setPattern(EvalPatterns pattern) {
        _pattern = pattern;
    }

    public void setField(String field) {
        _field = field.toLowerCase();
    }


    public EvalReturn EvalData() {
        String message = ReadNext();
        errorList.clear();
        EvalReturn returnValue = new EvalReturn();
        returnValue.message = message;

        switch (_pattern.datatype) {
            case STRING -> returnValue.valid = EvalString(message, (EvalPatterns.StringOptions) _pattern.options);
            case INTEGER -> returnValue.valid = EvalInteger(message, (EvalPatterns.IntegerOptions) _pattern.options);
            case DOUBLE -> returnValue.valid = EvalDouble(message, (EvalPatterns.DoubleOptions) _pattern.options);
        }
        returnValue.errors = errorList;
        return returnValue;
    }

    private boolean EvalString(String message, EvalPatterns.StringOptions options) {
        boolean valid = true;
        if (!options.allowEmpty && message.isEmpty()) {
            valid = false;
            errorList.add(String.format("Campo %s não pode ser vazio!", _field));
            return valid;
        }

        if (message.length() > options.maxLength) {
            errorList.add(String.format("Campo %s excedeu o tamanho máximo de %d", _field, options.maxLength));
        }

        if (!options.regexpattern.isEmpty()){
            Pattern pattern = Pattern.compile(options.regexpattern);
            Matcher matcher = pattern.matcher(message);
            if (!matcher.matches()) {
                valid = false;
                errorList.add(String.format("Campo %s não segue o padrão informado!", _field));
            }
        }
        return valid;
    }

    private boolean EvalInteger(String message, EvalPatterns.IntegerOptions options) {
        boolean valid = true;
        int number;

        try {
            number = Integer.parseInt(message);
        } catch (NumberFormatException e) {
            valid = false;
            errorList.add(String.format("%s precisa ser um número!", _field));
            return valid;
        }

        if (options.allowedValues != null){
            if(!options.allowedValues.contains(number)){
                valid=false;
                errorList.add(String.format("%s não é válido nesta seleção!", _field));
                return valid;
            }
        }

        if (!options.allowEmpty && message.isEmpty()) {
            valid = false;
            errorList.add(String.format("%s não pode ser vazio!", _field));
            return valid;
        }
        if (!options.allowNegative && number < 0) {
            valid = false;
            errorList.add(String.format("%s não pode ser vazio!", _field));
        }
        if (!options.allowZero && number == 0) {
            valid = false;
            errorList.add(String.format("%s não pode ser zero!", _field));
        }
        return valid;
    }

    private boolean EvalDouble(String message, EvalPatterns.DoubleOptions options) {
        boolean valid = true;
        double number;

        try {
            number = Double.parseDouble(message);
        } catch (NumberFormatException e) {
            valid = false;
            errorList.add(String.format("%s precisa ser um número!", _field));
            return valid;
        }


        if (!options.allowEmpty && message.isEmpty()) {
            valid = false;
            errorList.add(String.format("%s não pode ser vazio!", _field));
            return valid;
        }
        if (options.minValue != 0 && number > options.minValue){
            errorList.add(String.format("%s não pode ser maior que %.2f!", _field,options.minValue));
            valid = false;
            return valid;
        }
        if (!options.allowNegative && number < 0) {
            valid = false;
            errorList.add(String.format("%s não pode ser vazio!", _field));
        }
        if (!options.allowZero && number == 0) {
            valid = false;
            errorList.add(String.format("%s não pode ser zero!", _field));
        }
        return valid;
    }

    private String ReadNext() {
        return scanner.nextLine();
    }
}
