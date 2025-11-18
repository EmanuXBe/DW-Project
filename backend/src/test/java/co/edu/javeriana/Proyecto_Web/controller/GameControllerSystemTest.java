package co.edu.javeriana.Proyecto_Web.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.WaitForSelectorState;

import co.edu.javeriana.Proyecto_Web.model.Board;
import co.edu.javeriana.Proyecto_Web.model.Cell;
import co.edu.javeriana.Proyecto_Web.model.Model;
import co.edu.javeriana.Proyecto_Web.model.Ship;
import co.edu.javeriana.Proyecto_Web.model.User;
import co.edu.javeriana.Proyecto_Web.repository.BoardRepository;
import co.edu.javeriana.Proyecto_Web.repository.ModelRepository;
import co.edu.javeriana.Proyecto_Web.repository.ShipRepository;
import co.edu.javeriana.Proyecto_Web.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("system-testing")
public class GameControllerSystemTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private BoardRepository boardRepository;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    private User testUser;
    private Model testModel;
    private Ship testShip;
    private Board testBoard;

    @BeforeEach
    void init() {
        // Crear usuario de prueba
        testUser = userRepository.save(new User("testplayer", "password", "user"));

        // Crear modelo de prueba
        testModel = modelRepository.save(new Model("Test Model", "Red"));

        // Crear barco de prueba
        testShip = new Ship();
        testShip.setName("Test Ship");
        testShip.setOwner(testUser);
        testShip.setModel(testModel);
        testShip = shipRepository.save(testShip);

        // Crear un tablero simple de 5x5 con un camino directo a la meta
        List<Cell> cells = new ArrayList<>();

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Cell cell = new Cell();
                cell.setX(x);
                cell.setY(y);

                // Celda de inicio en (0, 2)
                if (x == 0 && y == 2) {
                    cell.setType('P'); // START
                }
                // Celda de meta en (4, 2)
                else if (x == 4 && y == 2) {
                    cell.setType('M'); // FINISH
                }
                // Agua en el camino central
                else if (y == 2) {
                    cell.setType(' '); // WATER
                }
                // Muros arriba y abajo
                else {
                    cell.setType('X'); // WALL
                }

                cells.add(cell);
            }
        }

        testBoard = new Board(5, 5, cells);

        // Asignar el board a cada celda después de crear el board
        for (Cell cell : cells) {
            cell.setBoard(testBoard);
        }

        testBoard = boardRepository.save(testBoard);

        // Inicializar Playwright
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));
        this.browserContext = browser.newContext();
        this.page = browserContext.newPage();
    }

    @AfterEach
    void end() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    void testPlayerWinsGame() throws InterruptedException {
        // Navegar a la página del juego
        String gameUrl = String.format("http://localhost:4200/game?shipId=%d&boardId=%d",
                testShip.getId(), testBoard.getId());
        page.navigate(gameUrl);

        // Esperar a que el juego cargue
        Locator gameTitle = page.locator("#game-title");
        gameTitle.waitFor();
        PlaywrightAssertions.assertThat(gameTitle).containsText("Carrera de Barcos");

        // Esperar a que la información del juego esté visible
        Locator gameInfo = page.locator("#game-info");
        gameInfo.waitFor();

        // Verificar que el juego ha comenzado
        Locator shipName = page.locator("#game-info-ship");
        PlaywrightAssertions.assertThat(shipName).containsText("Test Ship");

        // Movimiento 1: Acelerar hacia la derecha (deltaVx=1, deltaVy=0)
        // Esto debería mover el barco de (0,2) con velocidad (1,0) a posición (1,2)
        Thread.sleep(1000); // Esperar a que los movimientos se carguen
        Locator moveBtn1 = page.locator("#game-move-btn-1-0");
        moveBtn1.click();

        Locator executeBtn = page.locator("#game-btn-execute");
        executeBtn.click();

        // Esperar a que se procese el movimiento
        Thread.sleep(1000);

        // Movimiento 2: Mantener velocidad (deltaVx=0, deltaVy=0)
        // Esto debería mover el barco a (2,2) con velocidad (1,0)
        Locator moveBtn2 = page.locator("#game-move-btn-0-0");
        moveBtn2.click();

        executeBtn = page.locator("#game-btn-execute");
        executeBtn.click();

        Thread.sleep(1000);

        // Movimiento 3: Mantener velocidad de nuevo
        // Esto debería mover el barco a (3,2) con velocidad (1,0)
        Locator moveBtn3 = page.locator("#game-move-btn-0-0");
        moveBtn3.click();

        executeBtn = page.locator("#game-btn-execute");
        executeBtn.click();

        Thread.sleep(1000);

        // Movimiento 4: Mantener velocidad una vez más
        // Esto debería mover el barco a (4,2) que es la META - ¡El jugador gana!
        Locator moveBtn4 = page.locator("#game-move-btn-0-0");
        moveBtn4.click();

        executeBtn = page.locator("#game-btn-execute");
        executeBtn.click();

        // Esperar a que aparezca el mensaje de victoria
        Thread.sleep(2000);

        // Verificar que el mensaje de "Juego Terminado" aparece
        Locator gameOverSection = page.locator("#game-over-section");
        gameOverSection.waitFor(new Locator.WaitForOptions().setTimeout(5000));

        Locator gameOverTitle = page.locator("#game-over-title");
        PlaywrightAssertions.assertThat(gameOverTitle).containsText("¡Juego Terminado!");

        // Verificar que muestra el número de turnos
        Locator gameOverMessage = page.locator("#game-over-message");
        PlaywrightAssertions.assertThat(gameOverMessage).containsText("Completaste la carrera");

        Locator gameOverTurns = page.locator("#game-over-turns");
        PlaywrightAssertions.assertThat(gameOverTurns).containsText("4");

        // Verificar que el botón de volver existe
        Locator backButton = page.locator("#game-over-btn-back");
        PlaywrightAssertions.assertThat(backButton).isVisible();

    }
}
