package main.java.model.strategy;
import main.java.model.entity.character.Jogador;

// Estratégia de avaliação para disciplinas de Algoritmos: bônus concedido pela motivação do jogador
public class ProvaAlgoritmosStrategy implements EstrategiaAvaliacao {
    @Override
    public double calcularNota(int acertos, int totalPerguntas, Jogador jogador, int dificuldade) {
        double notaBase = ((double) acertos / totalPerguntas) * 10.0;
        double bonus = (jogador.getMotivacao() > 70) ? 1.0 : 0.0;
        double notaFinal = notaBase + bonus - (dificuldade * 0.5);
        return Math.clamp(notaFinal, 0, 10);
    }
}