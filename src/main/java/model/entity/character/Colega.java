package main.java.model.entity.character;

import main.java.model.entity.world.Local;

import java.util.ArrayList;

// Colega de curso
public class Colega extends Personagem {

    // Constrói o colega
    public Colega(String nome, Local local, ArrayList<String> dialogos) {
        super(nome, local, dialogos);
    }

    // Colegas podem circular livremente por qualquer local do jogo
    @Override
    public boolean podeAcessar(Local l) {
        return true;
    }

    // Aumenta levemente a motivação do jogador ao compartilhar uma fofoca
    private void fofocaAlheia(Jogador jogador) {
        jogador.setMotivacao(jogador.getMotivacao() + 5);
    }

    // A interação específica do colega é contar uma fofoca ao jogador
    @Override
    public void interacaoEspecifica(Jogador jogador) {
        this.fofocaAlheia(jogador);
    }

    // Define se o colega se move aleatoriamente pelo mapa
    @Override
    public boolean seMoveAleatoriamente(){
        return true;
    }
}