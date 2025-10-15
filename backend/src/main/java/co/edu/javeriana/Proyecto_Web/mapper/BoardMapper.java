package co.edu.javeriana.Proyecto_Web.mapper;

import co.edu.javeriana.Proyecto_Web.dto.BoardDTO;
import co.edu.javeriana.Proyecto_Web.model.Board;
import java.util.stream.Collectors;

public class BoardMapper {

    public static BoardDTO toDto(Board board) {
        BoardDTO dto = new BoardDTO();
        dto.setId(board.getId());
        dto.setHeight(board.getHeight());
        dto.setWidth(board.getWidth());

        if (board.getCell() != null) {
            dto.setCells(board.getCell().stream()
                    .map(CellMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static Board toEntity(BoardDTO dto) {
        Board board = new Board();
        board.setId(dto.getId());
        board.setHeight(dto.getHeight());
        board.setWidth(dto.getWidth());
        return board;
    }
}
