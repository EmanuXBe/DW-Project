package co.edu.javeriana.Proyecto_Web.model;

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


public Cell(char type, int x, int y, Ship ship, Board board) {
    this.type = type;
    this.x = x;
    this.y = y;
    this.ship = ship;
    this.board = board;
    }
}

