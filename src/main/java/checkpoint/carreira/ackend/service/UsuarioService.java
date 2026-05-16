package checkpoint.carreira.ackend.service;

import checkpoint.carreira.ackend.dto.DadosAtualizacaoUsuario;
import checkpoint.carreira.ackend.dto.UsuarioDTO;
import checkpoint.carreira.ackend.entities.Usuario;
import checkpoint.carreira.ackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service // Define esta classe como camada de serviço do Spring
public class UsuarioService {

    private static final Set<String> CAMPOS_ORDENACAO = Set.of("id", "nome", "email", "idade", "cpf");

    // Injeta automaticamente o repository de usuários
    @Autowired
    private UsuarioRepository repository;

    // Método responsável por cadastrar um usuário
    public Usuario cadastrar(UsuarioDTO dto) {

        // Cria uma nova entidade Usuario usando os dados do DTO
        Usuario usuario = new Usuario(dto);

        // Executa as validações da entidade
        usuario.validar();

        // Salva o usuário no banco de dados
        return repository.save(usuario);
    }

    // Método responsável por listar usuários com paginação
    public Page<Usuario> listar(Pageable paginacao) {

        // Busca todos os usuários no banco
        Pageable paginacaoValidada = PageableUtils.withAllowedSort(paginacao, CAMPOS_ORDENACAO, "nome");
        return repository.findAll(paginacaoValidada);
    }

    // Método responsável por deletar um usuário pelo ID
    public void deletar(Long id) {

        // Busca o usuário pelo ID
        // Caso não exista, lança exceção
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Remove o usuário do banco
        repository.delete(usuario);
    }

    // Método responsável por atualizar informações do usuário
    public Usuario atualizarInformacoes(Long id, DadosAtualizacaoUsuario dados) {

        // Busca o usuário no banco pelo ID
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualiza o nome somente se ele foi enviado
        if (dados.nome() != null) {
            usuario.setNome(dados.nome());
        }

        // Atualiza o email somente se ele foi enviado
        if (dados.email() != null) {
            usuario.setEmail(dados.email());
        }

        // Atualiza a idade somente se ela foi enviada
        if (dados.idade() != null) {
            usuario.setIdade(dados.idade());
        }

        // O Hibernate detecta automaticamente as alterações
        // e executa o UPDATE no banco (dirty checking)
        return usuario;
    }

}
