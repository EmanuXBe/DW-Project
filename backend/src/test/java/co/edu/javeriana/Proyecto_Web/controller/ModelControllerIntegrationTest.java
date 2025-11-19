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

import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.dto.LoginDTO;
import co.edu.javeriana.Proyecto_Web.dto.JwtAuthenticationResponse;
import co.edu.javeriana.Proyecto_Web.model.User;
import co.edu.javeriana.Proyecto_Web.repository.UserRepository;
import co.edu.javeriana.Proyecto_Web.model.Model;
import co.edu.javeriana.Proyecto_Web.repository.ModelRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ModelControllerIntegrationTest {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        userRepository.save(new User("admin", passwordEncoder.encode("password"), "admin"));
        modelRepository.save(new Model("Model1", "Red"));
        modelRepository.save(new Model("Model2", "Blue"));
        modelRepository.save(new Model("Model3", "Green"));
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
    void getModels() {
        String token = loginAndGetToken("admin", "password");

        List<ModelDTO> list = webTestClient.get().uri("/model")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ModelDTO.class)
                .returnResult()
                .getResponseBody();

        assertThat(list).hasSize(3);
    }

    @Test
    void getModelById() {
        Model savedModel = modelRepository.save(new Model("TestModel", "Yellow"));
        String token = loginAndGetToken("admin", "password");

        ModelDTO dto = webTestClient.get().uri("/model/" + savedModel.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ModelDTO.class)
                .returnResult()
                .getResponseBody();

        assertThat(dto.getName()).isEqualTo("TestModel");
        assertThat(dto.getColor()).isEqualTo("Yellow");
    }

    @Test
    void createModel() {
        ModelDTO newModel = new ModelDTO();
        newModel.setName("NewModel");
        newModel.setColor("Purple");

        String token = loginAndGetToken("admin", "password");

        ModelDTO created = webTestClient.post().uri("/model")
                .header("Authorization", "Bearer " + token)
                .bodyValue(newModel)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ModelDTO.class)
                .returnResult()
                .getResponseBody();

        assertThat(created.getName()).isEqualTo("NewModel");
        assertThat(created.getColor()).isEqualTo("Purple");
    }

    @Test
    void updateModel() {
        Model savedModel = modelRepository.save(new Model("OldName", "OldColor"));

        ModelDTO updateDTO = new ModelDTO();
        updateDTO.setName("UpdatedName");
        updateDTO.setColor("UpdatedColor");

        String token = loginAndGetToken("admin", "password");

        ModelDTO updated = webTestClient.put().uri("/model/" + savedModel.getId())
                .header("Authorization", "Bearer " + token)
                .bodyValue(updateDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ModelDTO.class)
                .returnResult()
                .getResponseBody();

        assertThat(updated.getName()).isEqualTo("UpdatedName");
        assertThat(updated.getColor()).isEqualTo("UpdatedColor");
    }

    @Test
    void deleteModel() {
        Model savedModel = modelRepository.save(new Model("ToDelete", "Black"));

        String token = loginAndGetToken("admin", "password");

        webTestClient.delete().uri("/model/" + savedModel.getId())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void searchModels() {
        String token = loginAndGetToken("admin", "password");

        List<ModelDTO> results = webTestClient.get().uri("/model/search?searchText=Model")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ModelDTO.class)
                .returnResult()
                .getResponseBody();

        assertThat(results).hasSize(3);
    }
}
