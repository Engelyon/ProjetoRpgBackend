package model;

import jakarta.persistence.*; // Importa todas as anotações JPA
import java.util.Date;       // Importa a classe Date
import java.util.List;       // Importa a interface List

/**
 * Magia
 * * Representa uma magia ou feitiço em um sistema de RPG.
 * Esta entidade herda de Conteudo e inclui atributos específicos de Magia,
 * como nível, escola, alcance, área e duração.
 * Mapeada para a tabela "magia" no banco de dados.
 */
@Entity // Marca esta classe como uma entidade persistente
@Table(name = "magia") // Mapeia a entidade para a tabela "magia"
@PrimaryKeyJoinColumn(name = "conteudo_id") // Define a chave primária que também é uma chave estrangeira para "conteudo"
@DiscriminatorValue("Magia") // Define o valor do discriminador para a coluna 'tipo_conteudo' na superclasse Conteudo
public class Magia extends Conteudo {

    // Nível da magia (ex: 1, 3, 9). Mapeado para coluna 'nivel_magia'.
    @Column(name = "nivel_magia") 
    private Integer nivel; // Usar Integer para permitir valores nulos

    // Escola da magia (ex: "Evocação", "Ilusão"). Mapeado para coluna 'escola_magia'.
    @Column(name = "escola_magia") 
    private String escola;

    // Alcance da magia (ex: "30 metros", "Toque"). Mapeado para coluna 'alcance_magia'.
    @Column(name = "alcance_magia") 
    private String alcance;

    // Área de efeito da magia (ex: "Esfera 6m", "Cubo 9m"). Mapeado para coluna 'area_magia'.
    @Column(name = "area_magia") 
    private String area;

    // Duração da magia (ex: "Instantânea", "1 minuto", "Concentração"). Mapeado para coluna 'duracao_magia'.
    @Column(name = "duracao_magia") 
    private String duracao;

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public Magia() {
        super(); // Chama o construtor da superclasse Conteudo
    }

    /**
     * Construtor completo para criar um objeto Magia.
     * @param titulo O título da magia (herdado de Conteudo).
     * @param descricao A descrição da magia (herdado de Conteudo).
     * @param autor O usuário autor da magia (herdado de Conteudo).
     * @param sistema O sistema de RPG ao qual a magia pertence (herdado de Conteudo).
     * @param dataCriacao A data de criação da magia (herdado de Conteudo).
     * @param dataUpdate A data da última atualização da magia (herdado de Conteudo).
     * @param tags Uma lista de tags associadas à magia (herdado de Conteudo).
     * @param nivel O nível da magia.
     * @param escola A escola da magia.
     * @param alcance O alcance da magia.
     * @param area A área de efeito da magia.
     * @param duracao A duração da magia.
     */
    public Magia(String titulo, String descricao, Usuario autor, Sistema sistema,
                 Date dataCriacao, Date dataUpdate, List<String> tags,
                 Integer nivel, String escola, String alcance, String area, String duracao) {
        super(titulo, descricao, autor, sistema, dataCriacao, dataUpdate, tags); // Chama o construtor da superclasse
        this.nivel = nivel;       // Inicializa o nível
        this.escola = escola;     // Inicializa a escola
        this.alcance = alcance;   // Inicializa o alcance
        this.area = area;         // Inicializa a área
        this.duracao = duracao;   // Inicializa a duração
    }

    // --- Getters e Setters ---

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }
}