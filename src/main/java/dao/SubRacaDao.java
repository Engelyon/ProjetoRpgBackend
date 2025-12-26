package dao;

import jakarta.persistence.EntityManager; // Importa EntityManager do JPA
import model.SubRaca; // Importa a entidade SubRaca

/**
 * SubRacaDao
 * * DAO (Data Access Object) específico para a entidade SubRaça.
 * Estende GenericDao para herdar as operações CRUD básicas
 * (salvar, atualizar, remover, buscarPorId, listarTodos).
 */
public class SubRacaDao extends ConteudoDao<SubRaca> {

    /**
     * Construtor da SubRacaDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     */
    public SubRacaDao(EntityManager em) {
        super(em, SubRaca.class); // Chama o construtor da classe pai (GenericDao)
    }
    
    // Métodos específicos para SubRaça podem ser adicionados aqui, se necessários.
    // Por exemplo: buscarSubRacasPorRacaPai(String racaPai)
}