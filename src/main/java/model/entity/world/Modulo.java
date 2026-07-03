package main.java.model.entity.world;

import main.java.model.entity.character.Jogador;

// Representa um módulo da universidade que atua como ponto de passagem
public class Modulo extends Local {

    // Constrói o módulo
    public Modulo(String nome, String descricao) {
        super(nome, descricao);
    }

    // A ação específica do módulo reposiciona o jogador no seu interior
    @Override
    public void acaoEspecifica(Jogador jogador) {
        jogador.setLocalAtual(this);
    }
}