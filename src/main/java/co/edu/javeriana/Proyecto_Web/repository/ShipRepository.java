package co.edu.javeriana.Proyecto_Web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.Proyecto_Web.model.Ship;


@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
    
}
