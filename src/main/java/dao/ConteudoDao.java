package dao;

import model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConteudoDao
 * * DAO (Data Access Object) genérico para a entidade Conteudo e suas subclasses.
 * Permite realizar operações de busca e deleção de Conteudo no banco de dados.
 * Estende GenericDao para operações CRUD básicas.
 * @param <T> O tipo específico de Conteudo (Conteudo ou uma de suas subclasses).
 */
public class ConteudoDao<T extends Conteudo> extends GenericDao<T> {

    // Logger para registrar mensagens de erro ou informações
    private static final Logger logger = Logger.getLogger(ConteudoDao.class.getName());

    /**
     * Construtor da ConteudoDao.
     * @param em O EntityManager a ser utilizado para operações de persistência.
     * @param classe A classe da entidade Conteudo ou de uma subclasse específica.
     */
    public ConteudoDao(EntityManager em, Class<T> classe) {
        super(em, classe);
    }

    /**
     * Busca conteúdos com filtros opcionais utilizando JPQL dinâmica.
     * Este método é mais simples, e para filtros complexos com atributos de subclasses,
     * o método {@code buscarPorFiltros} é recomendado.
     *
     * @param tipo Nome do tipo de conteúdo (ex: "Item", "Classe", "Magia"). Pode ser nulo ou vazio para não filtrar por tipo.
     * @param titulo Parte do título (case-insensitive). Pode ser nulo ou vazio para não filtrar por título.
     * @param autor Usuário autor do conteúdo. Pode ser nulo para não filtrar por autor.
     * @param sistema Sistema ao qual o conteúdo pertence. Pode ser nulo para não filtrar por sistema.
     * @return Lista de Conteudos que correspondem aos filtros.
     */
    public List<Conteudo> buscarComFiltros(String tipo, String titulo, Usuario autor, Sistema sistema) {
        StringBuilder jpql = new StringBuilder("SELECT c FROM Conteudo c WHERE 1=1");

        if (tipo != null && !tipo.isEmpty() && !tipo.equalsIgnoreCase("todos")) {
            jpql.append(" AND TYPE(c) = :tipoClasse");
        }
        if (titulo != null && !titulo.isEmpty()) {
            jpql.append(" AND LOWER(c.titulo) LIKE :titulo");
        }
        if (autor != null) {
            jpql.append(" AND c.autor = :autor");
        }
        if (sistema != null) {
            jpql.append(" AND c.sistema = :sistema");
        }

        TypedQuery<Conteudo> query = em.createQuery(jpql.toString(), Conteudo.class);

        if (tipo != null && !tipo.isEmpty() && !tipo.equalsIgnoreCase("todos")) {
            Class<? extends Conteudo> tipoClasse = getConteudoClassFromString(tipo);
            query.setParameter("tipoClasse", tipoClasse);
        }

        if (titulo != null && !titulo.isEmpty()) {
            query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
        }
        if (autor != null) {
            query.setParameter("autor", autor);
        }
        if (sistema != null) {
            query.setParameter("sistema", sistema);
        }

        return query.getResultList();
    }

