package co.edu.javeriana.Proyecto_Web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.javeriana.Proyecto_Web.model.Model;


@Repository
public interface ModelRepository extends JpaRepository<Model, Long>{
    
}
