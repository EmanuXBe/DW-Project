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

private String name;



private int xspeed;

private int yspeed;

@ManyToOne
private Model model;

@OneToOne
private Cell cell;

@ManyToOne 
private User owner;





public Ship(){

}


public Ship(String name,int xspeed, int yspeed, Model model, User owner) {
        this.name = name;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.model = model;
        this.owner = owner;
    }

    
    
public void setName(String name) {
    this.name = name;
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getName() {
    return name;
}




public int getXspeed() {
    return xspeed;
}


public void setXspeed(int xspeed) {
    this.xspeed = xspeed;
}


public int getYspeed() {
    return yspeed;
}


public void setYspeed(int yspeed) {
    this.yspeed = yspeed;
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
