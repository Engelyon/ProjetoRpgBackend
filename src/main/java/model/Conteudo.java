package model;

import jakarta.persistence.*; 
import java.util.Date;       
import java.util.List;       
import util.TagListConverter; 

/**
 * Conteudo
 * Classe base que representa um conteúdo genérico em um sistema de RPG.
 * Entidades como Item, Magia, Classe, etc., herdam desta classe.
 * Utiliza estratégia de herança JOINED para mapeamento no banco de dados.
 */
@Entity 
@Table(name = "conteudo") 
@Inheritance(strategy = InheritanceType.JOINED) 
@DiscriminatorColumn(name = "tipo_conteudo", discriminatorType = DiscriminatorType.STRING) 
public class Conteudo {

    // Identificador único do conteúdo. Gerado automaticamente.
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 

    // Título do conteúdo
    private String titulo;
    // Descrição detalhada do conteúdo
    private String descricao;

    // Autor do conteúdo. Relação muitos-para-um com a entidade Usuario.
    @ManyToOne 
    @JoinColumn(name = "autor_usuario_id") 
    private Usuario autor;

    // Sistema de RPG ao qual o conteúdo pertence. Relação muitos-para-um com a entidade Sistema.
    @ManyToOne 
    @JoinColumn(name = "sistema_id") 
    private Sistema sistema;

    // Data de criação do conteúdo. Mapeado para coluna 'data_criacao'.
    @Column(name = "data_criacao") 
    private Date dataCriacao;

    // Data da última atualização do conteúdo. Mapeado para coluna 'data_atualizacao'.
    @Column(name = "data_atualizacao") 
    private Date dataUpdate;

    // Lista de tags associadas ao conteúdo. Convertida para JSON para armazenamento.
    @Convert(converter = TagListConverter.class) 
    @Column(name = "tags", columnDefinition = "json") 
    private List<String> tags;

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public Conteudo() {
    }

    /**
     * Construtor completo para criar um objeto Conteudo.
     * @param titulo O título do conteúdo.
     * @param descricao A descrição do conteúdo.
     * @param autor O usuário autor do conteúdo.
     * @param sistema O sistema de RPG ao qual o conteúdo pertence.
     * @param dataCriacao A data de criação do conteúdo.
     * @param dataUpdate A data da última atualização do conteúdo.
     * @param tags Uma lista de tags associadas ao conteúdo.
     */
    public Conteudo(String titulo, String descricao, Usuario autor, Sistema sistema, Date dataCriacao, Date dataUpdate, List<String> tags) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.autor = autor;
        this.sistema = sistema;
        this.dataCriacao = dataCriacao;
        this.dataUpdate = dataUpdate;
        this.tags = tags;
    }

    // --- Getters e Setters ---

    public Long getId() { 
        return id;
    }

    public void setId(Long id) { 
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataUpdate() {
        return dataUpdate;
    }

    public void setDataUpdate(Date dataUpdate) {
        this.dataUpdate = dataUpdate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Retorna o tipo de conteúdo como uma String, baseando-se no nome da classe concreta.
     * Este valor corresponde ao DiscriminatorValue usado para a herança JOINED.
     * @return Uma String indicando o tipo de conteúdo (ex: "Item", "Magia", "Classe").
     */
    public String getTipoConteudo() {
        return this.getClass().getSimpleName(); 
    }
}