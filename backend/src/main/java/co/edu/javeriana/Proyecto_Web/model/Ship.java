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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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



public Ship(String name,int xspeed, int yspeed, Model model, User owner) {
        this.name = name;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.model = model;
        this.owner = owner;
    }



    
}
