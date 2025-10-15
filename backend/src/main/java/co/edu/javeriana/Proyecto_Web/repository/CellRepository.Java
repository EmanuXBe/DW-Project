package co.edu.javeriana.Proyecto_Web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.javeriana.Proyecto_Web.model.Cell;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
    
}
