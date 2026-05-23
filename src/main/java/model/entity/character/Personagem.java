package main.java.model.entity.character;

import main.java.model.entity.event.Quest;
import main.java.model.entity.event.Recompensa; // import não utilizado, pode ser removido
import main.java.model.entity.world.Local;
import java.util.ArrayList;

// Classe abstrata para todos os personagens do jogo
public abstract class Personagem {

    protected String nome;                          // Nome do personagem
    protected Local local;                          // Local onde o personagem se encontra
    protected ArrayList<String> dialogos;

    // Constrói o personagem
    public Personagem(String nome, Local local, ArrayList<String> dialogos) {
        this.nome = nome;
        this.local = local;
        this.dialogos = dialogos;
    }

    // Atualiza o local do personagem
    public void setLocal(Local local) { this.local = local; }

    // Retorna o local do personagem
    public Local getLocalAtual() { return local; }

    // Retorna o diálogo
    public String getDialogo() {
        int indice = (int) (Math.random() * dialogos.size());
        return dialogos.get(indice);
    }

    // Retorna o nome do personagem
    public String getNome() { return nome; }

    // Define se o personagem pode acessar determinado local
    public abstract boolean podeAcessar(Local local);

    // Define o comportamento específico da interação com o jogador
    public abstract void interacaoEspecifica(Jogador jogador);
}