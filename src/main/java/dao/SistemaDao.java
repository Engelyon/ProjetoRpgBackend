package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.Sistema;

/**
 * SistemaDao
 * * DAO (Data Access Object) específico para a entidade Sistema.
 * Estende GenericDao para herdar as operações CRUD básicas
 * e adiciona métodos de busca específicos para Sistema.
 */
public class SistemaDao extends GenericDao<Sistema> {

    /**
     * Construtor da SistemaDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     */
    public SistemaDao(EntityManager em) {
        super(em, Sistema.class);
    }

    /**
     * Lista todos os sistemas cadastrados no banco de dados.
     * Reimplementa o método listarTodos() da classe pai para clareza no escopo.
     * @return Uma lista contendo todos os sistemas encontrados.
     */
    @Override // Boa prática adicionar @Override quando reimplementando
    public List<Sistema> listarTodos() {
        return em.createQuery("SELECT s FROM Sistema s", Sistema.class).getResultList();
    }

    /**
     * Busca um sistema no banco de dados pelo seu nome exato.
     * @param nome O nome do sistema a ser buscado.
     * @return O objeto Sistema correspondente, ou null se não for encontrado.
     */
    public Sistema buscarPorNome(String nome) {
        try {
            // Cria uma consulta JPQL para buscar um Sistema pelo seu nome exato.
            TypedQuery<Sistema> query = em.createQuery("SELECT s FROM Sistema s WHERE s.nome = :nome", Sistema.class);
            query.setParameter("nome", nome); // Define o parâmetro 'nome' da query
            return query.getSingleResult(); // Retorna o único resultado encontrado
        } catch (NoResultException e) {
            // Captura a exceção se nenhum resultado for encontrado e retorna null.
            return null;
        }
    }
}