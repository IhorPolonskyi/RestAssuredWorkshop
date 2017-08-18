package service;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by IT school on 18.08.2017.
 */
@Data
public class User {

    private String name;
    private String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }
}
