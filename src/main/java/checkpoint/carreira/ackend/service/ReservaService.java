package checkpoint.carreira.ackend.service;

import checkpoint.carreira.ackend.dto.ReservaDTO;
import checkpoint.carreira.ackend.entities.Reserva;
import checkpoint.carreira.ackend.entities.Sala;
import checkpoint.carreira.ackend.entities.StatusReserva;
import checkpoint.carreira.ackend.entities.Usuario;
import checkpoint.carreira.ackend.repository.ReservaRepository;
import checkpoint.carreira.ackend.repository.SalaRepository;
import checkpoint.carreira.ackend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service // Define esta classe como uma camada de serviço do Spring
public class ReservaService {

    private static final Set<String> CAMPOS_ORDENACAO = Set.of("id", "inicio", "fim", "status");

    // Injeta o repository responsável pelas reservas
    @Autowired
    private ReservaRepository reservaRepository;

    // Injeta o repository responsável pelos usuários
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Injeta o repository responsável pelas salas
    @Autowired
    private SalaRepository salaRepository;

    // Indica que o método executa dentro de uma transação
    @Transactional
    public Reserva criarReserva(ReservaDTO dto) {

        // Busca a sala pelo ID enviado no DTO
        // Caso não exista, lança exceção
        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        // Busca o usuário pelo ID enviado no DTO
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se a sala está ativa
        if (!sala.isAtiva()) {
            throw new IllegalArgumentException("Sala inativa");
        }

        // Verifica se já existe uma reserva ativa
        // no mesmo horário para essa sala
        boolean existeConflito = reservaRepository
                .existsBySalaIdAndStatusAndInicioLessThanAndFimGreaterThan(
                        sala.getId(),
                        StatusReserva.ATIVA,
                        dto.fim(),
                        dto.inicio()
                );

        // Se existir conflito de horário, impede a reserva
        if (existeConflito) {
            throw new IllegalArgumentException("Conflito de horário");
        }

        // Cria uma nova entidade Reserva
        Reserva nova = new Reserva(
                dto.inicio(),
                dto.fim(),
                sala,
                usuario
        );

        // Salva a reserva no banco e retorna o objeto salvo
        return reservaRepository.save(nova);
    }

    // Lista todas as reservas com paginação
    public Page<Reserva> listar(Pageable pageable) {
        Pageable paginacao = PageableUtils.withAllowedSort(pageable, CAMPOS_ORDENACAO, "inicio");
        return reservaRepository.findAll(paginacao);
    }

    // Remove uma reserva pelo ID
    public void deletar(Long id) {

        // Busca a reserva no banco
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Deleta a reserva encontrada
        reservaRepository.delete(reserva);
    }

    // Atualiza informações da reserva
    public Reserva atualizarInformacoes(Long id, ReservaDTO dados) {

        // Busca a reserva pelo ID
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        // Atualiza a data/hora de início
        reserva.setInicio(dados.inicio());

        // Atualiza a data/hora de fim
        reserva.setFim(dados.fim());

        // Retorna a entidade atualizada
        // Como o método está em contexto transacional,
        // o Hibernate salva automaticamente no banco
        return reserva;
    }
}
