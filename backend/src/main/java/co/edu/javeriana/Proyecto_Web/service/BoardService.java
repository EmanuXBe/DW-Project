package co.edu.javeriana.Proyecto_Web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.Proyecto_Web.dto.BoardDTO;
import co.edu.javeriana.Proyecto_Web.mapper.BoardMapper;
import co.edu.javeriana.Proyecto_Web.model.Board;
import co.edu.javeriana.Proyecto_Web.model.Cell;
import co.edu.javeriana.Proyecto_Web.repository.BoardRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardDTO> listBoards() {
        return boardRepository.findAll().stream()
                .map(BoardMapper::toDto)
                .toList();
    }

    public Optional<BoardDTO> searchBoard(Long id) {
        return boardRepository.findById(id)
                .map(BoardMapper::toDto);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + id));
    }

    public BoardDTO save(BoardDTO boardDTO) {
        Board board = BoardMapper.toEntity(boardDTO);
        Board savedBoard = boardRepository.save(board);
        return BoardMapper.toDto(savedBoard);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    /**
     * Genera un mapa de ejemplo con dimensiones específicas
     */
    public BoardDTO generateDefaultMap(int width, int height) {
        Board board = new Board();
        board.setWidth(width);
        board.setHeight(height);

        List<Cell> cells = new ArrayList<>();

        // Generar el mapa predeterminado
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = new Cell();
                cell.setX(x);
                cell.setY(y);
                cell.setBoard(board);

                // Lógica para determinar el tipo de celda
                char type = determineCellType(x, y, width, height);
                cell.setType(type);

                cells.add(cell);
            }
        }

        board.setCell(cells);
        Board savedBoard = boardRepository.save(board);
        return BoardMapper.toDto(savedBoard);
    }

    /**
     * Determina el tipo de celda según su posición
     */
    private char determineCellType(int x, int y, int width, int height) {
        // Posiciones de partida (segunda fila, columna 2) - PRIMERO para evitar ser
        // sobrescritas
        if (x == 1 && (y == 1 || y == 2)) {
            return 'P';
        }

        // Posiciones de meta (penúltima fila, columnas 1-3)
        if (y == height - 2 && (x >= 1 && x <= 3)) {
            return 'M';
        }

        // Bordes = Pared
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
            return 'X';
        }

        // Obstáculos distribuidos (patrón aleatorio pero fijo)
        if (isObstacle(x, y)) {
            return 'X';
        }

        // Por defecto = Agua
        return ' ';
    }

    /**
     * Define posiciones de obstáculos
     */
    private boolean isObstacle(int x, int y) {
        // Patrón de obstáculos
        return (x == 3 && y == 1) ||
                (x == 7 && y == 1) ||
                (x == 12 && y == 1) ||
                (x == 4 && y == 2) ||
                (x == 7 && y == 2) ||
                (x == 9 && y == 2) ||
                (x == 12 && y == 2) ||
                (x == 4 && y == 3) ||
                (x == 6 && y == 3) ||
                (x == 10 && y == 3) ||
                (x == 2 && y == 4) ||
                (x == 5 && y == 4) ||
                (x == 10 && y == 4) ||
                (x == 12 && y == 4) ||
                (x == 3 && y == 5) ||
                (x == 6 && y == 5) ||
                (x == 8 && y == 5) ||
                (x == 11 && y == 5) ||
                (x == 1 && y == 6) ||
                (x == 4 && y == 6) ||
                (x == 10 && y == 6) ||
                (x == 13 && y == 6) ||
                (x == 4 && y == 7) ||
                (x == 6 && y == 7) ||
                (x == 8 && y == 7) ||
                (x == 11 && y == 7) ||
                (x == 2 && y == 8) ||
                (x == 5 && y == 8) ||
                (x == 9 && y == 8) ||
                (x == 12 && y == 8) ||
                (x == 4 && y == 9) ||
                (x == 10 && y == 9) ||
                (x == 12 && y == 9);
    }
}
