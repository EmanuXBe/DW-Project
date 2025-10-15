package co.edu.javeriana.Proyecto_Web.service;

import co.edu.javeriana.Proyecto_Web.dto.MoveDTO;
import co.edu.javeriana.Proyecto_Web.dto.PositionDTO;
import co.edu.javeriana.Proyecto_Web.dto.PossibleMovesDTO;
import co.edu.javeriana.Proyecto_Web.model.Board;
import co.edu.javeriana.Proyecto_Web.model.Cell;
import co.edu.javeriana.Proyecto_Web.model.Ship;
import co.edu.javeriana.Proyecto_Web.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShipMovementService {

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private BoardService boardService;

    /**
     * Calcula las posiciones posibles para el siguiente movimiento
     * Según las reglas: puede mantener, aumentar o disminuir en 1 cada componente
     * PERO NO AMBOS A LA VEZ (solo 5 opciones)
     */
    public PossibleMovesDTO calculatePossibleMoves(Long shipId, Long boardId) {
        Ship ship = shipRepository.findById(shipId)
                .orElseThrow(() -> new RuntimeException("Ship not found"));

        Board board = boardService.findById(boardId);

        List<PositionDTO> possiblePositions = new ArrayList<>();

        // 5 opciones: mantener velocidad, aumentar/disminuir vx, aumentar/disminuir vy
        int[][] deltas = {
                { 0, 0 }, // Mantener velocidad
                { 1, 0 }, // Aumentar vx
                { -1, 0 }, // Disminuir vx
                { 0, 1 }, // Aumentar vy
                { 0, -1 } // Disminuir vy
        };

        for (int[] delta : deltas) {
            int deltaVx = delta[0];
            int deltaVy = delta[1];

            int newVx = ship.getVx() + deltaVx;
            int newVy = ship.getVy() + deltaVy;
            int newX = ship.getPosX() + newVx;
            int newY = ship.getPosY() + newVy;

            // Validar el movimiento
            ValidationResult validation = validateMove(ship, board, newX, newY, newVx, newVy);

            PositionDTO position = new PositionDTO(
                    newX,
                    newY,
                    newVx,
                    newVy,
                    deltaVx,
                    deltaVy,
                    validation.isValid(),
                    validation.getReason());
            possiblePositions.add(position);
        }

        return new PossibleMovesDTO(
                ship.getPosX(),
                ship.getPosY(),
                ship.getVx(),
                ship.getVy(),
                possiblePositions);
    }

    /**
     * Ejecuta un movimiento del barco
     */
    @Transactional
    public Ship executeMove(Long shipId, Long boardId, MoveDTO moveDTO) {
        Ship ship = shipRepository.findById(shipId)
                .orElseThrow(() -> new RuntimeException("Ship not found"));

        Board board = boardService.findById(boardId);

        // Validar que deltaVx y deltaVy solo puedan ser -1, 0 o +1
        if (moveDTO.getDeltaVx() < -1 || moveDTO.getDeltaVx() > 1 ||
                moveDTO.getDeltaVy() < -1 || moveDTO.getDeltaVy() > 1) {
            throw new RuntimeException("Invalid velocity change. Must be -1, 0 or +1");
        }

        // No se puede cambiar ambos a la vez (excepto mantener ambos en 0)
        if (moveDTO.getDeltaVx() != 0 && moveDTO.getDeltaVy() != 0) {
            throw new RuntimeException("Cannot change both vx and vy at the same time");
        }

        // Calcular nueva velocidad y posición
        int newVx = ship.getVx() + moveDTO.getDeltaVx();
        int newVy = ship.getVy() + moveDTO.getDeltaVy();
        int newX = ship.getPosX() + newVx;
        int newY = ship.getPosY() + newVy;

        // Validar el movimiento
        ValidationResult validation = validateMove(ship, board, newX, newY, newVx, newVy);
        if (!validation.isValid()) {
            throw new RuntimeException("Invalid move: " + validation.getReason());
        }

        // Actualizar el barco
        ship.setVx(newVx);
        ship.setVy(newVy);
        ship.setPosX(newX);
        ship.setPosY(newY);
        ship.setTurnCount(ship.getTurnCount() + 1);

        // Verificar si llegó a la meta
        Cell targetCell = getCellAt(board, newX, newY);
        if (targetCell != null && targetCell.getType() == 'M') {
            ship.setFinished(true);
            ship.setRacing(false);
        }

        return shipRepository.save(ship);
    }

    /**
     * Inicia una carrera para un barco en una celda de inicio
     */
    @Transactional
    public Ship startRace(Long shipId, Long boardId, int startX, int startY) {
        Ship ship = shipRepository.findById(shipId)
                .orElseThrow(() -> new RuntimeException("Ship not found"));

        Board board = boardService.findById(boardId);

        // Verificar que la celda de inicio sea válida
        Cell startCell = getCellAt(board, startX, startY);
        if (startCell == null || startCell.getType() != 'P') {
            throw new RuntimeException("Invalid start position");
        }

        // Inicializar el barco
        ship.setPosX(startX);
        ship.setPosY(startY);
        ship.setVx(0);
        ship.setVy(0);
        ship.setTurnCount(0);
        ship.setRacing(true);
        ship.setFinished(false);

        return shipRepository.save(ship);
    }

    /**
     * Valida si un movimiento es válido
     */
    private ValidationResult validateMove(Ship ship, Board board, int newX, int newY, int newVx, int newVy) {
        // Verificar que no salga del tablero
        if (newX < 0 || newX >= board.getWidth() || newY < 0 || newY >= board.getHeight()) {
            return new ValidationResult(false, "Fuera del tablero");
        }

        // Obtener la celda destino
        Cell targetCell = getCellAt(board, newX, newY);
        if (targetCell == null) {
            return new ValidationResult(false, "Celda no encontrada");
        }

        // Verificar que no sea un muro
        if (targetCell.getType() == 'X') {
            return new ValidationResult(false, "No puedes atravesar muros");
        }

        // Validar trayectoria (algoritmo de Bresenham para verificar línea recta)
        if (!isPathClear(board, ship.getPosX(), ship.getPosY(), newX, newY)) {
            return new ValidationResult(false, "La trayectoria atraviesa un muro");
        }

        return new ValidationResult(true, null);
    }

    /**
     * Verifica que la trayectoria entre dos puntos no atraviese muros
     * Usa el algoritmo de Bresenham
     */
    private boolean isPathClear(Board board, int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        int x = x0;
        int y = y0;

        while (true) {
            // Verificar la celda actual
            Cell cell = getCellAt(board, x, y);
            if (cell != null && cell.getType() == 'X') {
                return false;
            }

            // Si llegamos al destino, la trayectoria está libre
            if (x == x1 && y == y1) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }

        return true;
    }

    /**
     * Obtiene la celda en una posición específica del tablero
     */
    private Cell getCellAt(Board board, int x, int y) {
        return board.getCell().stream()
                .filter(cell -> cell.getX() == x && cell.getY() == y)
                .findFirst()
                .orElse(null);
    }

    /**
     * Clase interna para resultado de validación
     */
    private static class ValidationResult {
        private final boolean valid;
        private final String reason;

        public ValidationResult(boolean valid, String reason) {
            this.valid = valid;
            this.reason = reason;
        }

        public boolean isValid() {
            return valid;
        }

        public String getReason() {
            return reason;
        }
    }
}
