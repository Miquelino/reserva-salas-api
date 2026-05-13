package checkpoint.carreira.ackend.dto;

import checkpoint.carreira.ackend.entities.Usuario;

// DTO usado para devolver os dados detalhados
// de um usuário nas respostas da API.
public record UsuarioDetalhamentoDTO(

        // ID do usuário
        Long id,

        // Nome do usuário
        String nome,

        // Email do usuário
        String email,

        // Idade do usuário
        int idade,

        // CPF do usuário
        String cpf

) {

    // Construtor que recebe uma entidade Usuario
    // e converte automaticamente para DTO.
    public UsuarioDetalhamentoDTO(Usuario usuario) {

        // this(...) chama o construtor principal do record.
        this(
                usuario.getId(),

                // Pega o nome do usuário
                usuario.getNome(),

                // Pega o email do usuário
                usuario.getEmail(),

                // Pega a idade do usuário
                usuario.getIdade(),

                // Pega o CPF do usuário
                usuario.getCpf()
        );
    }
}