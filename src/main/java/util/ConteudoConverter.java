package util;

import dao.*; // Importa todos os DAOs necessários (ItemDao, MagiaDao, etc.)
import model.*; // Importa todas as entidades de modelo (Conteudo, Item, Magia, etc.)
import jakarta.persistence.EntityManager; // Importa EntityManager do JPA
import java.util.ArrayList; // Importa ArrayList
import java.util.Arrays; // Importa Arrays para manipulação de arrays
import java.util.List; // Importa a interface List
import java.util.stream.Collectors; // Importa Collectors para operações de Stream

/**
 * ConteudoConverter
 * * Classe utilitária para auxiliar na criação, persistência e formatação de entidades Conteudo.
 * Centraliza lógicas de conversão e obtenção de DAOs para diferentes tipos de Conteudo.
 */
public class ConteudoConverter { // Nome da classe é ConteudoConverter

    /**
     * Cria uma nova instância da subclasse concreta de Conteudo
     * baseada na string do tipo fornecida.
     * @param tipo A string que representa o tipo de conteúdo (ex: "Item", "Magia", "Local").
     * @return Uma nova instância da subclasse de Conteudo correspondente.
     * @throws IllegalArgumentException se o tipo de conteúdo fornecido for desconhecido
     * (não mapeado a uma subclasse existente).
     */
    public static Conteudo criarNovaInstanciaConteudo(String tipo) {
        switch (tipo.toLowerCase()) {
            case "item": return new Item();
            case "magia": return new Magia();
            case "classe": return new Classe();
            case "raca": return new Raca();
            case "quest": return new Quest();
            case "local": return new Local();
            case "subclasse": return new SubClasse();
            case "subraca": return new SubRaca();
            default: throw new IllegalArgumentException("Tipo de conteúdo desconhecido: " + tipo);
        }
    }

    /**
     * Retorna a instância do DAO (Data Access Object) correta
     * para a entidade Conteudo fornecida, baseando-se em seu tipo concreto.
     * @param em O EntityManager a ser utilizado pelo DAO para operações de persistência.
     * @param conteudo A instância de Conteudo para a qual se deseja obter o DAO.
     * @return Uma instância de GenericDao<?> para o tipo específico de Conteudo.
     * @throws IllegalArgumentException se um DAO não for encontrado para o tipo de conteúdo.
     */
    @SuppressWarnings("unchecked") // Suprime avisos de unchecked cast ao retornar GenericDao<?>
    public static GenericDao<?> getDaoForConteudo(EntityManager em, Conteudo conteudo) {
        // Verifica o tipo de Conteudo e retorna o DAO específico correspondente
        if (conteudo instanceof Item) return new ItemDao(em);
        if (conteudo instanceof Magia) return new MagiaDao(em); 
        if (conteudo instanceof Classe) return new ClasseDao(em); 
        if (conteudo instanceof Raca) return new RacaDao(em);
        if (conteudo instanceof Quest) return new QuestDao(em);
        if (conteudo instanceof Local) return new LocalDao(em);
        if (conteudo instanceof SubClasse) return new SubClasseDao(em);
        if (conteudo instanceof SubRaca) return new SubRacaDao(em);
        
        // Lança uma exceção se nenhum DAO correspondente for encontrado para o tipo de Conteudo
        throw new IllegalArgumentException("DAO não encontrada para o tipo de conteúdo: " + conteudo.getClass().getSimpleName());
    }

    /**
     * Formata uma lista de objetos {@link Bonus} para uma String no formato JSON,
     * adequada para exibição em um {@link JTextArea} ou armazenamento em banco de dados.
     * Reutiliza o {@link util.BonusListConverter} internamente para a serialização.
     * @param bonusList A lista de Bonus a ser formatada.
     * @return Uma String JSON representando a lista de bônus, ou uma String vazia se a lista for nula ou vazia.
     */
    public static String formatBonusListForTextArea(List<Bonus> bonusList) {
        if (bonusList == null || bonusList.isEmpty()) {
            return "";
        }
        // Utiliza BonusListConverter para converter a lista de Bonus para a String JSON
        return new util.BonusListConverter().convertToDatabaseColumn(bonusList);
    }

    /**
     * Converte uma String (geralmente de um {@link JTextArea}) no formato JSON de volta
     * para uma {@link List} de {@link Bonus} objetos.
     * Reutiliza o {@link util.BonusListConverter} internamente para a desserialização.
     * @param jsonText A String JSON a ser convertida.
     * @return Uma List de Bonus, ou null se a String de entrada for nula ou em branco.
     * @throws RuntimeException se o formato JSON for inválido (erro de parsing).
     */
    public static List<Bonus> parseBonusListFromTextArea(String jsonText) {
        if (jsonText == null || jsonText.isBlank()) {
            return null; // Retorna null se a string de entrada for vazia ou em branco
        }
        // Utiliza BonusListConverter para converter a String JSON para a lista de Bonus
        return new util.BonusListConverter().convertToEntityAttribute(jsonText);
    }
    
    /**
     * Formata uma lista de Strings (usada para representar NPCs) em uma única String
     * separada por vírgulas, adequada para exibição em um {@link JTextArea}.
     * @param npcs A lista de Strings de NPCs.
     * @return Uma String contendo os NPCs separados por vírgulas, ou uma String vazia se a lista for nula ou vazia.
     */
    public static String formatNPCListForTextArea(List<String> npcs) {
        if (npcs == null || npcs.isEmpty()) {
            return "";
        }
        return String.join(", ", npcs);
    }
    
    /**
     * Converte uma String de texto (geralmente de um {@link JTextArea}),
     * onde os NPCs estão separados por vírgulas, de volta para uma {@link List} de Strings.
     * @param npcsText A String de texto contendo os NPCs separados por vírgulas.
     * @return Uma List de Strings, com cada NPC como um elemento da lista. Retorna uma lista vazia se a entrada for nula ou em branco.
     */
    public static List<String> parseNPCListFromTextArea(String npcsText) {
        if (npcsText == null || npcsText.isBlank()) {
            return new ArrayList<>(); // Retorna lista vazia para entradas nulas/em branco
        }
        // Divide a string por vírgulas, remove espaços em branco e filtra strings vazias
        return Arrays.stream(npcsText.split(","))
                     .map(String::trim)
                     .filter(s -> !s.isBlank())
                     .collect(Collectors.toList());
    }
}