package main.java.model.entity.academic;

// Avaliação acadêmica do jogo, com nome, dificuldade e nota
public class Avaliacao {

    private String nome;        // Nome da avaliação
    private int dificuldade;    // Grau de dificuldade
    private double nota;        // Nota registrada na avaliação

    // Constrói a avaliação
    public Avaliacao(String nome, int dificuldade, double nota) {
        this.nome = nome;
        this.dificuldade = dificuldade;
        this.nota = nota;
    }

    // Retorna a nota atual
    public double getNota() {
        return nota;
    }

    // Retorna o grau de dificuldade
    public int getDificuldade() {
        return dificuldade;
    }

    // Atualiza a nota com o resultado obtido no minigame
    public void setNota(double notaDoMinigame) {
        this.nota = notaDoMinigame;
    }

    // Atualiza o grau de dificuldade
    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }
}