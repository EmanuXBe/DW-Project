package co.edu.javeriana.Proyecto_Web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CellDTO {
    private long id;
    private char type;
    private int x;
    private int y;
    private Long shipId;
    private String shipName;
}
