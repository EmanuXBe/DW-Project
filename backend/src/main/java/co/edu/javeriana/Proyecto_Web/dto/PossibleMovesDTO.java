package co.edu.javeriana.Proyecto_Web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PossibleMovesDTO {
    // Estado actual del barco
    private int currentX;
    private int currentY;
    private int currentVx;
    private int currentVy;

    // Lista de 9 posiciones posibles
    private List<PositionDTO> possiblePositions;
}
