package co.edu.javeriana.Proyecto_Web.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Board {
    
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;



private int height;

private int width;




public int getHeight() {
    return height;
}


public void setHeight(int height) {
    this.height = height;
}


public int getWidth() {
    return width;
}


public void setWidth(int width) {
    this.width = width;
}

}
