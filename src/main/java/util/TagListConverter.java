package util;

import com.fasterxml.jackson.core.type.TypeReference;     // Importa TypeReference para tipos genéricos
import com.fasterxml.jackson.databind.ObjectMapper;       // Importa ObjectMapper para serialização/desserialização
import jakarta.persistence.AttributeConverter;            // Importa a interface principal para converters JPA
import jakarta.persistence.Converter;                     // Importa a anotação para registrar o converter
import java.util.Collections;                           // Importa Collections para lista vazia
import java.util.List;                                  // Importa a interface List
import java.io.IOException;                             // Importa exceções de I/O (para catch)
import com.fasterxml.jackson.core.JsonProcessingException; // Importa JsonProcessingException (para catch)

/**
 * TagListConverter
 * * Converte uma lista de Strings (usada para tags) para/de uma representação JSON (String)
 * para armazenamento em coluna de banco de dados.
 * Anotado com @Converter para ser reconhecido pelo JPA.
 * Esta classe é usada para colunas JSON que armazenam List<String>.
 */
@Converter // Anotação JPA para registrar este converter automaticamente
public class TagListConverter implements AttributeConverter<List<String>, String> {

    // ObjectMapper da biblioteca Jackson para realizar a serialização/desserialização JSON
    // Declarado como static final por ser thread-safe e otimizado para reuso.
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converte uma lista de Strings (tags) para uma String no formato JSON,
     * para ser salva no banco de dados.
     * @param attribute A lista de Strings de tags a ser convertida.
     * @return A representação JSON (String) da lista de tags, ou null se a lista for nula ou vazia.
     * @throws RuntimeException se ocorrer um erro durante a serialização JSON.
     */
    @Override // Indica que este método sobrescreve um método da interface AttributeConverter
    public String convertToDatabaseColumn(List<String> attribute) {
        // Retorna null para o banco de dados se a lista estiver vazia ou nula
        if (attribute == null || attribute.isEmpty()) { 
            return null;
        }
        try {
            // Serializa a lista de Strings para uma String JSON.
            return mapper.writeValueAsString(attribute); 
        } catch (JsonProcessingException e) {
            // Lança uma RuntimeException para indicar um erro crítico de conversão JSON.
            throw new RuntimeException("Erro ao converter lista para JSON", e);
        }
    }

    /**
     * Converte uma String no formato JSON (lida do banco de dados) de volta para
     * uma lista de Strings (tags).
     * @param dbData A String JSON lida do banco de dados.
     * @return Uma lista de Strings representando as tags, ou uma lista vazia se a String JSON for nula ou vazia.
     * @throws RuntimeException se ocorrer um erro durante a desserialização JSON.
     */
    @Override // Indica que este método sobrescreve um método da interface AttributeConverter
    public List<String> convertToEntityAttribute(String dbData) {
        // Retorna uma lista vazia se os dados do banco forem nulos ou vazios
        if (dbData == null || dbData.trim().isEmpty()) { 
            return Collections.emptyList(); // Retorna uma lista imutável e vazia
        }
        try {
            // Desserializa a String JSON para uma lista de Strings.
            // TypeReference é usado para especificar o tipo genérico (List<String>).
            return mapper.readValue(dbData, new TypeReference<List<String>>() {}); 
        } catch (IOException e) {
            // Lança uma RuntimeException para indicar um erro crítico de conversão JSON.
            throw new RuntimeException("Erro ao converter JSON para lista", e);
        }
    }
}