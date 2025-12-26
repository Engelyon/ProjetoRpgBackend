package model;

import jakarta.persistence.*; // Importa todas as anotações JPA
import java.util.Date;       // Importa a classe Date
import java.util.List;       // Importa a interface List

/**
 * Classe * Representa uma Classe de personagem ou arquétipo em um sistema de
 * RPG. Esta entidade herda de Conteudo e inclui atributos específicos de
 * Classe, como uma lista de bônus associados. Mapeada para a tabela "classe" no
 * banco de dados.
 */
@Entity // Anotação JPA que marca esta classe como uma entidade persistente
@Table(name = "classe") // Mapeia a entidade para a tabela "classe"
@PrimaryKeyJoinColumn(name = "conteudo_id") // Define a chave primária que também é uma chave estrangeira para "conteudo"
@DiscriminatorValue("Classe") // Define o valor do discriminador para a coluna 'tipo_conteudo' na superclasse Conteudo
public class Classe extends Conteudo {

    // Lista de bônus associados a esta classe.
    // @ElementCollection: Mapeia uma coleção de objetos embutíveis ou tipos básicos.
    // @CollectionTable: Define a tabela para armazenar os elementos da coleção ("classe_bonus")
    // e a coluna de união com a entidade proprietária ("classe_id").
    @ElementCollection
    @CollectionTable(name = "classe_bonus", joinColumns = @JoinColumn(name = "classe_id"))
    private List<Bonus> bonus; // Lista de objetos Bonus (embutíveis)

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public Classe() {
        super(); // Chama o construtor da superclasse Conteudo
    }

    /**
     * Construtor completo para criar um objeto Classe.
     *
     * @param nome O título da Classe (herdado de Conteudo).
     * @param descricao A descrição da Classe (herdado de Conteudo).
     * @param autor O usuário autor da Classe (herdado de Conteudo).
     * @param sistema O sistema de RPG ao qual a Classe pertence (herdado de
     * Conteudo).
     * @param dataCriacao A data de criação da Classe (herdado de Conteudo).
     * @param dataUpdate A data da última atualização da Classe (herdado de
     * Conteudo).
     * @param tags Uma lista de tags associadas à Classe (herdado de Conteudo).
     * @param bonus Uma lista de objetos Bonus associados a esta Classe.
     */
    public Classe(String nome, String descricao, Usuario autor, Sistema sistema,
            Date dataCriacao, Date dataUpdate, List<String> tags,
            List<Bonus> bonus) {
        super(nome, descricao, autor, sistema, dataCriacao, dataUpdate, tags); // Chama o construtor da superclasse
        this.bonus = bonus; // Inicializa a lista de bônus
    }

    // --- Getters e Setters ---
    /**
     * Retorna a lista de bônus desta Classe.
     *
     * @return Uma lista de objetos Bonus.
     */
    public List<Bonus> getBonus() {
        return bonus;
    }

    /**
     * Define a lista de bônus para esta Classe.
     *
     * @param bonus A lista de objetos Bonus a ser definida.
     */
    public void setBonus(List<Bonus> bonus) {
        this.bonus = bonus;
    }
}
