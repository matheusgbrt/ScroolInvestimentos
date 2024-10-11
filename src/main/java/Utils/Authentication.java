package Utils;

import Services.UsersDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Authentication {
    String _username;
    String _password;
    String hashPassword;
    UsersDAO service = new UsersDAO();
    public Authentication(String _username, String _password) {
        this._username = _username;
        this._password = _password;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public boolean Validate() {
        if (service.getUserExists(_username) > 0){
            hashPassword=service.getUserPassword(_username);
            return true;
        }else {
            return false;
        }
    }

    public boolean Authenticate(){
        hashPassword=service.getUserPassword(_username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(_password, hashPassword);
    }

}