    /**
     * Busca conteúdos com filtros opcionais usando a Criteria API para filtros gerais,
     * e delega a métodos JPQL específicos para filtros de atributos de subclasses
     * (como raridade em Item ou nível em Magia).
     *
     * @param filtros Mapa de filtros onde a chave é o nome do campo (ex: "titulo", "autor", "tipoConteudo", "raridade", "nivel")
     * e o valor é o critério de busca.
     * @return Lista de Conteudos que correspondem aos filtros.
     */
    public List<Conteudo> buscarPorFiltros(Map<String, Object> filtros) {
        // Extrai os filtros que podem determinar uma busca JPQL específica
        String tipoConteudoFiltro = (String) filtros.get("tipoConteudo");
        String raridadeFiltro = (String) filtros.get("raridade");
        Integer nivelFiltro = (Integer) filtros.get("nivel");

        // Estratégia 1: Filtrar Item por Raridade usando JPQL específica
        // Desvia para um método JPQL se o tipo for "Item" e a raridade for especificada.
        if (tipoConteudoFiltro != null && tipoConteudoFiltro.equalsIgnoreCase("Item")
                && raridadeFiltro != null && !raridadeFiltro.isBlank() && !raridadeFiltro.equalsIgnoreCase("Todos")) {
            logger.log(Level.INFO, "Desviando para buscarItensPorFiltrosJPQL para filtro de Raridade.");
            return buscarItensPorFiltrosJPQL(filtros);
        }
        // Estratégia 2: Filtrar Magia por Nível usando JPQL específica
        // Desvia para um método JPQL se o tipo for "Magia" e o nível for especificado.
        if (tipoConteudoFiltro != null && tipoConteudoFiltro.equalsIgnoreCase("Magia")
                && nivelFiltro != null && nivelFiltro > 0) {
            logger.log(Level.INFO, "Desviando para buscarMagiasPorFiltrosJPQL para filtro de Nível.");
            return buscarMagiasPorFiltrosJPQL(filtros);
        }
        
        logger.log(Level.INFO, "Usando Criteria API para busca geral.");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Conteudo> cq = cb.createQuery(Conteudo.class);
        Root<Conteudo> root = cq.from(Conteudo.class);

        // Joins opcionais para acessar atributos de entidades relacionadas (Autor, Sistema)
        Join<Conteudo, Usuario> joinAutor = null;
        if (filtros.containsKey("autor")) {
            joinAutor = root.join("autor", JoinType.LEFT);
        }
        Join<Conteudo, Sistema> joinSistema = null;
        if (filtros.containsKey("sistema")) {
            joinSistema = root.join("sistema", JoinType.LEFT);
        }

        List<Predicate> predicados = new ArrayList<>();

        // Adiciona predicados baseados nos filtros gerais
        if (filtros.containsKey("titulo")) {
            String valor = filtros.get("titulo").toString().toLowerCase();
            predicados.add(cb.like(cb.lower(root.get("titulo")), "%" + valor + "%"));
        }
        if (filtros.containsKey("autor") && joinAutor != null) {
            Object autorFiltro = filtros.get("autor");
            if (autorFiltro instanceof Usuario) { // Se o filtro for um objeto Usuario
                predicados.add(cb.equal(joinAutor, (Usuario) autorFiltro));
            } else if (autorFiltro instanceof String && !((String) autorFiltro).isBlank()) { // Se o filtro for o nome do autor
                String nomeAutor = ((String) autorFiltro).toLowerCase();
                predicados.add(cb.like(cb.lower(joinAutor.get("nome")), "%" + nomeAutor + "%"));
            }
        }
        if (filtros.containsKey("sistema") && joinSistema != null) {
            predicados.add(cb.equal(joinSistema, filtros.get("sistema")));
        }
        // Aplica filtro de tipo de conteúdo se não for "Todos" e não foi desviado para JPQL específica
        if (tipoConteudoFiltro != null && !tipoConteudoFiltro.equalsIgnoreCase("todos")) {
            Class<? extends Conteudo> tipoClasseParaFiltro = getConteudoClassFromString(tipoConteudoFiltro);
            predicados.add(cb.equal(root.type(), tipoClasseParaFiltro));
        }
        if (filtros.containsKey("tags")) {
            String tag = filtros.get("tags").toString().toLowerCase();
            predicados.add(cb.like(cb.lower(root.get("tags")), "%\"" + tag + "\"%"));
        }

        // Adiciona todos os predicados à query
        cq.where(predicados.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("titulo")));

