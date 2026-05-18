package com.teamsys.portafolios.repositories;

import com.teamsys.portafolios.entities.Tecnologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long> {
    List<Tecnologia> findByCategoria(String categoria);

    // Cambiamos boolean por Optional para recuperar la entidad si existe
    Optional<Tecnologia> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}