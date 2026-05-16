package checkpoint.carreira.ackend.service;

import checkpoint.carreira.ackend.dto.DadosAtualizacaoSala;
import checkpoint.carreira.ackend.dto.DadosDetalhamentoSala;
import checkpoint.carreira.ackend.dto.SalaDTO;
import checkpoint.carreira.ackend.entities.Sala;
import checkpoint.carreira.ackend.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service // Marca esta classe como camada de serviço do Spring
public class SalaService {

    private static final Set<String> CAMPOS_ORDENACAO = Set.of("id", "nome", "capacidade", "ativa");

    // Injeta automaticamente o repository de Sala
    @Autowired
    private SalaRepository salaRepository;

    // Método responsável por cadastrar uma nova sala
    public Sala cadastrar(SalaDTO dto) {

        // Cria uma nova entidade Sala usando os dados do DTO
        // e salva no banco de dados
        return salaRepository.save(
                new Sala(dto.nome(), dto.capacidade())
        );
    }

    // Método responsável por listar salas com paginação
    public Page<Sala> listar(Pageable paginacao) {

        // Busca todas as salas no banco
        Pageable paginacaoValidada = PageableUtils.withAllowedSort(paginacao, CAMPOS_ORDENACAO, "nome");
        return salaRepository.findAll(paginacaoValidada);
    }

    // Método responsável por deletar uma sala pelo ID
    public void deletar(Long id) {

        // Busca a sala pelo ID
        // Se não encontrar, lança exceção
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        // Remove a sala do banco
        salaRepository.delete(sala);
    }

    // Método responsável por atualizar informações da sala
    public Sala atualizarInformacoes(Long id, DadosAtualizacaoSala dados) {

        // Busca a sala no banco pelo ID
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrado"));

        // Atualiza o nome somente se ele foi enviado
        if (dados.nome() != null) {
            sala.setNome(dados.nome());
        }

        // Atualiza a capacidade somente se ela foi enviada
        if (dados.capacidade() != null) {
            sala.setCapacidade(dados.capacidade());
        }

        // O Hibernate detecta automaticamente alterações
        // em entidades gerenciadas e executa o UPDATE no banco
        return sala;
    }
}
