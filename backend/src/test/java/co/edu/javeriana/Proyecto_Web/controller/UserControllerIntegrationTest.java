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

import co.edu.javeriana.Proyecto_Web.dto.UserDTO;
import co.edu.javeriana.Proyecto_Web.dto.LoginDTO;
import co.edu.javeriana.Proyecto_Web.dto.JwtAuthenticationResponse;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        userRepository.save(new User("testuser", passwordEncoder.encode("password"), "admin"));
        userRepository.save(new User("testuser1", passwordEncoder.encode("contraseña"), "user"));
        userRepository.save(new User("testuser2", passwordEncoder.encode("monda"), "user"));
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
    void getUsers() {
        String token = loginAndGetToken("testuser", "password");
        webTestClient.get().uri("/user/list")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDTO.class)
                .hasSize(3);
    }

    @Test
    void getUserById() {
        String token = loginAndGetToken("testuser", "password");
        User existingUser = userRepository.findByName("testuser").orElseThrow();

        webTestClient.get().uri("/user/view/" + existingUser.getId())
                .header("Authorization", "Bearer " + token)
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
        String token = loginAndGetToken("testuser", "password");

        webTestClient.get().uri("/user/create")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class);
    }

    @Test
    void createUser() {
        String token = loginAndGetToken("testuser", "password");

        webTestClient.post().uri("/user")
                .header("Authorization", "Bearer " + token)
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
        String token = loginAndGetToken("testuser", "password");
        User existingUser = userRepository.findByName("testuser").orElseThrow();

        webTestClient.put().uri("/user")
                .header("Authorization", "Bearer " + token)
                .bodyValue(new UserDTO(existingUser.getId(), "updateduser", "user", "newpass"))
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
        String token = loginAndGetToken("testuser", "password");
        User existingUser = userRepository.findByName("testuser1").orElseThrow();

        webTestClient.delete().uri("/user/" + existingUser.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void searchUsers() {
        String token = loginAndGetToken("testuser", "password");

        webTestClient.get().uri("/user/search?searchText=testuser")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDTO.class)
                .hasSize(3);
    }

}