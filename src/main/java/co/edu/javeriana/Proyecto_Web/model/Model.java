package co.edu.javeriana.Proyecto_Web.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class Model {
    
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;

private String name;
private String color;


@OneToOne
private Ship ship;

public Model(){

}


public Model(String name, String color) {
        this.name = name;
        this.color = color;
    }


public String getName() {
    return name;
}


public void setName(String name) {
    this.name = name;
}


public String getColor() {
    return color;
}


public void setColor(String color) {
    this.color = color;
}


}
