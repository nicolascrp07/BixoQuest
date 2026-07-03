package main.java.view.controller;

import javafx.fxml.FXML;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.exception.MovimentoInvalidoException;

// Controla as opções de navegação e rotas de deslocamento a partir do Módulo 5
public class Modulo5Controller implements TelaControlavel {
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

    // Tenta mover o personagem para a Sala de Exatas e solicita a mudança de tela correspondente
    @FXML
    public void irParaSalaExatas() {
        try {
            bixoController.moverPara("Sala de Exatas");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Tenta mover o personagem para a Sala de Algoritmos e solicita a mudança de tela correspondente
    @FXML
    public void irParaSalaAlgoritmos() {
        try {
            bixoController.moverPara("Sala de Algoritmos");
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