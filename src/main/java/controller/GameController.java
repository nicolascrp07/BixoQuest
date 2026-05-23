package main.java.controller;

import main.java.model.entity.game.Partida;
import main.java.model.service.PartidaService;

import java.util.ArrayList;

// Controller central do jogo
public class GameController {

    private PartidaService partidaService; // Service central da partida

    // Constrói o controller com o service necessário
    public GameController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    // Inicia um novo jogo e retorna a partida criada
    public Partida iniciarJogo(String nomeJogador) {
        return partidaService.iniciarJogo(nomeJogador);
    }

    // Retorna a lista de partidas salvas disponíveis para carregar
    public ArrayList<Partida> carregarJogo() {
        return partidaService.carregarJogo();
    }
}