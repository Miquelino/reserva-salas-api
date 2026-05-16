package checkpoint.carreira.ackend.dto;

import checkpoint.carreira.ackend.entities.Reserva;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

// DTO usado para transferir os dados de uma reserva.
// Pode ser usado tanto para resposta quanto para requisição.
@Schema(description = "Dados de reserva")
public record ReservaDTO(

        // ID da reserva
        @Schema(description = "ID da reserva", example = "1")
        Long id,

        // Data e hora de início da reserva
        @Schema(description = "Data e hora de inicio da reserva", example = "2026-05-16T09:00:00")
        LocalDateTime inicio,

        // Data e hora de término da reserva
        @Schema(description = "Data e hora de fim da reserva", example = "2026-05-16T10:00:00")
        LocalDateTime fim,

        // ID da sala relacionada à reserva
        @Schema(description = "ID da sala reservada", example = "1")
        Long salaId,

        // ID do usuário que fez a reserva
        @Schema(description = "ID do usuario responsavel pela reserva", example = "1")
        Long usuarioId,

        // Nome da sala
        @Schema(description = "Nome da sala reservada", example = "Sala Reuniao 1")
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
