package model;

import com.fasterxml.jackson.core.type.TypeReference; // Importa TypeReference do Jackson
import com.fasterxml.jackson.databind.ObjectMapper;   // Importa ObjectMapper do Jackson
import jakarta.persistence.*; // Importa todas as anotações JPA
import java.util.Collections; // Importa Collections para lista vazia
import java.util.List;       // Importa a interface List

/**
 * Sistema
 * * Representa um sistema de RPG (ex: "D&D 5e", "Pathfinder").
 * Esta entidade armazena informações sobre o sistema, incluindo
 * raridades e tipos de itens em formato JSON.
 * Mapeada para a tabela "sistema" no banco de dados.
 */
@Entity // Marca esta classe como uma entidade persistente
@Table(name = "sistema") // Mapeia a entidade para a tabela "sistema"
public class Sistema {

    // Identificador único do sistema. Gerado automaticamente.
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estratégia de geração de ID (auto-incremento)
    private Long id;
    // Nome do sistema (ex: "D&D 5e")
    private String nome;

    // String JSON que armazena os tipos de itens disponíveis para este sistema.
    // Mapeado para coluna 'tipos_itens' no banco.
    @Column(name = "tipos_itens", columnDefinition = "json") 
    private String tiposItensJson;

    // String JSON que armazena as raridades de itens disponíveis para este sistema.
    // Mapeado para coluna 'raridades' no banco.
    @Column(name = "raridades", columnDefinition = "json") 
    private String raridadesJson;

    // --- Getters e Setters para campos de banco de dados ---

    public String getTiposItensJson() {
        return tiposItensJson;
    }

    public void setTiposItensJson(String tiposItensJson) {
        this.tiposItensJson = tiposItensJson;
    }

    public String getRaridadesJson() {
        return raridadesJson;
    }

    public void setRaridadesJson(String raridadesJson) {
        this.raridadesJson = raridadesJson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // --- Métodos utilitários para converter JSON em listas Java ---

    /**
     * Converte a String JSON de tipos de itens em uma lista de Strings.
     * @Transient: Indica que este método/campo não será persistido pelo JPA diretamente.
     * @return Uma lista de Strings representando os tipos de itens, ou uma lista vazia em caso de erro/null.
     */
    @Transient // Indica que este campo/método não é persistente no banco de dados
    public List<String> getTiposItens() {
        try {
            // ObjectMapper é usado para ler a String JSON e convertê-la para List<String>
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(tiposItensJson, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erro grave ao ler JSON do sistema", e);
        }
    }

    /**
     * Converte a String JSON de raridades em uma lista de Strings.
     * @Transient: Indica que este método/campo não será persistido pelo JPA diretamente.
     * @return Uma lista de Strings representando as raridades, ou uma lista vazia em caso de erro/null.
     */
    @Transient // Indica que este campo/método não é persistente no banco de dados
    public List<String> getRaridades() {
        try {
            // ObjectMapper é usado para ler a String JSON e convertê-la para List<String>
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(raridadesJson, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erro grave ao ler JSON do sistema", e);
        }
    }

    /**
     * Retorna o nome do sistema como sua representação em String.
     * Útil para exibição em JComboBoxes.
     * @return O nome do sistema.
     */
    @Override // Sobrescreve o método toString da classe Object
    public String toString() {
        return nome;
    }
}