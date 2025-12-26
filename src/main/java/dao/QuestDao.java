package dao;

import jakarta.persistence.EntityManager; // Importa EntityManager do JPA
import model.Quest; // Importa a entidade Quest

/**
 * QuestDao
 * * DAO (Data Access Object) específico para a entidade Quest.
 * Estende GenericDao para herdar as operações CRUD básicas
 * (salvar, atualizar, remover, buscarPorId, listarTodos).
 */
public class QuestDao extends ConteudoDao<Quest> {

    /**
     * Construtor da QuestDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     */
    public QuestDao(EntityManager em) {
        super(em, Quest.class); // Chama o construtor da classe pai (GenericDao)
    }
    
    // Métodos específicos para Quest podem ser adicionados aqui, se necessários.
    // Por exemplo: buscarQuestsPorCliente(String cliente)
}