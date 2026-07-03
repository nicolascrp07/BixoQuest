package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.entity.world.Local;
import main.java.model.exception.InteracaoEsgotadaException;
import main.java.model.exception.MovimentoInvalidoException;
import main.java.model.exception.SaldoInsuficienteException;

// Controla a lógica de interface e as interações na tela da Cantina
public class CantinaController implements TelaControlavel {

    @FXML private HUDController meuHudController;
    @FXML private BotaoNPCController botaoNpcComponenteController;

    private BixoController bixoController;

    // Injeta as dependências globais e inicializa os componentes da tela
    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;

        if (meuHudController != null) {
            meuHudController.inicializarHUD(bc);
        }

        if (botaoNpcComponenteController != null) {
            botaoNpcComponenteController.inicializarBotao(bc, "Cantina");
        }
    }

    // Processa a tentativa de compra de lanche e notifica o resultado
    @FXML
    public void botaoComprarLancheClicado() {
        try {
            Local cantina = buscarCantinaNoModel();
            bixoController.executarAcao(cantina);
            mostrarAlerta(Alert.AlertType.INFORMATION,"Lanche comprado","Você comprou um lanche e sente a larica diminuir.");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();

        } catch (InteracaoEsgotadaException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sem fome!", e.getMessage());
        } catch (SaldoInsuficienteException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Tá duro dorme!", e.getMessage());
        }
    }

    // Redireciona o jogador de volta para o Módulo Auxiliar
    @FXML
    public void irParaModuloAuxiliar() {
        try {
            bixoController.moverPara("Módulo Auxiliar");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Busca a referência do local Cantina
    private Local buscarCantinaNoModel() {
        return bixoController.getLocalPorNome("Cantina");
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