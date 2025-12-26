package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Column; // Importar Column para a anotação unique
import java.util.Date;

/**
 * Usuario
 * * Representa um usuário do sistema.
 * Esta entidade armazena informações de login e perfil do usuário.
 * Mapeada para a tabela "usuario" no banco de dados.
 */
@Entity // Marca esta classe como uma entidade persistente
@Table(name= "usuario") // Mapeia a entidade para a tabela "usuario"
public class Usuario {
    // Identificador único do usuário. Gerado automaticamente.
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estratégia de geração de ID (auto-incremento)
    private Long id; // <<-- MUDANÇA (se ainda não estava): de 'long' para 'Long' para consistência

    // Nome completo do usuário
    private String nome;
    // Endereço de e-mail do usuário. É único no sistema.
    @Column(unique = true) // Garante que o email seja único no banco de dados
    private String email;
    // Hash da senha do usuário (nunca a senha em texto puro)
    private String senha_hash;
    // Data de nascimento do usuário. Mapeado para tipo DATE no banco de dados.
    @Temporal(TemporalType.DATE) // Define como a data será mapeada no banco (apenas a data)
    private Date nascimento;

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public Usuario() {
    }

    /**
     * Construtor completo para criar um objeto Usuario.
     * @param nome O nome completo do usuário.
     * @param email O endereço de e-mail do usuário.
     * @param senha O hash da senha do usuário.
     * @param nascimento A data de nascimento do usuário.
     */
    public Usuario(String nome, String email, String senha, Date nascimento) {
        this.nome = nome;
        this.email = email;
        this.senha_hash = senha;
        this.nascimento = nascimento;
    }

    // --- Getters e Setters ---

    public Long getId() { // <<-- MUDANÇA (se ainda não estava): Retorna Long
        return id;
    }
    
    // Setter de ID: geralmente não se define IDs manualmente para gerados automaticamente
    // Mas se o NetBeans gerou, pode ser mantido. O Hibernate irá ignorá-lo na persistência inicial.
    public void setId(Long id) { // <<-- MUDANÇA (se ainda não estava): Aceita Long
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }
    
    public String getSenha_hash() {
        return senha_hash;
    }

    public void setSenha_hash(String senha_hash) {
        this.senha_hash = senha_hash;
    }
}