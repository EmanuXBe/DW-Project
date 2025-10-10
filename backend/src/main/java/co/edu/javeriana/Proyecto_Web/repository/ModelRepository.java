package co.edu.javeriana.Proyecto_Web.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.javeriana.Proyecto_Web.model.Model;


@Repository
public interface ModelRepository extends JpaRepository<Model, Long>{

    Optional<Model> findByNameAndColor(String name, String color);
    List<Model> findAllByNameStartingWithIgnoreCase(String name);

}
