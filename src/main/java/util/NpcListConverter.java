/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Npc;

/**
 * NpcListConverter
 * * Converte uma lista de objetos Npc para/de uma representação JSON (String)
 * para armazenamento em coluna de banco de dados.
 * Anotado com @Converter para ser reconhecido pelo JPA.
 * Esta classe é usada para colunas JSON que armazenam List<Npc>.
 */
@Converter // Anotação JPA para registrar este converter
public class NpcListConverter implements AttributeConverter<List<Npc>, String> {

    // Logger para registrar mensagens de erro ou informações
    private static final Logger logger = Logger.getLogger(NpcListConverter.class.getName());
    // ObjectMapper da biblioteca Jackson para serializar/desserializar JSON
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converte uma lista de objetos Npc para uma String no formato JSON,
     * para ser salva no banco de dados.
     * * @param npcList A lista de Npc a ser convertida.
     * @param npcList
     * @return A representação JSON (String) da lista de Npc, ou null se a lista for vazia.
     * @throws RuntimeException se ocorrer um erro durante a serialização JSON.
     */
    @Override
    public String convertToDatabaseColumn(List<Npc> npcList) {
        // Retorna null para o banco de dados se a lista estiver vazia ou nula
        if (npcList == null || npcList.isEmpty()) {
            return null; 
        }
        try {
            // Serializa a lista de objetos Npc diretamente para JSON.
            // O Jackson usa os getters/setters de Npc para criar o JSON de objetos.
            return mapper.writeValueAsString(npcList); 
        } catch (JsonProcessingException e) {
            // Loga o erro grave e relança como RuntimeException, pois é um erro de conversão crítico
            logger.log(Level.SEVERE, "Erro ao converter lista de Npcs para JSON", e);
            throw new RuntimeException("Erro ao converter lista de Npcs para JSON", e);
        }
    }

    /**
     * Converte uma String no formato JSON (do banco de dados) de volta para uma
     * lista de objetos Npc.
     * * @param dbData A String JSON lida do banco de dados.
     * @return Uma lista de objetos Npc, ou uma lista vazia se a String JSON for vazia/nula.
     * @throws RuntimeException se ocorrer um erro durante a desserialização JSON.
     */
    @Override
    public List<Npc> convertToEntityAttribute(String dbData) {
        // Retorna uma lista vazia se os dados do banco forem nulos ou vazios
        if (dbData == null || dbData.trim().isEmpty()) {
            return Collections.emptyList(); 
        }
        try {
            // Desserializa a String JSON diretamente para uma lista de objetos Npc.
            // O Jackson usa o TypeReference para saber o tipo complexo (List<Npc>).
            return mapper.readValue(dbData, new TypeReference<List<Npc>>() {}); 
        } catch (IOException e) {
            // Loga o erro grave e relança como RuntimeException
            logger.log(Level.SEVERE, "Erro ao converter JSON de Npcs para lista", e);
            throw new RuntimeException("Erro ao converter JSON de Npcs para lista", e);
        }
    }
}