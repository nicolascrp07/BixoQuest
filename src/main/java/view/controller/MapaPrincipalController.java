package main.java.view.controller;

import javafx.fxml.FXML;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.exception.MovimentoInvalidoException;

// Controla as opções de deslocamento a partir do Mapa Principal da universidade
public class MapaPrincipalController implements TelaControlavel {

    @FXML private HUDController meuHudController;

    private BixoController bixoController;

    // Aplica a injeção do controller e inicializa o HUD
    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;

        if (meuHudController != null) {
            meuHudController.inicializarHUD(bc);
        }
    }

    // Tenta mover o personagem para o Módulo 3 e solicita a mudança de tela correspondente
    @FXML
    public void irParaModulo3() {
        try {
            bixoController.moverPara("Módulo 3");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Tenta mover o personagem para o Módulo 5 e solicita a mudança de tela correspondente
    @FXML
    public void irParaModulo5() {
        try {
            bixoController.moverPara("Módulo 5");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Tenta mover o personagem para o Módulo Auxiliar e solicita a mudança de tela correspondente
    @FXML
    public void irParaModuloAuxiliar() {
        try {
            bixoController.moverPara("Módulo Auxiliar");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }
}