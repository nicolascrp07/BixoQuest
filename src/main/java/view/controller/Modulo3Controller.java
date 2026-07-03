package main.java.view.controller;

import javafx.fxml.FXML;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.exception.MovimentoInvalidoException;

// Controla as opções de navegação e rotas de deslocamento a partir do Módulo 3
public class Modulo3Controller implements TelaControlavel {
    @FXML
    private HUDController meuHudController;

    private BixoController bixoController;

    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;

        if (meuHudController != null) {
            meuHudController.inicializarHUD(bc);
        }
    }

    // Tenta mover o personagem para o laboratório LEDS e solicita a mudança de tela correspondente
    @FXML
    public void irParaLEDS() {
        try {
            bixoController.moverPara("LEDS");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Tenta mover o personagem para o Colegiado e solicita a mudança de tela correspondente
    @FXML
    public void irParaColegiado() {
        try {
            bixoController.moverPara("Colegiado");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Tenta mover o personagem de volta para o mapa principal e solicita a mudança de tela
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