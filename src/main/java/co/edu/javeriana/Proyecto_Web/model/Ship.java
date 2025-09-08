package co.edu.javeriana.Proyecto_Web.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Ship {


@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;

private String Name;



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


public Ship(String name,int xspeed, int yspeed, Model model, User owner) {
        Name = name;
        Xspeed = xspeed;
        Yspeed = yspeed;
        this.model = model;
        this.owner = owner;
    }

    
    
public void setName(String name) {
    Name = name;
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getName() {
    return Name;
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

public void setOwner(User owner) {
    this.owner = owner;
}


public User getOwner() {
    return owner;
}


    
}
