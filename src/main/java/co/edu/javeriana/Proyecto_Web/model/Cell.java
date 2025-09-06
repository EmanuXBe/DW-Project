package co.edu.javeriana.Proyecto_Web.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cell {
    
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;

private char type;
private int x;
private int y;

@OneToOne
private Ship ship;

@ManyToOne
private Board board;


public char getType() {
    return type;
}


public void setType(char type) {
    this.type = type;
}


public int getX() {
    return x;
}


public void setX(int x) {
    this.x = x;
}


public int getY() {
    return y;
}


public void setY(int y) {
    this.y = y;
}

}

