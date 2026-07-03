package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.entity.game.Partida;

import java.util.ArrayList;

// Controla a interface de listagem e carregamento de jogos salvos
public class MenuCarregarJogoController implements TelaControlavel {

    @FXML private VBox vboxSaves;

    private BixoController bixoController;

    // Vincula o controller e aciona a renderização da lista de saves
    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;
        carregarListaDeSavesNaTela();
    }

    // Busca os saves persistidos no repository e cria dinamicamente os botões na tela
    private void carregarListaDeSavesNaTela() {
        vboxSaves.getChildren().clear();

        ArrayList<Partida> saves = bixoController.carregarJogo();

        // Trata o cenário do repository vazio
        if (saves == null || saves.isEmpty()) {
            Button btnVazio = new Button("Nenhum jogo salvo encontrado.");
            btnVazio.setDisable(true);
            btnVazio.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial';");
            vboxSaves.getChildren().add(btnVazio);
            return;
        }

        // Instancia os botões para cada save retornado
        for (Partida save : saves) {

            String textoSave = String.format("%s - Semestre %d, Semana %d",
                    save.getJogador().getNome(),
                    save.getTempo().getSemestreAtual(),
                    save.getTempo().getSemanaAtual());

            Button btnSave = new Button(textoSave);

            btnSave.setMaxWidth(Double.MAX_VALUE);
            btnSave.setMinHeight(50);
            btnSave.setWrapText(true);
            btnSave.getStyleClass().add("botao-dinamico-retro");

            btnSave.setOnAction(event -> {
                carregarPartidaSelecionada(save);
            });

            vboxSaves.getChildren().add(btnSave);
        }
    }

    // Sobrescreve o estado atual do jogo com o save escolhido e redireciona o jogador
    private void carregarPartidaSelecionada(Partida save) {
        bixoController.selecionarSave(save);
        GerenciadorCenas.getInstance().navegarParaLocalAtual();
    }

    // Retorna para o menu inicial do jogo
    @FXML
    public void botaoVoltarClicado() {
        GerenciadorCenas.getInstance().irParaMenuInicial();
    }
}