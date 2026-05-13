package checkpoint.carreira.ackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// Indica que essa classe representa uma entidade do banco de dados.
@Entity

// Define o nome da tabela no banco.
@Table(name = "reserva")

// Lombok gera automaticamente os getters.
@Getter

// Lombok gera automaticamente os setters.
@Setter
public class Reserva {

    // Define a chave primária da entidade.
    @Id

    // ID gerado automaticamente pelo banco.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Data e hora de início da reserva.
    private LocalDateTime inicio;

    // Data e hora de término da reserva.
    private LocalDateTime fim;

    // Muitas reservas podem pertencer a uma sala.
    @ManyToOne(fetch = FetchType.LAZY)
    // Define a coluna de chave estrangeira no banco.
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    // Muitas reservas podem pertencer a um usuário.
    @ManyToOne
    // Define a FK do usuário.
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Salva o enum como texto no banco.
    // Exemplo: ATIVA ao invés de 0.
    @Enumerated(EnumType.STRING)
    private StatusReserva status = StatusReserva.ATIVA;

    // Construtor vazio obrigatório para o JPA/Hibernate.
    public Reserva() {}

    // Construtor usado para criar uma reserva já validada.
    public Reserva(
            LocalDateTime inicio,
            LocalDateTime fim,
            Sala sala,
            Usuario usuario
    ) {

        // Valida as datas antes de criar a reserva.
        validarDatas(inicio, fim);

        // Verifica se as datas foram enviadas.
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Datas obrigatórias");
        }

        // Verifica se o início é antes do fim.
        if (!inicio.isBefore(fim)) {
            throw new IllegalArgumentException("Início deve ser antes do fim");
        }

        // Verifica se sala e usuário existem.
        if (sala == null || usuario == null) {
            throw new IllegalArgumentException("Sala e usuário obrigatórios");
        }

        // Atribui os valores aos atributos.
        this.inicio = inicio;
        this.fim = fim;
        this.sala = sala;
        this.usuario = usuario;

        // Toda reserva começa ativa.
        this.status = StatusReserva.ATIVA;
    }

    // Método privado usado para validar datas.
    private void validarDatas(LocalDateTime inicio, LocalDateTime fim) {

        // Não permite datas nulas.
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Datas são obrigatórias");
        }

        // O início precisa ser antes do fim.
        if (!inicio.isBefore(fim)) {
            throw new IllegalArgumentException("Início deve ser antes do fim");
        }
    }

    // Verifica conflito de horário entre reservas.
    public boolean conflitaCom(Reserva outra) {

        // Se alguma reserva estiver cancelada,
        // não existe conflito.
        if (!this.estaAtiva() || !outra.estaAtiva()) {
            return false;
        }

        // Verifica sobreposição de horários.
        return this.inicio.isBefore(outra.fim) &&
                this.fim.isAfter(outra.inicio);
    }

    // Verifica se a reserva está ativa.
    public boolean estaAtiva() {
        return this.status == StatusReserva.ATIVA;
    }

    // Cancela uma reserva.
    public void cancelar() {

        // Evita cancelar duas vezes.
        if (this.status == StatusReserva.CANCELADA) {
            throw new IllegalStateException("Reserva já está cancelada");
        }

        // Altera o status para CANCELADA.
        this.status = StatusReserva.CANCELADA;
    }
}