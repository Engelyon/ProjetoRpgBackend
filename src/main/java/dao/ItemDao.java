package dao;

import jakarta.persistence.EntityManager; // Importa EntityManager do JPA
import model.Item; // Importa a entidade Item

/**
 * ItemDao
 * * DAO (Data Access Object) específico para a entidade Item.
 * Estende ConteudoDao para herdar as operações de busca e deleção
 * de Conteudo, e as operações CRUD básicas do GenericDao.
 */
public class ItemDao extends ConteudoDao<Item> { // Herda de ConteudoDao, que por sua vez herda de GenericDao

    /**
     * Construtor da ItemDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     */
    public ItemDao(EntityManager em) {
        super(em, Item.class); // Chama o construtor da classe pai (ConteudoDao)
    }
    
    // Métodos específicos para Item podem ser adicionados aqui, se necessários.
    // Por exemplo: buscarItensPorRaridade(String raridade)
    // A lógica de busca por raridade já é tratada no ConteudoDao.buscarPorFiltros.
}