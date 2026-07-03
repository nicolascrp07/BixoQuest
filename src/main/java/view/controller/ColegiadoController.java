package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.entity.world.Local;
import main.java.model.exception.InteracaoEsgotadaException;
import main.java.model.exception.MovimentoInvalidoException;

// Controla a lógica de interface e as interações na tela do Colegiado
public class ColegiadoController implements TelaControlavel {

    @FXML private HUDController meuHudController;
    @FXML private BotaoNPCController botaoNpcComponenteController;
    private BixoController bixoController;

    // Injeta as dependências globais e inicializa os componentes da tela
    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;
        if (meuHudController != null) meuHudController.inicializarHUD(bc);
        if (botaoNpcComponenteController != null) {
            botaoNpcComponenteController.inicializarBotao(bc, bc.getLocalAtual().getNome());
        }
    }

    // Processa o pedido de ajuda no Colegiado e aplica a recuperação de atributos
    @FXML
    public void botaoPedirAjudaClicado() {
        try {
            Local colegiado = bixoController.getLocalPorNome("Colegiado");
            bixoController.executarAcao(colegiado);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Ajuda Recebida!", "Maeli te deu uma luz e você já se sente mais revigorado!");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (InteracaoEsgotadaException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atendimento Fechado", e.getMessage());
        }
    }

    // Redireciona o jogador de volta para o Módulo 3
    @FXML
    public void botaoSairDoColegiadoClicado() {
        try {
            bixoController.moverPara("Módulo 3");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
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