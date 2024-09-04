import Entities.Usuario;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Usuario usuario = new Usuario(1, "John Doe", "123456789", "john@example.com", new Date());
        System.out.println();
        usuario.login();
        Debug.PrintOut(usuario);
    }
}