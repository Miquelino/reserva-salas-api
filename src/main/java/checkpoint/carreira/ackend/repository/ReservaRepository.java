package checkpoint.carreira.ackend.repository;

import checkpoint.carreira.ackend.entities.Reserva;
import checkpoint.carreira.ackend.entities.StatusReserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

// Interface responsável pelo acesso aos dados da entidade Reserva
// JpaRepository já fornece métodos prontos como save, delete, findById e findAll
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Lista reservas com paginação
    Page<Reserva> findAll(Pageable pageable);

    // Busca reservas de uma sala específica com determinado status
    // Exemplo:
    // findBySalaIdAndStatus(1L, StatusReserva.ATIVA)
    List<Reserva> findBySalaIdAndStatus(Long id, StatusReserva statusReserva);

    // Query personalizada usando JPQL
    // Busca reservas que possuem conflito de horário
    @Query("""
    SELECT r FROM Reserva r
    WHERE r.sala.id = :salaId
    AND r.status = 'ATIVA'
    AND r.inicio < :fim
    AND r.fim > :inicio
    """)
    List<Reserva> buscarConflitos(
            Long salaId,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    // Método derivado do nome criado automaticamente pelo Spring Data JPA
    // Verifica se existe uma reserva com conflito de horário
    boolean existsBySalaIdAndStatusAndInicioLessThanAndFimGreaterThan(

            // ID da sala
            Long salaId,

            // Status da reserva (ATIVA/CANCELADA)
            StatusReserva status,

            // Verifica se o início da reserva existente é menor que o novo fim
            LocalDateTime fim,

            // Verifica se o fim da reserva existente é maior que o novo início
            LocalDateTime inicio
    );
}