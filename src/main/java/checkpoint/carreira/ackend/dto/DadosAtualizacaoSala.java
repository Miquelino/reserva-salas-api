package checkpoint.carreira.ackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para atualizacao de sala")
public record DadosAtualizacaoSala(

        @Size(min = 3, max = 100)
        @Schema(description = "Novo nome da sala", example = "Sala Reuniao Principal")
        String nome,

        @Min(1)
        @Schema(description = "Nova capacidade da sala", example = "20")
        Integer capacidade

) {
}
