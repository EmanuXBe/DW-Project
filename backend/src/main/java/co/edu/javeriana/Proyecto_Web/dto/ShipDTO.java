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
    private int xspeed;
    private int yspeed;
    private String model;
    private String color;
    private String owner;

    

}
