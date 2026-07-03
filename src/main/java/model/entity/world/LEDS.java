package main.java.model.entity.world;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Jogador;

// Laboratório LEDS
public class LEDS extends AmbienteAula {

    // Constrói o LEDS
    public LEDS(String nome, String descricao, Disciplina disciplinaAtual) {
        super(nome, descricao, disciplinaAtual);
    }

    @Override
    public void acaoEspecifica(Jogador j) {
        this.realizarAula(j);
    }
}