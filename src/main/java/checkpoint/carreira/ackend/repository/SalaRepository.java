package checkpoint.carreira.ackend.repository;

import checkpoint.carreira.ackend.entities.Sala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository responsável pelo acesso aos dados da entidade Sala
// JpaRepository já fornece métodos prontos como:
// save(), findById(), delete(), findAll(), etc.
public interface SalaRepository extends JpaRepository<Sala, Long> {

    // Lista salas utilizando paginação
    // Pageable permite controlar:
    // - quantidade de registros por página
    // - ordenação
    // - número da página
    Page<Sala> findAll(Pageable pageable);
}