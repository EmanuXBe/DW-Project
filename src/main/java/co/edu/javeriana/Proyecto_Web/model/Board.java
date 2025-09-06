package co.edu.javeriana.Proyecto_Web.model;

import java.util.HashMap;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Board {
    
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;



private int height;

private int width;

@OneToMany
private List<Cell> cell;



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
