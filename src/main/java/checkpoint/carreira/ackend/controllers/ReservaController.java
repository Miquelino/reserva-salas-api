package checkpoint.carreira.ackend.controllers;

// DTOs usados para entrada e saída de dados
import checkpoint.carreira.ackend.dto.DadosAtualizacaoUsuario;
import checkpoint.carreira.ackend.dto.ReservaDTO;
import checkpoint.carreira.ackend.dto.UsuarioDetalhamentoDTO;

// Entidades do sistema
import checkpoint.carreira.ackend.entities.Reserva;
import checkpoint.carreira.ackend.entities.Usuario;

// Camada de serviço
import checkpoint.carreira.ackend.service.ReservaService;

// Importações do Spring/Jakarta
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

// Define esta classe como um controller REST
@RestController

// Define a rota base:
// todas as rotas começam com /reservasala
@RequestMapping("/reservasala")
public class ReservaController {

    // Injeta automaticamente o ReservaService
    @Autowired
    private ReservaService service;

    // Endpoint responsável por criar reservas
    // Método HTTP: POST
    @PostMapping("/reservar")
    public ResponseEntity cadastrar(

            // Recebe os dados da reserva no corpo da requisição
            // @Valid executa validações do DTO
            @RequestBody @Valid ReservaDTO reservaDTO,

            // Usado para criar a URI do recurso criado
            UriComponentsBuilder uriBuilder) {

        // Chama o service para criar a reserva
        Reserva reserva = service.criarReserva(reservaDTO);

        // Monta a URI da reserva criada
        var uri = uriBuilder.path("/reservasala/{id}")
                .buildAndExpand(reserva.getId())
                .toUri();

        // Retorna:
        // 201 CREATED
        // + dados da reserva criada
        return ResponseEntity.created(uri)
                .body(new ReservaDTO(reserva));
    }

    // Endpoint responsável por listar reservas
    // Método HTTP: GET
    @GetMapping("/listarReservas")

    // Mantém a sessão do Hibernate aberta
    // evitando LazyInitializationException
    @Transactional
    public ResponseEntity<Page<ReservaDTO>> listar(

            // Configuração automática de paginação
            @PageableDefault Pageable paginacao) {

        // Busca reservas paginadas
        // e converte cada Reserva para ReservaDTO
        Page<ReservaDTO> page = service
                .listar(paginacao)
                .map(ReservaDTO::new);

        // 200 OK + lista paginada
        return ResponseEntity.ok(page);
    }

    // Endpoint responsável por deletar reservas
    // Método HTTP: DELETE
    @DeleteMapping("/deletar/{id}")

    // Garante transação no banco
    @Transactional
    public ResponseEntity<Void> deletar(

            // Captura o ID da URL
            @PathVariable Long id) {

        // Remove a reserva
        service.deletar(id);

        // Retorna:
        // 204 NO CONTENT
        return ResponseEntity.noContent().build();
    }

    // Endpoint responsável por atualizar reservas
    // Método HTTP: PUT
    @PutMapping("/alterarReserva/{id}")

    // Garante transação durante atualização
    @Transactional
    public ResponseEntity<ReservaDTO> atualizar(

            // Captura o ID da reserva pela URL
            @PathVariable Long id,

            // Recebe os novos dados da reserva
            @RequestBody ReservaDTO dados) {

        // Atualiza a reserva
        Reserva reserva = service.atualizarInformacoes(id, dados);

        // 200 OK + reserva atualizada
        return ResponseEntity.ok(new ReservaDTO(reserva));
    }
}