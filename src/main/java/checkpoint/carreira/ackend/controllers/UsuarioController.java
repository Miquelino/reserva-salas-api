package checkpoint.carreira.ackend.controllers;

import checkpoint.carreira.ackend.dto.DadosAtualizacaoUsuario;
import checkpoint.carreira.ackend.dto.UsuarioDTO;
import checkpoint.carreira.ackend.dto.UsuarioDetalhamentoDTO;
import checkpoint.carreira.ackend.entities.Usuario;
import checkpoint.carreira.ackend.service.UsuarioService;

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

// Define esta classe como um Controller REST
@RestController
@Tag(name = "Usuarios", description = "Endpoints para cadastro, listagem, atualizacao e exclusao de usuarios")

// Define a rota base dos endpoints
// Se estivesse "/reservasala", todas as rotas começariam com esse caminho
@RequestMapping()
public class UsuarioController {

    // Injeta automaticamente o UsuarioService
    @Autowired
    private UsuarioService service;

    // Endpoint responsável por cadastrar usuários
    // Método HTTP: POST
    @Operation(summary = "Cadastrar usuario", description = "Cria um novo usuario no sistema.")
    @PostMapping("/cadastrarUsuarios")
    public ResponseEntity<UsuarioDetalhamentoDTO> cadastrar(

            // Recebe os dados do corpo da requisição JSON
            // @Valid ativa as validações do DTO
            @RequestBody @Valid UsuarioDTO usuarioDTO,

            // Utilizado para construir a URI do recurso criado
            UriComponentsBuilder uriBuilder) {

        // Chama o service para salvar o usuário no banco
        Usuario usuario = service.cadastrar(usuarioDTO);

        // Cria a URI do usuário criado
        var uri = uriBuilder.path("/reservasala/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        // Retorna:
        // 201 CREATED
        // + usuário criado no body da resposta
        return ResponseEntity.created(uri)
                .body(new UsuarioDetalhamentoDTO(usuario));
    }

    // Endpoint responsável por listar usuários
    // Método HTTP: GET
    @Operation(summary = "Listar usuarios", description = "Lista os usuarios cadastrados com paginacao.")
    @GetMapping("/listarUsuarios")
    public ResponseEntity<Page<UsuarioDetalhamentoDTO>> listar(

            // Configuração padrão da paginação:
            // 10 elementos por página
            // ordenação por nome
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        // Busca usuários paginados
        // e converte cada Usuario para DTO
        Page<UsuarioDetalhamentoDTO> page = service
                .listar(paginacao)
                .map(UsuarioDetalhamentoDTO::new);

        // Retorna 200 OK com os dados
        return ResponseEntity.ok(page);
    }

    // Endpoint responsável por deletar usuários
    // Método HTTP: DELETE
    @Operation(summary = "Deletar usuario", description = "Remove um usuario pelo ID.")
    @DeleteMapping("/deletar/{id}")

    // Garante transação no banco
    @Transactional
    public ResponseEntity<Void> deletar(

            // Captura o ID informado na URL
            @PathVariable Long id) {

        // Chama o service para remover o usuário
        service.deletar(id);

        // Retorna:
        // 204 NO CONTENT
        return ResponseEntity.noContent().build();
    }

    // Endpoint responsável por atualizar usuários
    // Método HTTP: PUT
    @Operation(summary = "Atualizar usuario", description = "Atualiza os dados de um usuario existente.")
    @PutMapping("/atualizarUsuario/{id}")

    // Garante transação no banco
    @Transactional
    public ResponseEntity<UsuarioDetalhamentoDTO> atualizar(

            // Captura o ID da URL
            @PathVariable Long id,

            // Recebe os novos dados no corpo da requisição
            @RequestBody DadosAtualizacaoUsuario dados) {

        // Atualiza os dados do usuário
        Usuario usuario = service.atualizarInformacoes(id, dados);

        // Retorna 200 OK com usuário atualizado
        return ResponseEntity.ok(new UsuarioDetalhamentoDTO(usuario));
    }

}
