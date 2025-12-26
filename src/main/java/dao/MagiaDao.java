package dao;

import jakarta.persistence.EntityManager; // Importa EntityManager do JPA
import model.Magia; // Importa a entidade Magia

/**
 * MagiaDao
 * * DAO (Data Access Object) específico para a entidade Magia.
 * Estende GenericDao para herdar as operações CRUD básicas
 * (salvar, atualizar, remover, buscarPorId, listarTodos).
 */
public class MagiaDao extends ConteudoDao<Magia>{

    /**
     * Construtor da MagiaDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     */
    public MagiaDao(EntityManager em) {
        super(em, Magia.class); // Chama o construtor da classe pai (GenericDao)
    }
    
    // Métodos específicos para Magia podem ser adicionados aqui, se necessários.
    // Por exemplo: buscarMagiasPorEscola(String escola)
    // A lógica de busca por nível já é tratada no ConteudoDao.buscarPorFiltros.
}