package main.java.view.controller;

import javafx.fxml.FXML;
import main.java.controller.GerenciadorCenas;

// Controla as opções do menu de entrada principal do jogo
public class MenuInicialController {

    // Redireciona o jogador para a tela de criação de um novo save
    @FXML
    public void botaoIniciarClicado() {
        GerenciadorCenas.getInstance().irParaMenuIniciarJogo();
    }

    // Redireciona o jogador para a tela de seleção de saves existentes
    @FXML
    public void botaoCarregarClicado() {
        GerenciadorCenas.getInstance().irParaMenuCarregarJogo();
    }
}