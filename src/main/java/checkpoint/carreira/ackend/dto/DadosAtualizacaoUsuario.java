package checkpoint.carreira.ackend.dto;

import jakarta.validation.constraints.NotNull;

// Record é uma forma mais simples e moderna de criar classes
// usadas apenas para transportar dados (DTOs).
public record DadosAtualizacaoUsuario(

        // Obriga que o ID seja enviado.
        // Sem ele não é possível saber qual usuário atualizar.
        @NotNull
        Long id,

        // Novo nome do usuário.
        // Pode ser null caso o usuário não queira alterar esse campo.
        String nome,

        // Novo telefone do usuário.
        String telefone,

        // Novo email do usuário.
        String email,

        // Nova idade do usuário.
        Integer idade

) {
}