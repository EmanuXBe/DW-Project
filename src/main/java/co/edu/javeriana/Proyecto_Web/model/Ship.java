package co.edu.javeriana.Proyecto_Web.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

public class Ship {


@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;



private int Xspeed;

private int Yspeed;

@OneToOne
private Model model;

@OneToOne
private Cell cell;

@ManyToOne 
private User owner;


public Ship(){

}



public Ship(int xspeed, int yspeed, Model model) {
        Xspeed = xspeed;
        Yspeed = yspeed;
        this.model = model;
    }

    

public int getXspeed() {
    return Xspeed;
}


public void setXspeed(int xspeed) {
    Xspeed = xspeed;
}


public int getYspeed() {
    return Yspeed;
}


public void setYspeed(int yspeed) {
    Yspeed = yspeed;
}


public Model getModel() {
    return model;
}


public void setModel(Model model) {
    this.model = model;
}


    
}
