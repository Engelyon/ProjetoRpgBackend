package model;

import jakarta.persistence.*;       // Importa todas as anotações JPA
import java.util.Date;              // Importa a classe Date
import java.util.List;              // Importa a interface List
import util.BonusListConverter;     // Importa o converter de BonusListConverter

/**
 * SubClasse
 * * Representa uma subclasse (especialização de Classe) em um sistema de RPG.
 * Esta entidade herda de Conteudo e inclui atributos específicos de SubClasse,
 * como a classe pai (referência textual) e uma lista de bônus.
 * Mapeada para a tabela "subclasse" no banco de dados.
 */
@Entity // Marca esta classe como uma entidade persistente
@Table(name = "subclasse") // Mapeia a entidade para a tabela "subclasse"
@PrimaryKeyJoinColumn(name = "conteudo_id") // Define a chave primária que também é uma chave estrangeira para "conteudo"
@DiscriminatorValue("Subclasse") // Define o valor do discriminador para a coluna 'tipo_conteudo' na superclasse Conteudo
public class SubClasse extends Conteudo {

    // Nome da classe pai desta subclasse. Armazenado como String, sem FK no banco.
    @Column(name = "classe_pai") 
    private String classePai;

    // Lista de bônus associados a esta subclasse. Convertida para JSON de objetos Bonus.
    @Convert(converter = BonusListConverter.class) // Usa BonusListConverter para serialização/desserialização JSON
    @Column(name = "bonus_subclasse", columnDefinition = "json") // Define a coluna "bonus_subclasse" como tipo JSON no banco
    private List<Bonus> bonus; // Lista de objetos Bonus

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public SubClasse() {
        super(); // Chama o construtor da superclasse Conteudo
    }

    /**
     * Construtor completo para criar um objeto SubClasse.
     * @param titulo O título da SubClasse (herdado de Conteudo).
     * @param descricao A descrição da SubClasse (herdado de Conteudo).
     * @param autor O usuário autor da SubClasse (herdado de Conteudo).
     * @param sistema O sistema de RPG ao qual a SubClasse pertence (herdado de Conteudo).
     * @param dataCriacao A data de criação da SubClasse (herdado de Conteudo).
     * @param dataUpdate A data da última atualização da SubClasse (herdado de Conteudo).
     * @param tags Uma lista de tags associadas à SubClasse (herdado de Conteudo).
     * @param classePai O nome da classe pai (String).
     * @param bonus Uma lista de objetos Bonus associados a esta SubClasse.
     */
    public SubClasse(String titulo, String descricao, Usuario autor, Sistema sistema,
                     Date dataCriacao, Date dataUpdate, List<String> tags,
                     String classePai, List<Bonus> bonus) {
        super(titulo, descricao, autor, sistema, dataCriacao, dataUpdate, tags); // Chama o construtor da superclasse
        this.classePai = classePai; // Inicializa o nome da classe pai
        this.bonus = bonus;       // Inicializa a lista de bônus
    }

    // --- Getters e Setters ---

    public String getClassePai() { return classePai; }
    public void setClassePai(String classePai) { this.classePai = classePai; }

    public List<Bonus> getBonus() { return bonus; }
    public void setBonus(List<Bonus> bonus) { this.bonus = bonus; }
}