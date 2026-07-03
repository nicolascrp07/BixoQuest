package main.java.model.entity.event;

import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.Local;

import java.util.ArrayList;

// Classe abstrata para todos os eventos que podem ocorrer durante a partida
public abstract class Evento {

    protected String nome;                      // Nome do evento
    protected String descricao;                 // Texto que descreve o evento
    protected double probabilidadeOcorrencia;   // Chance do evento acontecer
    protected ArrayList<Escolha> escolhas;      // Escolhas disponíveis

    // Constrói o evento
    public Evento(String nome, String descricao, double probabilidadeOcorrencia) {
        this.nome = nome;
        this.descricao = descricao;
        this.probabilidadeOcorrencia = probabilidadeOcorrencia;
        this.escolhas = new ArrayList<>();
    }

    // Retorna a probabilidade de ocorrência
    public double getProbabilidadeOcorrencia() { return probabilidadeOcorrencia; }

    // Retorna as escolhas disponíveis
    public ArrayList<Escolha> getEscolhas() { return new ArrayList<>(this.escolhas); }

    // Define a condição de ocorrerência
    public abstract boolean condicaoOcorrencia(Tempo tempo, Local local);

    // Retorna a descrição do evento
    public String getDescricao() { return this.descricao; }

    // Retorna o noem do evento
    public String getNome() { return this.nome;}
}