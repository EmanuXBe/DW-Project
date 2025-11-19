package co.edu.javeriana.Proyecto_Web.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import co.edu.javeriana.Proyecto_Web.repository.ModelRepository;
import co.edu.javeriana.Proyecto_Web.repository.ShipRepository;
import co.edu.javeriana.Proyecto_Web.repository.UserRepository;
import co.edu.javeriana.Proyecto_Web.model.Ship;
import co.edu.javeriana.Proyecto_Web.model.User;
import co.edu.javeriana.Proyecto_Web.model.Model;
import co.edu.javeriana.Proyecto_Web.model.Cell;
import co.edu.javeriana.Proyecto_Web.service.BoardService;

@Configuration
@Component
@Profile({ "default" })
public class DBInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override

    public void run(String... args) throws Exception {
        // Crear tablero de juego
        System.out.println("Generando tablero de juego...");
        boardService.generateDefaultMap(15, 11);
        System.out.println("Tablero generado exitosamente");

        // Crear usuarios de prueba para autenticación
        System.out.println("Creando usuarios de prueba...");
        User adminUser = new User("testuser", passwordEncoder.encode("password"), "admin");
        User normalUser1 = new User("testuser1", passwordEncoder.encode("contraseña"), "user");
        User normalUser2 = new User("testuser2", passwordEncoder.encode("monda"), "user");

        userRepository.save(adminUser);
        userRepository.save(normalUser1);
        userRepository.save(normalUser2);
        System.out.println("Usuarios de prueba creados: testuser (admin), testuser1 (user), testuser2 (user)");

        // Asignar un barco al usuario testuser1 para poder jugar
        Model modeloJugador = new Model("Velero Rápido", "Azul");
        modelRepository.save(modeloJugador);
        Ship barcoJugador = new Ship("La Reina del Mar", modeloJugador, normalUser1);
        shipRepository.save(barcoJugador);
        System.out.println("Barco asignado a testuser1: 'La Reina del Mar' (Modelo: Velero Rápido, Color: Azul)");

        // Crear usuarios y naves adicionales
        User user = new User("Juan", passwordEncoder.encode("12345"), "user");
        userRepository.save(user);

        String[] colors = { "Rojo", "Azul", "Verde", "Amarillo", "Naranja", "Morado", "Blanco", "Negro", "Rosa" };

        for (int i = 1; i < 10; i++) {
            User usuario = userRepository.save(new User("Juan" + i, passwordEncoder.encode("12345" + i), "user"));
            Model modelo = new Model("Galviz" + i, colors[i - 1]);
            Ship ship = new Ship("Emavemaria" + i, modelo, usuario);
            userRepository.save(usuario);
            modelRepository.save(modelo);
            shipRepository.save(ship);
        }

    }
}
