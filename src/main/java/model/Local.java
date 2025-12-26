package model;

import jakarta.persistence.*; // Importa todas as anotações JPA
import java.util.Date;       // Importa a classe Date
import java.util.List;       // Importa a interface List
import util.NpcListConverter; // Importa o converter de NpcListConverter

/**
 * Local
 * * Representa um local (cidade, floresta, caverna, etc.) em um sistema de RPG.
 * Esta entidade herda de Conteudo e inclui atributos específicos de Local,
 * como tipo de local, clima, população e uma lista de NPCs.
 * Mapeada para a tabela "local" no banco de dados.
 */
@Entity // Marca esta classe como uma entidade persistente
@Table(name = "local") // Mapeia a entidade para a tabela "local"
@PrimaryKeyJoinColumn(name = "conteudo_id") // Define a chave primária que também é uma chave estrangeira para "conteudo"
@DiscriminatorValue("Local") // Define o valor do discriminador para a coluna 'tipo_conteudo' na superclasse Conteudo
public class Local extends Conteudo {

    // Tipo do local (ex: "Cidade", "Floresta"). Mapeado para coluna 'tipo_local'.
    @Column(name = "tipo_local") 
    private String tipo;

    // Nome do Local Pai (se existir). Armazenado como String, sem FK no banco. Mapeado para coluna 'local_pai'.
    @Column(name = "local_pai") 
    private String localPai;

    // Clima predominante do local. Mapeado para coluna 'clima_local'.
    @Column(name = "clima_local") 
    private String clima;

    // População do local (ex: "50 mil", "1000 habitantes"). Armazenado como String. Mapeado para coluna 'populacao_local'.
    @Column(name = "populacao_local") 
    private String populacao;

    // Lista de NPCs presentes no local. Convertida para JSON de objetos Npc.
    @Convert(converter = NpcListConverter.class) // Usa NpcListConverter para serialização/desserialização JSON
    @Column(name = "npcs_presentes_local", columnDefinition = "json") // Define a coluna "npcs_presentes_local" como tipo JSON no banco
    private List<Npc> npcsPresentes; // Lista de objetos Npc

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public Local() {
        super(); // Chama o construtor da superclasse Conteudo
    }

    /**
     * Construtor completo para criar um objeto Local.
     * @param titulo O título do local (herdado de Conteudo).
     * @param descricao A descrição do local (herdado de Conteudo).
     * @param autor O usuário autor do local (herdado de Conteudo).
     * @param sistema O sistema de RPG ao qual o local pertence (herdado de Conteudo).
     * @param dataCriacao A data de criação do local (herdado de Conteudo).
     * @param dataUpdate A data da última atualização do local (herdado de Conteudo).
     * @param tags Uma lista de tags associadas ao local (herdado de Conteudo).
     * @param tipo O tipo do local (ex: "Cidade").
     * @param localPai O nome do local pai (String).
     * @param clima O clima do local.
     * @param populacao A população do local (String).
     * @param npcsPresentes Uma lista de objetos Npc presentes no local.
     */
    public Local(String titulo, String descricao, Usuario autor, Sistema sistema,
                 Date dataCriacao, Date dataUpdate, List<String> tags,
                 String tipo, String localPai, String clima, String populacao, List<Npc> npcsPresentes) {
        super(titulo, descricao, autor, sistema, dataCriacao, dataUpdate, tags); // Chama o construtor da superclasse
        this.tipo = tipo; // Inicializa o tipo
        this.localPai = localPai; // Inicializa o nome do local pai
        this.clima = clima; // Inicializa o clima
        this.populacao = populacao; // Inicializa a população
        this.npcsPresentes = npcsPresentes; // Inicializa a lista de NPCs
    }

    // --- Getters e Setters ---

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocalPai() {
        return localPai;
    }

    public void setLocalPai(String localPai) {
        this.localPai = localPai;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getPopulacao() {
        return populacao;
    }

    public void setPopulacao(String populacao) {
        this.populacao = populacao;
    }

    public List<Npc> getNpcsPresentes() {
        return npcsPresentes;
    }

    public void setNpcsPresentes(List<Npc> npcsPresentes) {
        this.npcsPresentes = npcsPresentes;
    }
}