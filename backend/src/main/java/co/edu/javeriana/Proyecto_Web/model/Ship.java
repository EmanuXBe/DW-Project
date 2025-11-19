package co.edu.javeriana.Proyecto_Web.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    // Velocidad del barco (vector de velocidad)
    private int vx = 0;
    private int vy = 0;

    // Posición actual del barco en el tablero
    private int posX = 0;
    private int posY = 0;

    // Contador de turnos
    private int turnCount = 0;

    // Estado del barco en la carrera
    private boolean racing = false;
    private boolean finished = false;

    @ManyToOne
    private Model model;

    @OneToOne
    private Cell cell;

    @ManyToOne
    private User owner;

    public Ship(String name, Model model, User owner) {
        this.name = name;
        this.vx = 0;
        this.vy = 0;
        this.posX = 0;
        this.posY = 0;
        this.model = model;
        this.owner = owner;
    }

}
