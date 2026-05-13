package checkpoint.carreira.ackend.repository;

import checkpoint.carreira.ackend.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository responsável pelo acesso aos dados da entidade Usuario
// Herdando JpaRepository, o Spring cria automaticamente métodos como:
// save(), delete(), findById(), existsById(), findAll(), etc.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para listar usuários com paginação
    // Pageable permite definir:
    // - tamanho da página
    // - ordenação
    // - página atual
    Page<Usuario> findAll(Pageable pageable);

}