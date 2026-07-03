package main.java.model.entity.character;

import main.java.model.entity.world.AmbienteAula;
import main.java.model.entity.world.Local;

import java.util.ArrayList;

// Professor do jogo
public class Professor extends Personagem {

    // Constrói o professor
    public Professor(String nome, Local local, ArrayList<String> dialogos) {
        super(nome, local, dialogos);
    }

    // Professores só podem acessar ambientes de aula
    @Override
    public boolean podeAcessar(Local l) {
        return l instanceof AmbienteAula;
    }

    // Aumenta o nível de conhecimento do jogador ao tirar uma dúvida
    private void tirarDuvida(Jogador jogador) {
        jogador.setNivelConhecimento(jogador.getNivelConhecimento() + 3);
    }

    // A interação específica do professor é responder uma dúvida do jogador
    @Override
    public void interacaoEspecifica(Jogador jogador) {
        this.tirarDuvida(jogador);
    }

    // Define se o professor se move aleatoriamente pelo mapa
    @Override
    public boolean seMoveAleatoriamente(){
        return false;
    }
}