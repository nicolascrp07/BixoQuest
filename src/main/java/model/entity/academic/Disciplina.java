package main.java.model.entity.academic;

import main.java.model.entity.character.Professor;

// Disciplina cursada pelo jogador
public class Disciplina {

    private String nome;                // Nome da disciplina
    private String area;                // Área do conhecimento
    private Professor professor;        // Professor responsável pela disciplina
    private Disciplina preRequisito;    // Disciplina que deve ser concluída antes desta
    private double notaFinal;           // Nota final obtida
    private int cargaHoraria;           // Carga horária total
    private boolean statusAprovacao;    // Indica se o jogador foi aprovado
    private Avaliacao avaliacao;        // Avaliação vinculada

    // Constrói a disciplina
    public Disciplina(String nome, String area, Professor professor, Disciplina preRequisito, double notaFinal, int cargaHoraria, boolean statusAprovacao, Avaliacao avaliacao) {
        this.nome = nome;
        this.area = area;
        this.professor = professor;
        this.preRequisito = preRequisito;
        this.notaFinal = notaFinal;
        this.cargaHoraria = cargaHoraria;
        this.statusAprovacao = statusAprovacao;
        this.avaliacao = avaliacao;
    }

    // Retorna se o jogador foi aprovado na disciplina
    public boolean getStatusAprovacao() {
        return statusAprovacao;
    }

    // Aprova o jogador se a nota final for 7 ou mais / Reprova caso contrário
    public void verificarAprovacao() {
        if (notaFinal < 7) {
            this.statusAprovacao = false;
        } else {
            this.statusAprovacao = true;
        }
    }

    // Atualiza a nota final
    public void setNotaFinal(double nota) {
        this.notaFinal = nota;
    }

    // Retorna a nota final
    public double getNotaFinal() {
        return notaFinal;
    }

    // Retorna a área do conhecimento da disciplina
    public String getArea() {
        return area;
    }

    // Retorna o nome da disciplina
    public String getNome() {
        return nome;
    }

    // Retorna o pré-requisito exigido desta disciplina
    public Disciplina getPreRequisito() {
        return preRequisito;
    }

    // Retorna o professor da disciplina
    public Professor getProfessor() {
        return professor;
    }

    // Retorna a avaliação da disciplina
    public Avaliacao getAvaliacao() { return this.avaliacao; }
}