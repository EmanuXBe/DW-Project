package co.edu.javeriana.Proyecto_Web.mapper;

import co.edu.javeriana.Proyecto_Web.dto.CellDTO;
import co.edu.javeriana.Proyecto_Web.model.Cell;

public class CellMapper {

    public static CellDTO toDto(Cell cell) {
        CellDTO dto = new CellDTO();
        dto.setId(cell.getId());
        dto.setType(cell.getType());
        dto.setX(cell.getX());
        dto.setY(cell.getY());

        if (cell.getShip() != null) {
            dto.setShipId(cell.getShip().getId());
            dto.setShipName(cell.getShip().getName());
        }

        return dto;
    }

    public static Cell toEntity(CellDTO dto) {
        Cell cell = new Cell();
        cell.setId(dto.getId());
        cell.setType(dto.getType());
        cell.setX(dto.getX());
        cell.setY(dto.getY());
        return cell;
    }
}
