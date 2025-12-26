package util;

import com.fasterxml.jackson.core.JsonProcessingException; // Importa exceção de processamento JSON
import com.fasterxml.jackson.core.type.TypeReference;     // Importa TypeReference para tipos genéricos
import com.fasterxml.jackson.databind.ObjectMapper;       // Importa ObjectMapper para serialização/desserialização
import jakarta.persistence.AttributeConverter;            // Importa a interface principal para converters JPA
import jakarta.persistence.Converter;                     // Importa a anotação para registrar o converter
import java.io.IOException;                             // Importa exceções de I/O
import java.util.Collections;                           // Importa Collections para lista vazia (opcional, se usado)
import java.util.List;                                  // Importa a interface List
import model.Bonus;                                     // Importa a classe de modelo Bonus

/**
 * BonusListConverter
 * * Converte uma lista de objetos Bonus para/de uma representação JSON (String)
 * para armazenamento em coluna de banco de dados.
 * Anotado com @Converter para ser reconhecido pelo JPA.
 * Esta classe é usada para colunas JSON que armazenam List<Bonus>.
 */
@Converter // Anotação JPA para registrar este converter automaticamente
public class BonusListConverter implements AttributeConverter<List<Bonus>, String> {

    // ObjectMapper da biblioteca Jackson para realizar a serialização/desserialização JSON
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converte uma lista de objetos Bonus para uma String no formato JSON,
     * para ser salva no banco de dados.
     * @param bonusList A lista de Bonus a ser convertida.
     * @return A representação JSON (String) da lista de Bonus, ou null se a lista for nula ou vazia.
     * @throws RuntimeException se ocorrer um erro durante a serialização JSON.
     */
    @Override // Indica que este método sobrescreve um método da interface AttributeConverter
    public String convertToDatabaseColumn(List<Bonus> bonusList) {
        // Retorna null para o banco de dados se a lista estiver vazia ou nula
        if (bonusList == null || bonusList.isEmpty()) {
            return null;
        }
        try {
            // Serializa a lista de objetos Bonus diretamente para uma String JSON.
            // O Jackson utiliza os getters/setters da classe Bonus para criar a estrutura JSON.
            return mapper.writeValueAsString(bonusList); 
        } catch (JsonProcessingException e) {
            // Lança uma RuntimeException para indicar um erro crítico de conversão JSON.
            throw new RuntimeException("Erro ao converter lista de bônus para JSON", e);
        }
    }

    /**
     * Converte uma String no formato JSON (lida do banco de dados) de volta para
     * uma lista de objetos Bonus.
     * @param json A String JSON lida do banco de dados.
     * @return Uma lista de objetos Bonus, ou uma lista vazia se a String JSON for nula ou vazia.
     * @throws RuntimeException se ocorrer um erro durante a desserialização JSON.
     */
    @Override // Indica que este método sobrescreve um método da interface AttributeConverter
    public List<Bonus> convertToEntityAttribute(String json) {
        // Retorna uma lista vazia se os dados do banco forem nulos ou vazios
        if (json == null || json.trim().isEmpty()) { 
            return Collections.emptyList(); // Retorna uma lista imutável e vazia
        }
        try {
            // Desserializa a String JSON diretamente para uma lista de objetos Bonus.
            // TypeReference é usado para especificar o tipo genérico (List<Bonus>) para o Jackson.
            return mapper.readValue(json, new TypeReference<List<Bonus>>() {}); 
        } catch (IOException e) {
            // Lança uma RuntimeException para indicar um erro crítico de conversão JSON.
            throw new RuntimeException("Erro ao converter JSON para lista de bônus", e);
        }
    }
}