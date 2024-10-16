
import Entities.*;
import View.InitialState;

import java.io.ByteArrayInputStream;

public class Main {

    public static Usuario user;



    public static void main(String[] args) {
        String simulatedInputs = "1\n";
        System.setIn(new ByteArrayInputStream(simulatedInputs.getBytes()));
        InitialState state = new InitialState();
        state.AskMain();

    }
}