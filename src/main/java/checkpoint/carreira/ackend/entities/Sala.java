package checkpoint.carreira.ackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Define que essa classe é uma entidade do banco.
@Entity
// Define o nome da tabela no banco de dados.
@Table(name = "sala")
// Lombok gera automaticamente os getters.
@Getter
// Lombok gera automaticamente os setters.
@Setter
public class Sala {

    // Chave primária da tabela.
    @Id
    // ID gerado automaticamente pelo banco.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome da sala.
    // Exemplo: "Sala 101"
    private String nome;

    // Quantidade máxima de pessoas na sala.
    private int capacidade;

    // Define se a sala está disponível para uso.
    // Toda sala começa ativa por padrão.
    private boolean ativa = true;

    // Construtor vazio obrigatório para o JPA/Hibernate.
    public Sala() {}

    // Construtor usado para criar uma sala validada.
    public Sala(String nome, int capacidade) {

        // Verifica se o nome foi preenchido.
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        // Verifica se a capacidade é válida.
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }

        // Atribui os valores aos atributos.
        this.nome = nome;
        this.capacidade = capacidade;

        // Toda sala começa ativa.
        this.ativa = true;
    }

    // Método usado para validar regras da entidade.
    public void validar() {

        // Verifica se a capacidade é válida.
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade inválida");
        }
    }

    // Desativa a sala.
    // Pode ser útil para impedir reservas temporariamente.
    public void desativar() {
        this.ativa = false;
    }

    // Reativa a sala.
    public void ativar() {
        this.ativa = true;
    }
}