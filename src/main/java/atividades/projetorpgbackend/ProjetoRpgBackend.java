package atividades.projetorpgbackend;

import dao.*;
import model.*;
import util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ProjetoRpgBackend {

    public static void main(String[] args) {
        //Rodando um teste básico para conferir se está tudo funcionando ainda
        
        EntityManager em = JpaUtil.getEntityManager();
        
        // Instanciando os DAOs
        UsuarioDao usuarioDao = new UsuarioDao(em);
        SistemaDao sistemaDao = new SistemaDao(em);
        ItemDao itemDao = new ItemDao(em);
        try {
            System.out.println("=== INICIANDO TESTES DO BACKEND ===");

            //Criar e Salvar um Sistema 
            Sistema sistema = new Sistema();
            sistema.setNome("D&D 5ª Edição");
            sistema.setRaridadesJson("[\"Comum\", \"Incomum\", \"Raro\", \"Lendário\"]");
            sistemaDao.salvar(sistema);
            System.out.println("[OK] Sistema salvo: " + sistema.getNome());

            //Criar e Salvar um Usuário (Autor)
            Usuario autor = new Usuario("Mestre dos Magos", "mestre@teste.com", "123456", new Date());
            // Verifica se já existe
            if(usuarioDao.buscarPorEmail(autor.getEmail()) == null){
                usuarioDao.salvar(autor);
                System.out.println("[OK] Usuário salvo: " + autor.getNome());
            } else {
                autor = usuarioDao.buscarPorEmail(autor.getEmail());
                System.out.println("[INFO] Usuário já existia, usando o existente.");
            }

            //Criar Bônus (Teste do JSON)
            List<Bonus> listaBonus = new ArrayList<>();
            listaBonus.add(new Bonus("Atributo", "Força", "+2"));
            listaBonus.add(new Bonus("Dano", "Fogo", "1d6"));

            //Criar o ITEM (Teste da Herança Conteudo -> Item)
            Item espada = new Item();
            espada.setTitulo("Espada Flamejante");
            espada.setDescricao("Uma espada que queima os inimigos.");
            espada.setDataCriacao(new Date());
            espada.setDataUpdate(new Date());
            espada.setRaridade("Raro");
            espada.setTipo("Arma");
            espada.setAutor(autor);   // Relacionamento com Usuario
            espada.setSistema(sistema); // Relacionamento com Sistema
            espada.setBonus(listaBonus); // Relacionamento com JSON/ElementCollection
            espada.setTags(Arrays.asList("fogo", "magico", "dano")); // Teste do TagListConverter

            // Se a herança estiver certa, isso vai salvar na tabela 'conteudo' E na tabela 'item'
            itemDao.salvar(espada); 
            System.out.println("[OK] Item salvo com sucesso! ID: " + espada.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            JpaUtil.fechar();
        }
    }
}