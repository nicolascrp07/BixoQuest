package main.java.model.entity.world;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Jogador;

// Sala de aula
public class Sala extends AmbienteAula {

    // Constrói a sala
    public Sala(String nome, String descricao, Disciplina disciplinaAtual) {
        super(nome, descricao, disciplinaAtual);
    }

    // A ação específica da sala é assistir uma aula teórica
    @Override
    public void acaoEspecifica(Jogador j) {
        this.realizarAula(j);
    }

}