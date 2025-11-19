package co.edu.javeriana.Proyecto_Web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import co.edu.javeriana.Proyecto_Web.dto.BoardDTO;
import co.edu.javeriana.Proyecto_Web.dto.LoginDTO;
import co.edu.javeriana.Proyecto_Web.dto.JwtAuthenticationResponse;
import co.edu.javeriana.Proyecto_Web.model.User;
import co.edu.javeriana.Proyecto_Web.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        userRepository.save(new User("admin", passwordEncoder.encode("password"), "admin"));

        Board board1 = new Board(10, 10, null);
        Board board2 = new Board(15, 15, null);
        Board board3 = new Board(20, 20, null);

        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
    }

    private String loginAndGetToken(String username, String password) {
        LoginDTO login = new LoginDTO(username, password);
        JwtAuthenticationResponse resp = webTestClient.post().uri("/api/auth/login")
                .bodyValue(login)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtAuthenticationResponse.class)
                .returnResult()
                .getResponseBody();

        return resp != null ? resp.getToken() : null;
    }

    @Test
    void getBoards() {
        String token = loginAndGetToken("admin", "password");

        webTestClient.get().uri("/board/list")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BoardDTO.class)
                .hasSize(3);
    }

    @Test
    void getBoardById() {
        Board savedBoard = boardRepository.save(new Board(12, 12, null));
        String token = loginAndGetToken("admin", "password");

        webTestClient.get().uri("/board/view/" + savedBoard.getId())
                .header("Authorization", "Bearer " + token)
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
        String token = loginAndGetToken("admin", "password");

        webTestClient.post().uri("/board")
                .header("Authorization", "Bearer " + token)
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

        String token = loginAndGetToken("admin", "password");

        webTestClient.put().uri("/board")
                .header("Authorization", "Bearer " + token)
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
        String token = loginAndGetToken("admin", "password");

        webTestClient.delete().uri("/board/" + savedBoard.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void generateDefaultMap() {
        String token = loginAndGetToken("admin", "password");

        webTestClient.post().uri("/board/generate?width=15&height=11")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoardDTO.class)
                .value(board -> {
                    assert board.getWidth() == 15;
                    assert board.getHeight() == 11;
                });
    }
}
