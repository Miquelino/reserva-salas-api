package checkpoint.carreira.ackend.dto;

import checkpoint.carreira.ackend.entities.Sala;

// Record usado para devolver os dados de uma sala.
// Geralmente utilizado nas respostas da API.
public record DadosDetalhamentoSala(

        // ID da sala
        Long id,

        // Nome da sala
        String nome,

        // Capacidade máxima da sala
        Integer capacidade

) {

    // Construtor que recebe uma entidade Sala
    // e transforma ela automaticamente em DTO.
    public DadosDetalhamentoSala(Sala sala) {

        // Chama o construtor principal do record
        // passando os dados vindos da entidade.
        this(
                sala.getId(),
                sala.getNome(),
                sala.getCapacidade()
        );
    }
}