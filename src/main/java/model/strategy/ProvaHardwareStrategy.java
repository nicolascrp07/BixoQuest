package main.java.model.strategy;
import main.java.model.entity.character.Jogador;

// Estratégia de avaliação para disciplinas de Hardware: bônus concedido pela energia do jogador
public class ProvaHardwareStrategy implements EstrategiaAvaliacao {
    @Override
    public double calcularNota(int acertos, int totalPerguntas, Jogador jogador, int dificuldade) {
        double notaBase = ((double) acertos / totalPerguntas) * 10.0;
        double bonus = (jogador.getEnergia() > 70) ? 1.0 : 0.0;
        double notaFinal = notaBase + bonus - (dificuldade * 0.5);
        return Math.clamp(notaFinal, 0, 10);
    }
}