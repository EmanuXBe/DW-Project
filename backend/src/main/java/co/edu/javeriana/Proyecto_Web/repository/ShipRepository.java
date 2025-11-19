package co.edu.javeriana.Proyecto_Web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.Proyecto_Web.model.Ship;
import co.edu.javeriana.Proyecto_Web.model.User;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {

    List<Ship> findAllByModel_Name(String searchText);

    List<Ship> findAllByModel_NameStartingWithIgnoreCase(String searchText);

    Optional<Ship> findByOwner(User owner);

}
