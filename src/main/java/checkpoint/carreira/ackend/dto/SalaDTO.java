package checkpoint.carreira.ackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// DTO usado para receber os dados de criação
// ou atualização de uma sala.
public record SalaDTO(

        // Não permite texto vazio ou apenas espaços.
        @NotBlank
        // Define tamanho mínimo e máximo do nome.
        @Size(min = 3, max = 100)
        String nome,

        // Obriga que a capacidade seja enviada.
        @NotNull
        // Define que a capacidade mínima deve ser 1.
        @Min(1)
        int capacidade

) {

}