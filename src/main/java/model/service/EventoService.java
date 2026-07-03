package main.java.model.service;

import main.java.model.entity.character.Jogador;
import main.java.model.entity.event.Escolha;
import main.java.model.entity.event.Evento;
import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.Local;

import java.util.ArrayList;

// Service responsável por gerar e processar os eventos durante a partida
public class EventoService {

    // Sorteia, entre os eventos cuja condição de ocorrência é satisfeita, aquele que passar no teste de probabilidade
    public Evento gerarEvento(ArrayList<Evento> eventosTotais, Tempo tempo, Local local) {
        for (Evento e : eventosTotais) {
            if (e.condicaoOcorrencia(tempo, local)) {
                if (Math.random() <= e.getProbabilidadeOcorrencia()) {
                    return e;
                }
            }
        }
        return null;
    }

    // Executa a escolha selecionada pelo jogador, aplicando suas consequências
    public void processarEscolha(Jogador jogador, Escolha escolhaClicada) {
        escolhaClicada.executar(jogador);
    }
}