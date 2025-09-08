package co.edu.javeriana.Proyecto_Web.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.edu.javeriana.Proyecto_Web.repository.ModelRepository;
import co.edu.javeriana.Proyecto_Web.repository.ShipRepository;
import co.edu.javeriana.Proyecto_Web.repository.UserRepository;
import co.edu.javeriana.Proyecto_Web.model.Ship;
import co.edu.javeriana.Proyecto_Web.model.User;
import co.edu.javeriana.Proyecto_Web.model.Model;
import co.edu.javeriana.Proyecto_Web.model.Cell;

@Component
public class DBInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private ModelRepository modelRepository;


    @Override
    public void run(String... args) throws Exception {
        User user = new User("Juan", "12345", "Usuario");


        userRepository.save(user);

        for (int i = 1; i < 10; i++) {
            User usuario = userRepository.save(new User("Juan" + i, "12345" + i, "Usuario"));
            Model modelo = new Model("Galviz"+i, "Negro");
            Ship ship = new Ship("Emavemaria"+i,2,2,modelo, usuario);
            userRepository.save(usuario);
            modelRepository.save(modelo);
            shipRepository.save(ship);
        }




    }
}
