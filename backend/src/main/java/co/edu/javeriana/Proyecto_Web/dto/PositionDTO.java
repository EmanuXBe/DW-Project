package co.edu.javeriana.Proyecto_Web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int deltaVx;
    private int deltaVy;
    private boolean valid; // Si el movimiento es válido (no choca con muros)
    private String invalidReason; // Razón si es inválido
}
