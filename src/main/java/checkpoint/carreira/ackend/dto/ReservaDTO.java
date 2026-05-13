package checkpoint.carreira.ackend.dto;

import checkpoint.carreira.ackend.entities.Reserva;

import java.time.LocalDateTime;

// DTO usado para transferir os dados de uma reserva.
// Pode ser usado tanto para resposta quanto para requisição.
public record ReservaDTO(

        // ID da reserva
        Long id,

        // Data e hora de início da reserva
        LocalDateTime inicio,

        // Data e hora de término da reserva
        LocalDateTime fim,

        // ID da sala relacionada à reserva
        Long salaId,

        // ID do usuário que fez a reserva
        Long usuarioId,

        // Nome da sala
        String nome

) {

    // Construtor que converte uma entidade Reserva em DTO.
    public ReservaDTO(Reserva reserva) {

        // this(...) chama o construtor principal do record.
        this(
                reserva.getId(),

                // Pega o horário inicial da reserva
                reserva.getInicio(),

                // Pega o horário final da reserva
                reserva.getFim(),

                // Acessa a entidade Sala relacionada
                // e pega apenas o ID dela
                reserva.getSala().getId(),

                // Acessa a entidade Usuario relacionada
                // e pega apenas o ID
                reserva.getUsuario().getId(),

                // Pega o nome da sala
                reserva.getSala().getNome()
        );
    }
}