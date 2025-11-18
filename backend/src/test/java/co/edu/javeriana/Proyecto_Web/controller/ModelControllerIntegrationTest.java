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

import co.edu.javeriana.Proyecto_Web.dto.ModelDTO;
import co.edu.javeriana.Proyecto_Web.model.Model;
import co.edu.javeriana.Proyecto_Web.repository.ModelRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ModelControllerIntegrationTest {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void init() {
        modelRepository.save(new Model("Model1", "Red"));
        modelRepository.save(new Model("Model2", "Blue"));
        modelRepository.save(new Model("Model3", "Green"));
    }

    @Test
    void getModels() {
        webTestClient.get().uri("/model")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ModelDTO.class)
                .hasSize(3);
    }

    @Test
    void getModelById() {
        Model savedModel = modelRepository.save(new Model("TestModel", "Yellow"));

        webTestClient.get().uri("/model/" + savedModel.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ModelDTO.class)
                .value(model -> {
                    assert model.getName().equals("TestModel");
                    assert model.getColor().equals("Yellow");
                });
    }

    @Test
    void createModelPrototype() {
        webTestClient.get().uri("/model/create")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ModelDTO.class);
    }

    @Test
    void createModel() {
        ModelDTO newModel = new ModelDTO();
        newModel.setName("NewModel");
        newModel.setColor("Purple");

        webTestClient.post().uri("/model")
                .bodyValue(newModel)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ModelDTO.class)
                .value(model -> {
                    assert model.getName().equals("NewModel");
                    assert model.getColor().equals("Purple");
                });
    }

    @Test
    void updateModel() {
        Model savedModel = modelRepository.save(new Model("OldName", "OldColor"));

        ModelDTO updateDTO = new ModelDTO();
        updateDTO.setName("UpdatedName");
        updateDTO.setColor("UpdatedColor");

        webTestClient.put().uri("/model/" + savedModel.getId())
                .bodyValue(updateDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ModelDTO.class)
                .value(model -> {
                    assert model.getName().equals("UpdatedName");
                    assert model.getColor().equals("UpdatedColor");
                });
    }

    @Test
    void deleteModel() {
        Model savedModel = modelRepository.save(new Model("ToDelete", "Black"));

        webTestClient.delete().uri("/model/" + savedModel.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void searchModels() {
        webTestClient.get().uri("/model/search?searchText=Model")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ModelDTO.class)
                .hasSize(3);
    }
}
