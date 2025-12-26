package dao;

import jakarta.persistence.EntityManager; // Importa EntityManager do JPA
import model.SubClasse; // Importa a entidade SubClasse

/**
 * SubClasseDao
 * * DAO (Data Access Object) específico para a entidade SubClasse.
 * Estende GenericDao para herdar as operações CRUD básicas
 * (salvar, atualizar, remover, buscarPorId, listarTodos).
 */
public class SubClasseDao extends ConteudoDao<SubClasse> {

    /**
     * Construtor da SubClasseDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     */
    public SubClasseDao(EntityManager em) {
        super(em, SubClasse.class); // Chama o construtor da classe pai (GenericDao)
    }
    
    // Métodos específicos para SubClasse podem ser adicionados aqui, se necessários.
    // Por exemplo: buscarSubClassesPorClassePai(String classePai)
}