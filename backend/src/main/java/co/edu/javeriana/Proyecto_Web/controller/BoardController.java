package co.edu.javeriana.Proyecto_Web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.Proyecto_Web.dto.BoardDTO;
import co.edu.javeriana.Proyecto_Web.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/board")
@Tag(name = "Board", description = "Board game management endpoints")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    @Operation(summary = "List all boards", description = "Retrieve a list of all game boards")
    public List<BoardDTO> listBoards() {
        return boardService.listBoards();
    }

    @GetMapping("/view/{id}")
    @Operation(summary = "View board details", description = "Retrieve details of a specific board by ID")
    public BoardDTO viewBoard(@Parameter(description = "ID of the board to retrieve") @PathVariable Long id) {
        return boardService.searchBoard(id).orElseThrow();
    }

    @PostMapping
    @Operation(summary = "Create a new board", description = "Save a new game board")
    public BoardDTO createBoard(@Parameter(description = "Board data to save") @RequestBody BoardDTO dto) {
        dto.setId(0L);
        return boardService.save(dto);
    }

    @PutMapping
    @Operation(summary = "Update a board", description = "Update an existing game board")
    public BoardDTO updateBoard(@Parameter(description = "Updated board data") @RequestBody BoardDTO dto) {
        return boardService.save(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a board", description = "Delete a specific board by ID")
    public void deleteBoard(@Parameter(description = "ID of the board to delete") @PathVariable Long id) {
        boardService.delete(id);
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate default map", description = "Generate a default game map with specified dimensions")
    public BoardDTO generateDefaultMap(
            @Parameter(description = "Width of the board") @RequestParam(defaultValue = "15") int width,
            @Parameter(description = "Height of the board") @RequestParam(defaultValue = "11") int height) {
        return boardService.generateDefaultMap(width, height);
    }
}
