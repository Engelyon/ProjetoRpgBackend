package util;

import jakarta.persistence.EntityManager; // Importa EntityManager do JPA
import jakarta.persistence.EntityManagerFactory; // Importa EntityManagerFactory do JPA
import jakarta.persistence.Persistence; // Importa a classe Persistence para criar o EMF

/**
 * JpaUtil
 * * Classe utilitária para gerenciar a fábrica de EntityManagers (EntityManagerFactory)
 * e obter instâncias de EntityManager para interagir com o banco de dados.
 * Garante que apenas uma EntityManagerFactory seja criada por aplicação.
 */
public class JpaUtil {
    // A fábrica de EntityManagers, criada uma única vez quando a classe é carregada.
    // O nome "rpgdatabase_pu" deve corresponder ao <persistence-unit name="..."> no persistence.xml.
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("rpgdatabase_pu");
    
    /**
     * Obtém uma nova instância de EntityManager.
     * Cada EntityManager representa uma sessão de trabalho com o banco de dados
     * e deve ser fechado após o uso para liberar recursos.
     * @return Uma nova instância de EntityManager.
     */
    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    /**
     * Fecha a fábrica de EntityManagers.
     * Este método deve ser chamado apenas uma vez, geralmente ao final da aplicação,
     * para liberar todos os recursos da JPA/Hibernate.
     */
    public static void fechar(){
        // Verifica se a fábrica está aberta antes de tentar fechá-la
        if (emf.isOpen()){
            emf.close();
        }
    }
}