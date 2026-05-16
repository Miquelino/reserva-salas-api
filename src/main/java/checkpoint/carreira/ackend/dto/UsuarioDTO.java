package checkpoint.carreira.ackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

// DTO usado para receber os dados de cadastro
// de um usuário.
@Schema(description = "Dados para cadastro de usuario")
public record UsuarioDTO(

        // Não permite nome vazio.
        @NotBlank
        // Define tamanho mínimo e máximo do nome.
        @Size(min = 3, max = 100)
        @Schema(description = "Nome completo do usuario", example = "Ana Silva")
        String nome,

        // Não permite email vazio.
        @NotBlank
        // Valida se o formato do email é válido.
        @Email
        // Define tamanho máximo do email.
        @Size(max = 100)
        @Schema(description = "Email do usuario", example = "ana.silva@email.com")
        String email,

        // Obriga envio da idade.
        @NotNull
        // Define idade mínima.
        @Min(0)
        // Define idade máxima.
        @Max(120)
        @Schema(description = "Idade do usuario", example = "28")
        Integer idade,

        // Não permite CPF vazio.
        @NotBlank
        // Validação específica de CPF brasileiro.
        // Atualmente está comentada.
        //@CPF
        @Schema(description = "CPF do usuario", example = "12345678901")
        String cpf

) {}
