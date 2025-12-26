package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Local;

/**
 * LocalDao
 * * DAO (Data Access Object) específico para a entidade Local.
 * Estende GenericDao para herdar as operações CRUD básicas
 * e adiciona métodos de busca específicos para Local.
 */
public class LocalDao extends ConteudoDao<Local> {

    /**
     * Construtor da LocalDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     */
    public LocalDao(EntityManager em) {
        super(em, Local.class);
    }
    
    /**
     * Busca uma entidade Local no banco de dados pelo seu título.
     *
     * @param titulo O título do Local a ser buscado.
     * @return O objeto Local encontrado, ou null se não houver um Local com o título especificado.
     */
    public Local buscarPorTitulo(String titulo) {
        try {
            // Cria uma consulta JPQL para buscar um Local pelo seu título.
            TypedQuery<Local> query = em.createQuery(
                    "SELECT l FROM Local l WHERE l.titulo = :titulo", Local.class);
            query.setParameter("titulo", titulo); // Define o parâmetro da query
            return query.getSingleResult(); // Retorna o único resultado encontrado
        } catch (NoResultException e) {
            // Captura a exceção se nenhum resultado for encontrado e retorna null.
            return null;
        }
    }
}