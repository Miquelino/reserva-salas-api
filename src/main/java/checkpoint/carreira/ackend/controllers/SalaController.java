package checkpoint.carreira.ackend.controllers;

import checkpoint.carreira.ackend.dto.DadosDetalhamentoSala;
import checkpoint.carreira.ackend.dto.DadosAtualizacaoSala;
import checkpoint.carreira.ackend.dto.SalaDTO;
import checkpoint.carreira.ackend.entities.Sala;
import checkpoint.carreira.ackend.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

// Indica que essa classe é um controller REST,
// ou seja, ela recebe requisições HTTP e devolve respostas JSON.
@RestController
@Tag(name = "Salas", description = "Endpoints para cadastro, listagem, atualizacao e exclusao de salas")

// Define o caminho base de todas as rotas desse controller.
@RequestMapping("/salas")
public class SalaController {

    // Injeta automaticamente o service responsável
    // pelas regras de negócio relacionadas às salas.
    @Autowired
    private SalaService salaService;

    // Endpoint responsável por cadastrar uma nova sala.
    // Recebe requisições POST em /salas/cadastrar
    @Operation(summary = "Cadastrar sala", description = "Cria uma nova sala para reserva.")
    @PostMapping("/cadastrar")
    public ResponseEntity<DadosDetalhamentoSala> cadastrarSala(

            // @RequestBody pega os dados enviados no corpo da requisição.
            // @Valid executa as validações definidas no DTO.
            @RequestBody @Valid SalaDTO dto,

            // Classe usada para construir a URI do recurso criado.
            UriComponentsBuilder uriBuilder) {

        // Chama o service para salvar a sala no banco.
        Sala sala = salaService.cadastrar(dto);

        // Cria a URI do recurso recém-criado.
        // Exemplo: /salas/1
        var uri = uriBuilder.path("/salas/{id}")
                .buildAndExpand(sala.getId())
                .toUri();

        // Retorna HTTP 201 (Created)
        // junto com os dados da sala cadastrada.
        return ResponseEntity.created(uri)
                .body(new DadosDetalhamentoSala(sala));
    }

    // Endpoint para listar as salas cadastradas.
    // Responde requisições GET em /salas/listarSalas
    @Operation(summary = "Listar salas", description = "Lista as salas cadastradas com paginacao.")
    @GetMapping("/listarSalas")
    public ResponseEntity<Page<DadosDetalhamentoSala>> listarSala(

            // Pageable permite paginação automática.
            // Define tamanho padrão da página e ordenação por nome.
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){

        // Busca as salas no banco de forma paginada.
        // Depois converte cada entidade Sala em DTO.
        Page<DadosDetalhamentoSala> page = salaService
                .listar(paginacao)
                .map(DadosDetalhamentoSala::new);

        // Retorna HTTP 200 com a lista paginada.
        return ResponseEntity.ok(page);
    }

    // Endpoint para deletar uma sala pelo ID.
    // Responde requisições DELETE em /salas/deletar/{id}
    @Operation(summary = "Deletar sala", description = "Remove uma sala pelo ID.")
    @DeleteMapping("/deletar/{id}")
    // Garante que a operação aconteça dentro de uma transação.
    @Transactional
    public ResponseEntity<Void> deletarSala(
            // Captura o ID enviado na URL.
            @PathVariable("id") Long id) {

        // Chama o service para remover a sala.
        salaService.deletar(id);

        // Retorna HTTP 204 (No Content),
        // indicando que a exclusão ocorreu com sucesso.
        return ResponseEntity.noContent().build();
    }

    // Endpoint para atualizar uma sala existente.
    // Responde requisições PUT em /salas/atualizarSala/{id}
    @Operation(
            summary = "Atualizar sala",
            description = "Informe o ID da sala na URL e envie no body apenas os campos que deseja alterar."
    )
    @PutMapping("/atualizarSala/{id}")

    // Mantém a operação dentro de uma transação.
    @Transactional
    public ResponseEntity<DadosDetalhamentoSala> atualizarSala(

            // Recebe o ID da sala pela URL.
            @PathVariable Long id,

            // Recebe os novos dados da sala no corpo da requisição.
            @RequestBody @Valid DadosAtualizacaoSala dados) {

        // Atualiza as informações da sala.
        Sala sala = salaService.atualizarInformacoes(id, dados);

        // Retorna HTTP 200 com os dados atualizados.
        return ResponseEntity.ok(new DadosDetalhamentoSala(sala));
    }
}