        return em.createQuery(cq).getResultList();
    }

    /**
     * Busca entidades Item com filtros específicos (incluindo raridade) utilizando JPQL.
     * Este método é chamado internamente por {@code buscarPorFiltros} quando o filtro de raridade é aplicado a Itens.
     *
     * @param filtros Mapa de filtros com critérios de busca para Item.
     * @return Lista de Conteudos (Items) que correspondem aos filtros.
     */
    private List<Conteudo> buscarItensPorFiltrosJPQL(Map<String, Object> filtros) {
        StringBuilder jpql = new StringBuilder("SELECT i FROM Item i LEFT JOIN i.autor a LEFT JOIN i.sistema s WHERE 1=1");

        String titulo = (String) filtros.get("titulo");
        Object autorFiltro = filtros.get("autor");
        String tags = (String) filtros.get("tags");
        Sistema sistema = (Sistema) filtros.get("sistema");
        String raridade = (String) filtros.get("raridade");

        if (titulo != null && !titulo.isBlank()) {
            jpql.append(" AND LOWER(i.titulo) LIKE :titulo");
        }
        if (autorFiltro != null) {
            if (autorFiltro instanceof Usuario) { // Se o filtro de autor é um objeto Usuario
                jpql.append(" AND i.autor = :autorObjeto");
            } else if (autorFiltro instanceof String && !((String) autorFiltro).isBlank()) { // Se o filtro de autor é um nome (String)
                jpql.append(" AND LOWER(a.nome) LIKE :autorNome");
            }
        }
        if (tags != null && !tags.isBlank()) {
            jpql.append(" AND LOWER(i.tags) LIKE :tags");
        }
        if (sistema != null) {
            jpql.append(" AND i.sistema = :sistema");
        }
        // Filtro de raridade para Item (sempre aplicado neste método)
        if (raridade != null && !raridade.isBlank() && !raridade.equalsIgnoreCase("Todos")) {
            jpql.append(" AND i.raridade = :raridadeParam");
        }

        jpql.append(" ORDER BY i.titulo ASC");

        TypedQuery<Conteudo> query = em.createQuery(jpql.toString(), Conteudo.class);

        if (titulo != null && !titulo.isBlank()) {
            query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
        }
        if (autorFiltro != null) {
            if (autorFiltro instanceof Usuario) {
                query.setParameter("autorObjeto", (Usuario) autorFiltro);
            } else if (autorFiltro instanceof String && !((String) autorFiltro).isBlank()) {
                query.setParameter("autorNome", "%" + ((String) autorFiltro).toLowerCase() + "%");
            }
        }
        if (tags != null && !tags.isBlank()) {
            query.setParameter("tags", "%\"" + tags.toLowerCase() + "\"%");
        }
        if (sistema != null) {
            query.setParameter("sistema", sistema);
        }
        if (raridade != null && !raridade.isBlank() && !raridade.equalsIgnoreCase("Todos")) {
            query.setParameter("raridadeParam", raridade);
        }

        return query.getResultList();
    }

    /**
     * Busca entidades Magia com filtros específicos (incluindo nível) utilizando JPQL.
     * Este método é chamado internamente por {@code buscarPorFiltros} quando o filtro de nível é aplicado a Magias.
     *
     * @param filtros Mapa de filtros com critérios de busca para Magia.
     * @return Lista de Conteudos (Magias) que correspondem aos filtros.
     */
    private List<Conteudo> buscarMagiasPorFiltrosJPQL(Map<String, Object> filtros) {
        StringBuilder jpql = new StringBuilder("SELECT m FROM Magia m LEFT JOIN m.autor a LEFT JOIN m.sistema s WHERE 1=1");

        String titulo = (String) filtros.get("titulo");
        Object autorFiltro = filtros.get("autor");
        String tags = (String) filtros.get("tags");
        Sistema sistema = (Sistema) filtros.get("sistema");
        Integer nivel = (Integer) filtros.get("nivel");

        if (titulo != null && !titulo.isBlank()) {
            jpql.append(" AND LOWER(m.titulo) LIKE :titulo");
        }

        if (autorFiltro != null) {
            if (autorFiltro instanceof Usuario) {
                jpql.append(" AND m.autor = :autorObjeto");
            } else if (autorFiltro instanceof String && !((String) autorFiltro).isBlank()) {
                jpql.append(" AND LOWER(a.nome) LIKE :autorNome");
            }
        }
        if (tags != null && !tags.isBlank()) {
            jpql.append(" AND LOWER(m.tags) LIKE :tags");
        }
        if (sistema != null) {
            jpql.append(" AND m.sistema = :sistema");
        }
        // Filtro de nível para Magia (sempre aplicado neste método)
        if (nivel != null && nivel > 0) {
            jpql.append(" AND m.nivel = :nivel");
        }

        jpql.append(" ORDER BY m.titulo ASC");

        TypedQuery<Conteudo> query = em.createQuery(jpql.toString(), Conteudo.class);

        if (titulo != null && !titulo.isBlank()) {
            query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
        }
        if (autorFiltro != null) {
            if (autorFiltro instanceof Usuario) {
                query.setParameter("autorObjeto", (Usuario) autorFiltro);
            } else if (autorFiltro instanceof String && !((String) autorFiltro).isBlank()) {
                query.setParameter("autorNome", "%" + ((String) autorFiltro).toLowerCase() + "%");
            }
        }
        if (tags != null && !tags.isBlank()) {
            query.setParameter("tags", "%\"" + tags.toLowerCase() + "\"%");
        }
        if (sistema != null) {
            query.setParameter("sistema", sistema);
        }
        if (nivel != null && nivel > 0) {
            query.setParameter("nivel", nivel);
        }

        return query.getResultList();
    }

    /**
     * Método auxiliar para obter o objeto Class correspondente a uma string de
     * tipo de conteúdo.
     * @param tipoString A string do tipo de conteúdo (ex: "Item", "Magia").
     * @return A classe da entidade correspondente.
     * @throws IllegalArgumentException se o tipo de conteúdo não for reconhecido.
     */
    private Class<? extends Conteudo> getConteudoClassFromString(String tipoString) {
        switch (tipoString.toLowerCase()) {
            case "item":
                return Item.class;
            case "magia":
                return Magia.class;
            case "classe":
                return Classe.class;
            case "raca":
                return Raca.class;
            case "quest":
                return Quest.class;
            case "local":
                return Local.class;
            case "subclasse":
                return SubClasse.class;
            case "subraca":
                return SubRaca.class;
            default:
                throw new IllegalArgumentException("Tipo de conteúdo desconhecido: " + tipoString); // Lança exceção para tipo não reconhecido
        }
    }

    /**
     * Remove uma entidade do banco de dados.
     * Esta operação deve ser chamada dentro de uma transação ativa.
     *
     * @param entity A entidade a ser removida.
     */
    public void deletar(T entity) {
        em.remove(entity); // Remove a entidade do contexto de persistência
    }
}