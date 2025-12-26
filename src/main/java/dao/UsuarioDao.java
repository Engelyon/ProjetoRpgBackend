package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery; 
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

/**
 * UsuarioDao
 * * DAO (Data Access Object) específico para a entidade Usuario.
 * Estende GenericDao para herdar as operações CRUD básicas
 * e adiciona métodos específicos para autenticação e busca de usuário.
 */
public class UsuarioDao extends GenericDao<Usuario> {

    /**
     * Construtor da UsuarioDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     */
    public UsuarioDao(EntityManager em) {
        super(em, Usuario.class);
    }

    /**
     * Salva um novo usuário no banco de dados.
     * Antes de salvar, a senha do usuário é hashed utilizando BCrypt para segurança.
     * Reimplementa o método salvar() da classe pai para adicionar a lógica de hashing.
     * @param usuario O objeto Usuario a ser salvo.
     */
    @Override // Boa prática adicionar @Override quando reimplementando
    public void salvar(Usuario usuario) {
        // Gera o hash da senha usando BCrypt antes de salvar no banco de dados
        String hash = BCrypt.hashpw(usuario.getSenha_hash(), BCrypt.gensalt());
        usuario.setSenha_hash(hash); // Atribui o hash gerado de volta ao usuário
        super.salvar(usuario); // Chama o método salvar da classe pai para persistência
    }

    /**
     * Autentica um usuário verificando seu e-mail e senha.
     * A senha fornecida é comparada com o hash armazenado no banco de dados.
     * @param email O e-mail do usuário.
     * @param senha A senha em texto puro fornecida pelo usuário.
     * @return true se as credenciais forem válidas (usuário encontrado e senha corresponde), false caso contrário.
     */
    public boolean autentica(String email, String senha) {
        // Cria uma consulta para buscar o usuário pelo e-mail
        TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
        query.setParameter("email", email); // Define o parâmetro 'email' da query
        try {
            Usuario user = query.getSingleResult(); // Tenta obter o usuário único
            // Compara a senha fornecida com o hash armazenado usando BCrypt
            return BCrypt.checkpw(senha, user.getSenha_hash());
        } catch (NoResultException e) {
            // Se nenhum usuário for encontrado com o e-mail, retorna false (falha na autenticação)
            return false;
        }
    }

    /**
     * Busca um usuário no banco de dados pelo seu e-mail exato.
     * @param email O e-mail do usuário a ser buscado.
     * @return O objeto Usuario encontrado, ou null se não for encontrado.
     */
    public Usuario buscarPorEmail(String email) {
        try {
            // Cria uma consulta para buscar o usuário pelo e-mail
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
            query.setParameter("email", email); // Define o parâmetro 'email' da query
            return query.getSingleResult(); // Retorna o único resultado encontrado
        } catch (NoResultException e) {
            // Se nenhum usuário for encontrado com o e-mail, retorna null.
            return null;
        }
    }
}