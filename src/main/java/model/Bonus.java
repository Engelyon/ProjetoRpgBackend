package model;

import jakarta.persistence.Embeddable;

/**
 * Bonus
 * * Representa um bônus ou característica que pode ser associado a itens, classes, raças, etc.
 * Esta classe é um tipo embutível (Embeddable) para ser armazenada em colunas JSON
 * em outras entidades (ex: Item.item_bonus, Classe.bonus_classe).
 */
@Embeddable // Anotação JPA que marca esta classe como embutível
public class Bonus {

    // Tipo do bônus (ex: "Atributo", "Habilidade", "Sentido")
    private String tipo;
    // Nome específico do bônus (ex: "Força", "Ataque Extra", "Visão no Escuro")
    private String nome;
    // Valor do bônus (ex: "+2", "18m", "Imunidade"). Armazenado como String para flexibilidade.
    private String valor; 
    // Descrição detalhada do bônus (opcional)
    private String descricao;
    // Atributo principal afetado pelo bônus (ex: "Força", "Destreza", "Constituição")
    private String atributo; 

    /**
     * Construtor padrão. Necessário para JPA e serialização JSON.
     */
    public Bonus() {
    }

    /**
     * Construtor completo para criar um objeto Bonus.
     * @param tipo O tipo do bônus (ex: "Atributo").
     * @param nome O nome específico do bônus (ex: "Força").
     * @param valor O valor do bônus (ex: "+2").
     * @param descricao Descrição opcional do bônus.
     * @param atributo O atributo principal afetado pelo bônus.
     */
    public Bonus(String tipo, String nome, String valor, String descricao, String atributo) {
        this.tipo = tipo;
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
        this.atributo = atributo;
    }
    
    /**
     * Construtor simplificado para criar um objeto Bonus com tipo, nome e valor.
     * Descrição e atributo são inicializados como null.
     * @param tipo O tipo do bônus.
     * @param nome O nome do bônus.
     * @param valor O valor do bônus.
     */
    public Bonus(String tipo, String nome, String valor) {
        this(tipo, nome, valor, null, null); // Chama o construtor completo
    }

    // --- Getters e Setters para todos os campos ---
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getAtributo() { return atributo; }
    public void setAtributo(String atributo) { this.atributo = atributo; }

    /**
     * Retorna uma representação em String do objeto Bonus.
     * Formato: "Atributo: Tipo Nome (Valor) - Descricao"
     * Partes opcionais são incluídas apenas se existirem.
     * @return Uma String formatada representando o bônus.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Adiciona o atributo se não for nulo ou em branco
        if (atributo != null && !atributo.isBlank()) {
            sb.append(atributo).append(": ");
        }
        // Adiciona tipo e nome
        sb.append(tipo).append(" ").append(nome);

        // Adiciona o valor se não for nulo ou em branco
        if (valor != null && !valor.isBlank()) {
            sb.append(" (").append(valor).append(")");
        }

        // Adiciona a descrição se não for nula ou em branco
        if (descricao != null && !descricao.isBlank()) {
            sb.append(" - ").append(descricao);
        }

        return sb.toString();
    }
}