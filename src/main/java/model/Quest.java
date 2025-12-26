package model;

import jakarta.persistence.*; // Importa todas as anotações JPA
import java.util.Date;       // Importa a classe Date
import java.util.List;       // Importa a interface List

/**
 * Quest
 * * Representa uma missão ou tarefa em um sistema de RPG.
 * Esta entidade herda de Conteudo e inclui atributos específicos de Quest,
 * como objetivo, recompensa e o cliente da missão.
 * Mapeada para a tabela "quest" no banco de dados.
 */
@Entity // Marca esta classe como uma entidade persistente
@Table(name = "quest") // Mapeia a entidade para a tabela "quest"
@PrimaryKeyJoinColumn(name = "conteudo_id") // Define a chave primária que também é uma chave estrangeira para "conteudo"
@DiscriminatorValue("Quest") // Define o valor do discriminador para a coluna 'tipo_conteudo' na superclasse Conteudo
public class Quest extends Conteudo {

    // Objetivo principal da missão. Mapeado para coluna 'objetivo_quest'.
    @Column(name = "objetivo_quest") 
    private String objetivo;

    // Recompensa oferecida pela conclusão da missão. Mapeado para coluna 'recompensa_quest'.
    @Column(name = "recompensa_quest") 
    private String recompensa;

    // Nome ou descrição do cliente que oferece a missão. Mapeado para coluna 'cliente_quest'.
    @Column(name = "cliente_quest") 
    private String cliente;

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public Quest() {
        super(); // Chama o construtor da superclasse Conteudo
    }

    /**
     * Construtor completo para criar um objeto Quest.
     * @param titulo O título da missão (herdado de Conteudo).
     * @param descricao A descrição da missão (herdado de Conteudo).
     * @param autor O usuário autor da missão (herdado de Conteudo).
     * @param sistema O sistema de RPG ao qual a missão pertence (herdado de Conteudo).
     * @param dataCriacao A data de criação da missão (herdado de Conteudo).
     * @param dataUpdate A data da última atualização da missão (herdado de Conteudo).
     * @param tags Uma lista de tags associadas à missão (herdado de Conteudo).
     * @param objetivo O objetivo principal da missão.
     * @param recompensa A recompensa oferecida pela missão.
     * @param cliente O cliente que oferece a missão.
     */
    public Quest(String titulo, String descricao, Usuario autor, Sistema sistema,
                 java.util.Date dataCriacao, java.util.Date dataUpdate, java.util.List<String> tags,
                 String objetivo, String recompensa, String cliente) {
        super(titulo, descricao, autor, sistema, dataCriacao, dataUpdate, tags); // Chama o construtor da superclasse
        this.objetivo = objetivo;     // Inicializa o objetivo
        this.recompensa = recompensa; // Inicializa a recompensa
        this.cliente = cliente;       // Inicializa o cliente
    }

    // --- Getters e Setters ---

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}