package co.edu.javeriana.Proyecto_Web.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private long id;
    private int height;
    private int width;
    private List<CellDTO> cells;
}
