package dao;

import jakarta.persistence.EntityManager; // Importa EntityManager do JPA
import model.Classe; // Importa a entidade Classe

/**
 * ClasseDao
 * * DAO (Data Access Object) específico para a entidade Classe.
 * Estende GenericDao para herdar as operações CRUD básicas
 * (salvar, atualizar, remover, buscarPorId, listarTodos).
 */
public class ClasseDao extends ConteudoDao<Classe> {

    /**
     * Construtor da ClasseDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     */
    public ClasseDao(EntityManager em) {
        super(em, Classe.class); // Chama o construtor da classe pai (GenericDao)
    }
    
    // Métodos específicos para Classe podem ser adicionados aqui, se necessários.
    // Por exemplo: buscarClassesPorHabilidade(String habilidade)
}