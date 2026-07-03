package main.java.model.entity.world;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Jogador;

// Representa os ambientes acadêmicos na universidade
public abstract class AmbienteAula extends Local {
    protected Disciplina disciplinaAtual;  // Disciplina atual do ambiente

    // Constrói o ambiente
    public AmbienteAula(String nome, String descricao, Disciplina disciplinaAtual) {
        super(nome, descricao);
        this.disciplinaAtual = disciplinaAtual;
    }

    public Disciplina getDisciplinaAtual() { return disciplinaAtual; }
    public void setDisciplinaAtual(Disciplina d) { this.disciplinaAtual = d; }

    // Define como comportamento padrão a realização de uma aula
    protected void realizarAula(Jogador jogador) {
        jogador.setNivelConhecimento(jogador.getNivelConhecimento() + 10);
        jogador.setEnergia(jogador.getEnergia() - 10);
    }
}
