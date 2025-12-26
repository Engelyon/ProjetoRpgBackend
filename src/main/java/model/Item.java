package model;

import jakarta.persistence.*; // Importa todas as anotações JPA
import java.util.Date;       // Importa a classe Date
import java.util.List;       // Importa a interface List

/**
 * Item
 * * Representa um item (equipamento, consumível, etc.) em um sistema de RPG.
 * Esta entidade herda de Conteudo e inclui atributos específicos de Item,
 * como raridade, tipo de item e uma lista de bônus.
 * Mapeada para a tabela "item" no banco de dados.
 */
@Entity // Marca esta classe como uma entidade persistente
@Table(name = "item") // Mapeia a entidade para a tabela "item"
@PrimaryKeyJoinColumn(name = "conteudo_id") // Define a chave primária que também é uma chave estrangeira para "conteudo"
@DiscriminatorValue("Item") // Define o valor do discriminador para a coluna 'tipo_conteudo' na superclasse Conteudo
public class Item extends Conteudo {

    // Raridade do item (ex: "Comum", "Lendário"). Mapeado para coluna 'raridade_item'.
    @Column(name = "raridade_item") 
    private String raridade;
    // Tipo específico do item (ex: "Arma", "Armadura", "Poção"). Mapeado para coluna 'tipo_item'.
    @Column(name = "tipo_item") 
    private String tipo;

    // Lista de bônus associados a este item.
    // @ElementCollection: Mapeia uma coleção de objetos embutíveis (Bonus).
    // @CollectionTable: Define a tabela para armazenar os elementos da coleção ("item_bonus")
    // e a coluna de união com a entidade proprietária ("item_id").
    @ElementCollection
    @CollectionTable(name = "item_bonus", joinColumns = @JoinColumn(name = "item_id"))
    private List<Bonus> bonus; // Lista de objetos Bonus (embutíveis)

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public Item() {
        super(); // Chama o construtor da superclasse Conteudo
    }

    /**
     * Construtor completo para criar um objeto Item.
     * @param titulo O título do item (herdado de Conteudo).
     * @param descricao A descrição do item (herdado de Conteudo).
     * @param autor O usuário autor do item (herdado de Conteudo).
     * @param sistema O sistema de RPG ao qual o item pertence (herdado de Conteudo).
     * @param dataCriacao A data de criação do item (herdado de Conteudo).
     * @param dataUpdate A data da última atualização do item (herdado de Conteudo).
     * @param tags Uma lista de tags associadas ao item (herdado de Conteudo).
     * @param raridade A raridade do item (ex: "Lendário").
     * @param tipo O tipo específico do item (ex: "Arma").
     * @param bonus Uma lista de objetos Bonus associados a este item.
     */
    public Item(String titulo, String descricao, Usuario autor, Sistema sistema,
                Date dataCriacao, Date dataUpdate,
                List<String> tags, String raridade, String tipo, List<Bonus> bonus) {
        super(titulo, descricao, autor, sistema, dataCriacao, dataUpdate, tags); // Chama o construtor da superclasse
        this.raridade = raridade; // Inicializa a raridade
        this.tipo = tipo;         // Inicializa o tipo
        this.bonus = bonus;       // Inicializa a lista de bônus
    }

    // --- Getters e Setters ---

    public String getRaridade() {
        return raridade;
    }

    public void setRaridade(String raridade) {
        this.raridade = raridade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Bonus> getBonus() {
        return bonus;
    }

    public void setBonus(List<Bonus> bonus) {
        this.bonus = bonus;
    }
}