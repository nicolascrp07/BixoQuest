package main.java.model.entity.game;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Jogador;
import main.java.model.entity.world.Universidade;
import main.java.model.entity.event.Evento;
import java.util.ArrayList;
import java.util.UUID;

// Partida em andamento
public class Partida {

    private Jogador jogador;                    // Jogador da partida
    private Tempo tempo;                        // Tempo da partida
    private Universidade universidade;          // Universidade da partida
    private Evento eventoAtual;                 // Evento que está ocorrendo no momento
    private boolean jogoEncerrado;              // Indica se a partida chegou ao fim
    private ArrayList<Evento> eventos;          // Lista de todos os eventos disponíveis na partida
    private ArrayList<Disciplina> gradeCompleta; // Grade curricular completa do curso
    private UUID id;

    // Constrói a partida
    public Partida(Jogador jogador, Tempo tempo, Universidade universidade, Evento eventoAtual, boolean jogoEncerrado, ArrayList<Evento> eventos, ArrayList<Disciplina> gradeCompleta, UUID id) {
        this.jogador = jogador;
        this.tempo = tempo;
        this.universidade = universidade;
        this.eventoAtual = eventoAtual;
        this.jogoEncerrado = jogoEncerrado;
        this.eventos = eventos;
        this.gradeCompleta = gradeCompleta;
        this.id = id;
    }

    // Retorna o jogador
    public Jogador getJogador() { return jogador; }

    // Retorna o tempo
    public Tempo getTempo() { return tempo; }

    // Retorna a universidade
    public Universidade getUniversidade() { return universidade; }

    // Retorna a grade
    public ArrayList<Disciplina> getGradeCompleta() { return new ArrayList<>(this.gradeCompleta); }

    // Retorna se a partida foi encerrada
    public boolean isJogoEncerrado() { return jogoEncerrado; }

    // Encerra a partida
    public void setJogoEncerrado(boolean jogoEncerrado) { this.jogoEncerrado = jogoEncerrado; }

    // Atualiza o evento que está ocorrendo no momento
    public void setEventoAtual(Evento evento) { this.eventoAtual = evento; }

    // Retorna o evento que está ocorrendo no momento
    public Evento getEventoAtual() { return eventoAtual; }

    // Retorna todos os eventos
    public ArrayList<Evento> getEventos() { return new ArrayList<>(this.eventos); }

    // Retorna a identificação do arquivo na persistência
    public UUID getId (){ return id; }
}