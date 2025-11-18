package co.edu.javeriana.Proyecto_Web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import co.edu.javeriana.Proyecto_Web.dto.BoardDTO;
import co.edu.javeriana.Proyecto_Web.model.Board;
import co.edu.javeriana.Proyecto_Web.repository.BoardRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class BoardControllerIntegrationTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void init() {
        Board board1 = new Board(10, 10, null);
        Board board2 = new Board(15, 15, null);
        Board board3 = new Board(20, 20, null);

        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
    }

    @Test
    void getBoards() {
        webTestClient.get().uri("/board/list")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BoardDTO.class)
                .hasSize(3);
    }

    @Test
    void getBoardById() {
        Board savedBoard = boardRepository.save(new Board(12, 12, null));

        webTestClient.get().uri("/board/view/" + savedBoard.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoardDTO.class)
                .value(board -> {
                    assert board.getWidth() == 12;
                    assert board.getHeight() == 12;
                });
    }

    @Test
    void createBoard() {
        BoardDTO newBoard = new BoardDTO();
        newBoard.setWidth(25);
        newBoard.setHeight(25);

        webTestClient.post().uri("/board")
                .bodyValue(newBoard)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoardDTO.class)
                .value(board -> {
                    assert board.getWidth() == 25;
                    assert board.getHeight() == 25;
                });
    }

    @Test
    void updateBoard() {
        Board savedBoard = boardRepository.save(new Board(10, 10, null));

        BoardDTO updateDTO = new BoardDTO();
        updateDTO.setId(savedBoard.getId());
        updateDTO.setWidth(30);
        updateDTO.setHeight(30);

        webTestClient.put().uri("/board")
                .bodyValue(updateDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoardDTO.class)
                .value(board -> {
                    assert board.getWidth() == 30;
                    assert board.getHeight() == 30;
                });
    }

    @Test
    void deleteBoard() {
        Board savedBoard = boardRepository.save(new Board(10, 10, null));

        webTestClient.delete().uri("/board/" + savedBoard.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void generateDefaultMap() {
        webTestClient.post().uri("/board/generate?width=15&height=11")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoardDTO.class)
                .value(board -> {
                    assert board.getWidth() == 15;
                    assert board.getHeight() == 11;
                });
    }
}
