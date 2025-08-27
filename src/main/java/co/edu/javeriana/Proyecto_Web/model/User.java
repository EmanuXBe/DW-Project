package co.edu.javeriana.Proyecto_Web.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class User {


@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;

private String name;
private String password;
private String type;


public User(){
    
}


public User(long id, String name, String password, String type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
    }


public String getName() {
    return name;
}


public void setName(String name) {
    this.name = name;
}


public String getPassword() {
    return password;
}


public void setPassword(String password) {
    this.password = password;
}


public String getType() {
    return type;
}


public void setType(String type) {
    this.type = type;
}


    
}
