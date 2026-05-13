package checkpoint.carreira.ackend.entities;

import checkpoint.carreira.ackend.dto.DadosAtualizacaoUsuario;
import checkpoint.carreira.ackend.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

    // Define o atributo como chave primária da tabela
    @Id
    // Faz o banco gerar o ID automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do usuário
    private String nome;

    // Idade do usuário
    private int idade;

    // Email único e obrigatório no banco
    @Column(unique = true, nullable = false)
    private String email;

    // CPF único
    @Column(unique = true)
    private String cpf;

    // Construtor vazio obrigatório para o JPA/Hibernate
    public Usuario() {
    }

    // Construtor que recebe um DTO e transforma em entidade
    public Usuario(UsuarioDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.idade = dto.idade();
        this.cpf = dto.cpf();
    }

    // Método responsável por validar os dados da entidade
    public void validar() {

        // Verifica se o nome foi preenchido
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        // Verifica se a idade é válida
        if (idade <= 0) {
            throw new IllegalArgumentException("Idade inválida");
        }

        // Verifica se o email foi preenchido
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
    }
}