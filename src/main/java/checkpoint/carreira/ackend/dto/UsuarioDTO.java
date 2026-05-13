package checkpoint.carreira.ackend.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

// DTO usado para receber os dados de cadastro
// de um usuário.
public record UsuarioDTO(

        // Não permite nome vazio.
        @NotBlank
        // Define tamanho mínimo e máximo do nome.
        @Size(min = 3, max = 100)
        String nome,

        // Não permite email vazio.
        @NotBlank
        // Valida se o formato do email é válido.
        @Email
        // Define tamanho máximo do email.
        @Size(max = 100)
        String email,

        // Obriga envio da idade.
        @NotNull
        // Define idade mínima.
        @Min(0)
        // Define idade máxima.
        @Max(120)
        Integer idade,

        // Não permite CPF vazio.
        @NotBlank
        // Validação específica de CPF brasileiro.
        // Atualmente está comentada.
        //@CPF
        String cpf

) {}