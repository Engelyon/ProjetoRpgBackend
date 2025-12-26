package model;

import java.util.Objects; // Importa a classe Objects para métodos equals/hashCode

/**
 * Npc
 * * Representa um Personagem Não Jogável (NPC) associado a um Local.
 * Esta classe é um POJO (Plain Old Java Object) que armazena nome e descrição
 * de um NPC para ser serializado/desserializado em formato JSON.
 * Não é uma entidade JPA separada.
 */
public class Npc {
    // Nome do NPC
    private String nome;
    // Descrição do NPC (opcional)
    private String descricao;

    /**
     * Construtor padrão. Necessário para serialização/desserialização JSON (Jackson).
     */
    public Npc() {}

    /**
     * Construtor para criar um objeto Npc.
     * @param nome O nome do NPC.
     * @param descricao A descrição do NPC (pode ser null).
     */
    public Npc(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // --- Getters e Setters ---
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    /**
     * Retorna uma representação em String do objeto Npc.
     * Formato: "Nome (Descrição)" ou apenas "Nome" se a descrição for vazia.
     * @return Uma String formatada representando o NPC.
     */
    @Override
    public String toString() {
        return nome + (descricao != null && !descricao.isBlank() ? " (" + descricao + ")" : "");
    }

    /**
     * Compara este Npc com outro objeto para verificar igualdade.
     * Dois Npcs são considerados iguais se tiverem o mesmo nome e descrição.
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Npc npc = (Npc) o;
        return Objects.equals(nome, npc.nome) && Objects.equals(descricao, npc.descricao);
    }

    /**
     * Gera um código hash para este Npc.
     * É usado em conjunto com o método equals para coleções.
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, descricao);
    }
    
    /**
     * Tenta converter uma String (do banco de dados) no formato "Nome (Descrição)"
     * ou "Nome" de volta para um objeto Npc.
     * @param npcString A string a ser parseada.
     * @return Um objeto Npc correspondente, ou null se a string for nula ou em branco.
     */
    public static Npc fromString(String npcString) {
        if (npcString == null || npcString.isBlank()) {
            return null;
        }
        // Tenta parsear o formato "Nome (Descrição)"
        int startIndex = npcString.indexOf('(');
        int endIndex = npcString.lastIndexOf(')');

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            String nome = npcString.substring(0, startIndex).trim();
            String descricao = npcString.substring(startIndex + 1, endIndex).trim();
            return new Npc(nome, descricao);
        } else {
            // Se não encontrar o formato (ou for só o nome), assume que a string inteira é o nome
            return new Npc(npcString.trim(), null);
        }
    }
}