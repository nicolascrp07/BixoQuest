package main.java.view.controller;

import javafx.fxml.FXML;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;

// Lida com a tela exibida quando os atributos do jogador esgotam
public class GameOverController implements TelaControlavel {

    private BixoController bixoController;

    // Associa a referência do controller
    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;
    }

    // Redireciona o jogador para o menu inicial do jogo
    @FXML
    public void botaoIrParaMenuInicialClicado() {
        GerenciadorCenas.getInstance().irParaMenuInicial();
    }

    // Puxa o último save para tentar a rodada novamente
    @FXML
    public void botaoTentarNovamenteClicado() {
        bixoController.recarregarPartidaAtual();
    }
}