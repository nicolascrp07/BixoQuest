package main.java.view.controller;

import javafx.fxml.FXML;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.exception.MovimentoInvalidoException;

// Controla as opções de navegação a partir do Módulo Auxiliar
public class MapaModuloAuxiliarController implements TelaControlavel {

    @FXML private HUDController meuHudController;
    private BixoController bixoController;

    // Inicializa a conexão com o controller e renderiza o HUD
    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;
        if (meuHudController != null) meuHudController.inicializarHUD(bc);
    }

    // Desloca o jogador para a Cantina
    @FXML
    public void irParaCantina() {
        try {
            bixoController.moverPara("Cantina");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Desloca o jogador para o Ponto de Ônibus
    @FXML
    public void irParaPontoDeOnibus() {
        try {
            bixoController.moverPara("Ponto de Ônibus");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Retorna o jogador ao Mapa Principal
    @FXML
    public void irParaMapaPrincipal() {
        try {
            bixoController.moverPara("Mapa Principal");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }
}