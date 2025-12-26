package dao;

import jakarta.persistence.EntityManager; // Importa EntityManager do JPA
import model.Raca; // Importa a entidade Raça

/**
 * RacaDao
 * * DAO (Data Access Object) específico para a entidade Raça.
 * Estende GenericDao para herdar as operações CRUD básicas
 * (salvar, atualizar, remover, buscarPorId, listarTodos).
 */
public class RacaDao extends ConteudoDao<Raca> {

    /**
     * Construtor da RacaDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     */
    public RacaDao(EntityManager em) {
        super(em, Raca.class); // Chama o construtor da classe pai (GenericDao)
    }
    
    // Métodos específicos para Raça podem ser adicionados aqui, se necessários.
    // Por exemplo: buscarRacasPorBonus(String bonus)
}