package Utils;

import java.util.List;

public  class ScreenWriter {


    public static void WritePadded(String message){
        String paddingLeft = "*-*-*---";
        String paddingRight ="---*-*-*";
        System.out.println(paddingLeft + message + paddingRight);
    }

    public static void Write(String message){
        System.out.println(message);
    }

    public static void WriteEmptyLine(){
        System.out.println("");
    }


}
