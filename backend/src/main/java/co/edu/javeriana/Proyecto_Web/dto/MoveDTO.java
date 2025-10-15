package co.edu.javeriana.Proyecto_Web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoveDTO {
    // Cambio en velocidad: -1, 0, o +1
    private int deltaVx;
    private int deltaVy;
}
