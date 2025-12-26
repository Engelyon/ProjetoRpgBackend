package model;

import jakarta.persistence.*; // Importa todas as anotações JPA
import java.util.Date;       // Importa a classe Date
import java.util.List;       // Importa a interface List

/**
 * Raca
 * * Representa uma raça ou espécie em um sistema de RPG.
 * Esta entidade herda de Conteudo e inclui atributos específicos de Raça,
 * como uma lista de bônus raciais.
 * Mapeada para a tabela "raca" no banco de dados.
 */
@Entity // Marca esta classe como uma entidade persistente
@Table(name = "raca") // Mapeia a entidade para a tabela "raca"
@PrimaryKeyJoinColumn(name = "conteudo_id") // Define a chave primária que também é uma chave estrangeira para "conteudo"
@DiscriminatorValue("Raça") // Define o valor do discriminador para a coluna 'tipo_conteudo' na superclasse Conteudo
public class Raca extends Conteudo {

    // Lista de bônus associados a esta raça.
    // @ElementCollection: Mapeia uma coleção de objetos embutíveis ou tipos básicos.
    // @CollectionTable: Define a tabela para armazenar os elementos da coleção ("raca_bonus")
    // e a coluna de união com a entidade proprietária ("raca_id").
    @ElementCollection
    @CollectionTable(name = "raca_bonus", joinColumns = @JoinColumn(name = "raca_id"))
    private List<Bonus> bonus; // Lista de objetos Bonus (embutíveis)

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public Raca() {
        super(); // Chama o construtor da superclasse Conteudo
    }

    /**
     * Construtor completo para criar um objeto Raca.
     * @param titulo O título da Raça (herdado de Conteudo).
     * @param descricao A descrição da Raça (herdado de Conteudo).
     * @param autor O usuário autor da Raça (herdado de Conteudo).
     * @param sistema O sistema de RPG ao qual a Raça pertence (herdado de Conteudo).
     * @param dataCriacao A data de criação da Raça (herdado de Conteudo).
     * @param dataUpdate A data da última atualização da Raça (herdado de Conteudo).
     * @param tags Uma lista de tags associadas à Raça (herdado de Conteudo).
     * @param bonus Uma lista de objetos Bonus associados a esta Raça.
     */
    public Raca(String titulo, String descricao, Usuario autor, Sistema sistema,
                Date dataCriacao, Date dataUpdate, List<String> tags,
                List<Bonus> bonus) {
        super(titulo, descricao, autor, sistema, dataCriacao, dataUpdate, tags); // Chama o construtor da superclasse
        this.bonus = bonus; // Inicializa a lista de bônus
    }

    // --- Getters e Setters ---

    public List<Bonus> getBonus() {
        return bonus;
    }

    public void setBonus(List<Bonus> bonus) {
        this.bonus = bonus;
    }
}