package util;

import model.Usuario; // Importa a classe Usuario

/**
 * UserSession
 * * Classe para gerenciar a sessão do usuário logado na aplicação.
 * Implementa o padrão Singleton para garantir que exista apenas uma sessão ativa
 * por vez, armazenando o usuário logado.
 */
public class UserSession {

    // Instância única do Singleton UserSession.
    private static UserSession instance;
    // Objeto Usuario que representa o usuário logado na sessão atual.
    private final Usuario usuarioLogado;

    /**
     * Construtor privado para criar uma nova sessão com um usuário específico.
     * Restringe a criação de instâncias fora desta classe, seguindo o padrão Singleton.
     * @param usuario O objeto Usuario que será associado a esta sessão.
     */
    private UserSession(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    /**
     * Inicia uma nova sessão de usuário ou sobrescreve a sessão existente.
     * Este é o método a ser chamado após um login bem-sucedido.
     * @param usuario O objeto Usuario que está se autenticando e iniciando a sessão.
     */
    public static void iniciarSessao(Usuario usuario) {
        instance = new UserSession(usuario);
    }

    /**
     * Retorna a instância única da sessão de usuário ativa.
     * @return A instância de UserSession, ou null se nenhuma sessão estiver ativa.
     */
    public static UserSession getInstance() {
        return instance;
    }

    /**
     * Obtém o objeto Usuario que está logado na sessão atual.
     * @return O objeto Usuario logado, ou null se não houver usuário na sessão.
     */
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Encerra a sessão atual do usuário, limpando os dados do usuário logado.
     * Ocorre geralmente ao fazer logout.
     */
    public static void encerrarSessao() {
        instance = null;
    }

    /**
     * Verifica se há uma sessão de usuário ativa no momento.
     * @return true se uma sessão estiver ativa (existe uma instância de UserSession), false caso contrário.
     */
    public static boolean isAtiva() {
        return instance != null;
    }

    /**
     * Obtém diretamente o objeto Usuario logado, se a sessão estiver ativa.
     * Método de conveniência para acessar o usuário logado.
     * @return O objeto Usuario logado, ou null se não houver sessão ativa.
     */
    public static Usuario getUsuario() {
        return instance != null ? instance.getUsuarioLogado() : null;
    }
}