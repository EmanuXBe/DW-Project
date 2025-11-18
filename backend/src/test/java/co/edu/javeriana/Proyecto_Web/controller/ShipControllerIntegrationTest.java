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

import co.edu.javeriana.Proyecto_Web.dto.ShipDTO;
import co.edu.javeriana.Proyecto_Web.dto.MoveDTO;
import co.edu.javeriana.Proyecto_Web.dto.PossibleMovesDTO;
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

    private User testUser;
    private Model testModel;

    @BeforeEach
    void init() {
        testUser = userRepository.save(new User("testowner", "password", "user"));
        testModel = modelRepository.save(new Model("TestModel", "Blue"));

        shipRepository.save(new Ship("Ship1", testModel, testUser));
        shipRepository.save(new Ship("Ship2", testModel, testUser));
        shipRepository.save(new Ship("Ship3", testModel, testUser));
    }

    @Test
    void getShips() {
        webTestClient.get().uri("/ship")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShipDTO.class)
                .hasSize(3);
    }

    @Test
    void getShipById() {
        Ship savedShip = shipRepository.save(new Ship("TestShip", testModel, testUser));

        webTestClient.get().uri("/ship/" + savedShip.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShipDTO.class)
                .value(ship -> {
                    assert ship.getName().equals("TestShip");
                });
    }

    @Test
    void createShipPrototype() {
        webTestClient.get().uri("/ship/create")
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

        webTestClient.post().uri("/ship")
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

        webTestClient.put().uri("/ship/" + savedShip.getId())
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

        webTestClient.delete().uri("/ship/" + savedShip.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void searchShips() {
        webTestClient.get().uri("/ship/search?searchText=TestModel")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShipDTO.class)
                .hasSize(3);
    }
}
