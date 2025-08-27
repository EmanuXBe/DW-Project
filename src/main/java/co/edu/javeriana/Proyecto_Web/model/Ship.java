package co.edu.javeriana.Proyecto_Web.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Ship {


@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;



private int Xspeed;
private int Yspeed;
private Model model;



public Ship(){

}



public Ship(long id, int xspeed, int yspeed, Model model) {
        this.id = id;
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
