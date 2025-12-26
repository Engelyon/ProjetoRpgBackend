package model;

import jakarta.persistence.*;       // Importa todas as anotações JPA
import java.util.Date;              // Importa a classe Date
import java.util.List;              // Importa a interface List
import util.BonusListConverter;     // Importa o converter de BonusListConverter

/**
 * SubRaca
 * * Representa uma sub-raça (especialização de Raça) em um sistema de RPG.
 * Esta entidade herda de Conteudo e inclui atributos específicos de SubRaca,
 * como a raça pai (referência textual) e uma lista de bônus.
 * Mapeada para a tabela "subraca" no banco de dados.
 */
@Entity // Marca esta classe como uma entidade persistente
@Table(name = "subraca") // Mapeia a entidade para a tabela "subraca"
@PrimaryKeyJoinColumn(name = "conteudo_id") // Define a chave primária que também é uma chave estrangeira para "conteudo"
@DiscriminatorValue("Subraça") // Define o valor do discriminador para a coluna 'tipo_conteudo' na superclasse Conteudo
public class SubRaca extends Conteudo {

    // Nome da raça pai desta sub-raça. Armazenado como String, sem FK no banco.
    @Column(name = "raca_pai") 
    private String racaPai;

    // Lista de bônus associados a esta sub-raça. Convertida para JSON de objetos Bonus.
    @Convert(converter = BonusListConverter.class) // Usa BonusListConverter para serialização/desserialização JSON
    @Column(name = "bonus_subcraca", columnDefinition = "json") // Define a coluna "bonus_subcraca" como tipo JSON no banco
    private List<Bonus> bonus; // Lista de objetos Bonus

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public SubRaca() {
        super(); // Chama o construtor da superclasse Conteudo
    }

    /**
     * Construtor completo para criar um objeto SubRaca.
     * @param titulo O título da SubRaca (herdado de Conteudo).
     * @param descricao A descrição da SubRaca (herdado de Conteudo).
     * @param autor O usuário autor da SubRaca (herdado de Conteudo).
     * @param sistema O sistema de RPG ao qual a SubRaca pertence (herdado de Conteudo).
     * @param dataCriacao A data de criação da SubRaca (herdado de Conteudo).
     * @param dataUpdate A data da última atualização da SubRaca (herdado de Conteudo).
     * @param tags Uma lista de tags associadas à SubRaca (herdado de Conteudo).
     * @param racaPai O nome da raça pai (String).
     * @param bonus Uma lista de objetos Bonus associados a esta SubRaca.
     */
    public SubRaca(String titulo, String descricao, Usuario autor, Sistema sistema,
                   Date dataCriacao, Date dataUpdate, List<String> tags,
                   String racaPai, List<Bonus> bonus) {
        super(titulo, descricao, autor, sistema, dataCriacao, dataUpdate, tags); // Chama o construtor da superclasse
        this.racaPai = racaPai; // Inicializa o nome da raça pai
        this.bonus = bonus;   // Inicializa a lista de bônus
    }

    // --- Getters e Setters ---

    public String getRacaPai() { return racaPai; }
    public void setRacaPai(String racaPai) { this.racaPai = racaPai; }

    public List<Bonus> getBonus() { return bonus; }
    public void setBonus(List<Bonus> bonus) { this.bonus = bonus; }
}