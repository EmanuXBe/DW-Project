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

import co.edu.javeriana.Proyecto_Web.dto.UserDTO;
import co.edu.javeriana.Proyecto_Web.model.User;
import co.edu.javeriana.Proyecto_Web.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class UserControllerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void init() {
        userRepository.save(new User("testuser", "password", "admin"));
        userRepository.save(new User("testuser1", "contraseña", "user"));
        userRepository.save(new User("testuser2", "monda", "user"));
    }

    @Test
    void getUsers() {
        webTestClient.get().uri("/user/list")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDTO.class)
                .hasSize(3);
    }

    @Test
    void getUserById() {
        User savedUser = userRepository.save(new User("testuser", "password", "admin"));

        webTestClient.get().uri("/user/view/" + savedUser.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class)
                .value(user -> {
                    assert user.getName().equals("testuser");
                    assert user.getType().equals("admin");
                });
    }

    @Test
    void createUserPrototype() {
        webTestClient.get().uri("/user/create")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class);
    }

    @Test
    void createUser() {
        webTestClient.post().uri("/user")
                .bodyValue(new UserDTO(null, "newuser", "user", "newpass"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class)
                .value(user -> {
                    assert user.getName().equals("newuser");
                    assert user.getType().equals("user");
                });
    }

    @Test
    void updateUser() {
        User savedUser = userRepository.save(new User("testuser", "password", "admin"));

        webTestClient.put().uri("/user")
                .bodyValue(new UserDTO(savedUser.getId(), "updateduser", "user", "newpass"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class)
                .value(user -> {
                    assert user.getName().equals("updateduser");
                    assert user.getType().equals("user");
                });
    }

    @Test
    void deleteUser() {
        User savedUser = userRepository.save(new User("testuser", "password", "admin"));

        webTestClient.delete().uri("/user/" + savedUser.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void searchUsers() {
        webTestClient.get().uri("/user/search?searchText=testuser")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDTO.class)
                .hasSize(3);
    }

}