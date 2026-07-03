package main.java.model.repository;

import main.java.model.entity.event.*;

import java.util.ArrayList;

// Repositório responsável por armazenar e gerenciar os eventos da partida
public class EventoRepository {

    private ArrayList<Evento> eb = new ArrayList<>(); // Base de dados dos eventos

    // Salva o evento se ele ainda não estiver cadastrado | Retorna null caso contrário
    public Evento salvar(Evento evento) {
        if (!eb.contains(evento)) {
            eb.add(evento);
            return evento;
        }
        return null;
    }

    // Retorna uma cópia da lista com todos os eventos cadastrados
    public ArrayList<Evento> buscarTodos() {
        return new ArrayList<>(eb);
    }

    // Instancia e salva todos os eventos do jogo
    public void criarEventosPadrao() {

        if (!eb.isEmpty()) return;

        this.salvar(new ProvaSurpresa());
        this.salvar(new FilaGigante());
        this.salvar(new Greve());
        this.salvar(new MaterialCaro());
        this.salvar(new MilagreAcademico());
    }
}