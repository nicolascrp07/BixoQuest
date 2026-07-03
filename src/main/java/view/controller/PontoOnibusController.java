package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.entity.world.Local;
import main.java.model.exception.InteracaoEsgotadaException;
import main.java.model.exception.MovimentoInvalidoException;

// Controla a mecânica de transição de turno a partir do Ponto de Ônibus
public class PontoOnibusController implements TelaControlavel {

    @FXML private HUDController meuHudController;

    private BixoController bixoController;

    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;
        if (meuHudController != null) {
            meuHudController.inicializarHUD(bc);
        }
    }

    // Processa a virada da semana e reposiciona o jogador no mapa
    @FXML
    public void botaoViajarClicado() {
        try {
            Local ponto = buscarPontoNoModel();
            boolean conseguiuViajar = bixoController.executarAcao(ponto);

            if (conseguiuViajar) {
                bixoController.avancarSemana();
                mostrarAlerta(Alert.AlertType.INFORMATION,"Semana Passou","Você pegou o ônibus e voltou para casa.\nUma nova semana começou!");
                GerenciadorCenas.getInstance().navegarParaLocalAtual();
            }
        } catch (InteracaoEsgotadaException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", e.getMessage());
        }
    }

    // Redireciona o personagem de volta para o Módulo Auxiliar
    @FXML
    public void irParaModuloAuxiliar() {
        try {
            bixoController.moverPara("Módulo Auxiliar");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Busca a referência do local Ponto de Ônibus
    private Local buscarPontoNoModel() {
        return bixoController.getLocalPorNome("Ponto de Ônibus");
    }

    // Exibe caixas de aviso para o usuário
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}