package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.java.controller.BixoController;
import main.java.model.entity.character.Personagem;

// Gerencia a caixa flutuante de diálogo e as opções de interação com os NPCs na tela
public class PainelNPCController {

    @FXML private Pane painelRaiz;
    @FXML private Label labelNome;
    @FXML private Label labelDialogo;
    @FXML private Button btnAceitarQuest;
    @FXML private Button btnInteragir;

    private BixoController bixoController;
    private Personagem npcAtual;

    // Vincula o painel ao controller e o oculta da interface inicial
    public void inicializarPainel(BixoController bc) {
        this.bixoController = bc;
        this.painelRaiz.setVisible(false);
    }

    // Injeta os dados do personagem fornecido e torna a caixa de diálogo visível
    public void exibirPainel(Personagem npc) {
        this.npcAtual = npc;
        labelNome.setText(npc.getNome());
        labelDialogo.setText(bixoController.dialogoNPC(npc));
        painelRaiz.setVisible(true);
    }

    // Tenta validar e vincular uma missão para o jogador
    @FXML
    public void btnAceitarQuestClicado() {
        if (npcAtual != null) {
            try {
                bixoController.gerarQuestParaJogador();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Missão Aceita!", "Verifique o seu HUD para ver o novo objetivo.");
                painelRaiz.setVisible(false);
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.WARNING, "Calma lá, Bixo!", e.getMessage());
            }
        }
    }

    // Gerencia os ganhos de atributo gerados pela interação com o NPC
    @FXML
    public void btnInteragirClicado() {
        if (npcAtual != null) {
            try {
                bixoController.interacaoNPC(npcAtual);

                mostrarAlerta(Alert.AlertType.INFORMATION, "Interação", "Você conversou com " + npcAtual.getNome() + " e seus atributos mudaram!");
                painelRaiz.setVisible(false);
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.WARNING, "Interação Esgotada", e.getMessage());
            }
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