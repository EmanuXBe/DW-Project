package co.edu.javeriana.Proyecto_Web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipDTO {
    private long id;
    private String name;

    // Velocidad (vector de velocidad)
    private int vx;
    private int vy;

    // Posición actual en el tablero
    private int posX;
    private int posY;

    // Estado de la carrera
    private int turnCount;
    private boolean racing;
    private boolean finished;

    private String model;
    private String color;
    private String owner;
}
