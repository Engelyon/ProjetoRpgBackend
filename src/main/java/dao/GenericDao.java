package dao;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * GenericDao
 * * DAO (Data Access Object) genérico para operações CRUD (Criar, Ler, Atualizar, Deletar)
 * em qualquer tipo de entidade persistente.
 * Esta classe encapsula a lógica de acesso ao banco de dados utilizando JPA.
 * @param <T> O tipo da entidade sobre a qual o DAO irá operar.
 */
public class GenericDao<T> {
    // O EntityManager é a interface principal para interagir com o contexto de persistência.
    protected EntityManager em;
    // A classe da entidade que este DAO gerencia (ex: Usuario.class, Item.class).
    private final Class<T> classe;

    /**
     * Construtor da GenericDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     * @param classe A classe da entidade que este DAO irá gerenciar.
     */
    public GenericDao(EntityManager em, Class<T> classe) {
        this.em = em;
        this.classe = classe;
    }

    /**
     * Salva uma nova entidade no banco de dados.
     * Inicia uma transação, persiste a entidade e comita a transação.
     * @param entidade A entidade a ser salva.
     */
    public void salvar(T entidade) {
        em.getTransaction().begin(); // Inicia a transação
        em.persist(entidade);        // Persiste (salva) a entidade
        em.getTransaction().commit(); // Comita a transação
    }

    /**
     * Atualiza o estado de uma entidade existente no banco de dados.
     * Inicia uma transação, mescla as alterações da entidade e comita a transação.
     * @param entidade A entidade a ser atualizada.
     */
    public void atualizar(T entidade) {
        em.getTransaction().begin(); // Inicia a transação
        em.merge(entidade);          // Mescla (atualiza) a entidade
        em.getTransaction().commit(); // Comita a transação
    }

    /**
     * Remove uma entidade do banco de dados.
     * Inicia uma transação, remove a entidade e comita a transação.
     * Verifica se a entidade está no estado gerenciado antes de remover.
     * @param entidade A entidade a ser removida.
     */
    public void remover(T entidade) {
        em.getTransaction().begin(); // Inicia a transação
        // Garante que a entidade está no estado gerenciado antes de remover,
        // caso contrário, a mescla para anexá-la.
        em.remove(em.contains(entidade) ? entidade : em.merge(entidade)); 
        em.getTransaction().commit(); // Comita a transação
    }

    /**
     * Busca uma entidade no banco de dados pelo seu ID.
     * @param id O ID da entidade a ser buscada.
     * @return A entidade encontrada, ou null se não for encontrada.
     */
    public T buscarPorId(Long id) {
        return em.find(classe, id); // Encontra a entidade pelo ID
    }

    /**
     * Lista todas as entidades do tipo gerenciado por este DAO.
     * @return Uma lista contendo todas as entidades encontradas.
     */
    public List<T> listarTodos() {
        // Cria e executa uma query JPQL para selecionar todas as entidades do tipo
        return em.createQuery("SELECT e FROM " + classe.getSimpleName() + " e", classe)
                 .getResultList();
    }

    /**
     * Atualiza o estado de uma entidade gerenciada com os dados mais recentes do banco de dados.
     * Isso pode ser útil para sincronizar o objeto após alterações externas ou para inicializar
     * associações lazy.
     * @param entidade A entidade a ser atualizada.
     */
    public void refresh(T entidade) {
        em.refresh(entidade); // Atualiza o estado da entidade com dados do DB
    }

    /**
     * Desanexa uma entidade do contexto de persistência do EntityManager.
     * Após esta operação, a entidade passa para o estado "detached" e suas alterações
     * não serão mais sincronizadas automaticamente com o banco de dados.
     * @param entidade A entidade a ser desanexada.
     */
    public void detach(T entidade) {
        em.detach(entidade); // Desanexa a entidade
    }

    /**
     * Sincroniza o estado atual do contexto de persistência com o banco de dados,
     * forçando a execução de todas as operações pendentes (inserções, atualizações, deleções).
     */
    public void flush() {
        em.flush(); // Força a sincronização com o banco de dados
    }
}