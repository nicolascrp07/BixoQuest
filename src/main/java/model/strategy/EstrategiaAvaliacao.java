package main.java.model.strategy;

import main.java.model.entity.character.Jogador;

// Interface do padrão Strategy: define o contrato para o cálculo de nota das provas
public interface EstrategiaAvaliacao {
    double calcularNota(int acertos, int totalPerguntas, Jogador jogador, int dificuldade);
}