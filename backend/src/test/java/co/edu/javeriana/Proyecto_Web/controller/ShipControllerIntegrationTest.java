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

import co.edu.javeriana.Proyecto_Web.dto.ShipDTO;
import co.edu.javeriana.Proyecto_Web.dto.MoveDTO;
import co.edu.javeriana.Proyecto_Web.dto.PossibleMovesDTO;
import co.edu.javeriana.Proyecto_Web.dto.LoginDTO;
import co.edu.javeriana.Proyecto_Web.dto.JwtAuthenticationResponse;
import co.edu.javeriana.Proyecto_Web.model.Model;
import co.edu.javeriana.Proyecto_Web.model.Ship;
import co.edu.javeriana.Proyecto_Web.model.User;
import co.edu.javeriana.Proyecto_Web.repository.ModelRepository;
import co.edu.javeriana.Proyecto_Web.repository.ShipRepository;
import co.edu.javeriana.Proyecto_Web.repository.UserRepository;
import co.edu.javeriana.Proyecto_Web.service.BoardService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ShipControllerIntegrationTest {

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private Model testModel;

    @BeforeEach
    void init() {
        userRepository.save(new User("admin", passwordEncoder.encode("password"), "admin"));
        testUser = userRepository.save(new User("testowner", passwordEncoder.encode("password"), "user"));
        testModel = modelRepository.save(new Model("TestModel", "Blue"));

        shipRepository.save(new Ship("Ship1", testModel, testUser));
        shipRepository.save(new Ship("Ship2", testModel, testUser));
        shipRepository.save(new Ship("Ship3", testModel, testUser));
    }

    private String loginAndGetToken(String username, String password) {
        LoginDTO login = new LoginDTO(username, password);
        JwtAuthenticationResponse resp = webTestClient.post().uri("/auth/login")
                .bodyValue(login)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtAuthenticationResponse.class)
                .returnResult()
                .getResponseBody();

        return resp != null ? resp.getToken() : null;
    }

    @Test
    void getShips() {
        String token = loginAndGetToken("admin", "password");

        webTestClient.get().uri("/ship")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShipDTO.class)
                .hasSize(3);
    }

    @Test
    void getShipById() {
        Ship savedShip = shipRepository.save(new Ship("TestShip", testModel, testUser));
        String token = loginAndGetToken("admin", "password");

        webTestClient.get().uri("/ship/" + savedShip.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShipDTO.class)
                .value(ship -> {
                    assert ship.getName().equals("TestShip");
                });
    }

    @Test
    void createShipPrototype() {
        String token = loginAndGetToken("admin", "password");

        webTestClient.get().uri("/ship/create")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShipDTO.class);
    }

    @Test
    void createShip() {
        ShipDTO newShip = new ShipDTO();
        newShip.setName("NewShip");
        newShip.setModel(testModel.getName());
        newShip.setOwner(testUser.getName());

        String token = loginAndGetToken("admin", "password");

        webTestClient.post().uri("/ship")
                .header("Authorization", "Bearer " + token)
                .bodyValue(newShip)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShipDTO.class)
                .value(ship -> {
                    assert ship.getName().equals("NewShip");
                });
    }

    @Test
    void updateShip() {
        Ship savedShip = shipRepository.save(new Ship("OldName", testModel, testUser));

        ShipDTO updateDTO = new ShipDTO();
        updateDTO.setName("UpdatedName");
        updateDTO.setModel(testModel.getName());
        updateDTO.setOwner(testUser.getName());

        String token = loginAndGetToken("admin", "password");

        webTestClient.put().uri("/ship/" + savedShip.getId())
                .header("Authorization", "Bearer " + token)
                .bodyValue(updateDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShipDTO.class)
                .value(ship -> {
                    assert ship.getName().equals("UpdatedName");
                });
    }

    @Test
    void deleteShip() {
        Ship savedShip = shipRepository.save(new Ship("ToDelete", testModel, testUser));
        String token = loginAndGetToken("admin", "password");

        webTestClient.delete().uri("/ship/" + savedShip.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void searchShips() {
        String token = loginAndGetToken("admin", "password");

        webTestClient.get().uri("/ship/search?searchText=TestModel")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShipDTO.class)
                .hasSize(3);
    }
}
