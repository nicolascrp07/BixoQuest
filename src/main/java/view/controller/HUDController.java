package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.BixoController;
import main.java.view.observer.AtributosObserver;

import java.util.Objects;

// Gerencia o HUD implementando o padrão Observer
public class HUDController implements AtributosObserver {

    @FXML private ImageView imagePersonagem;
    @FXML private ProgressBar barraEnergia;
    @FXML private ProgressBar barraMotivacao;
    @FXML private ProgressBar barraConhecimento;
    @FXML private ProgressBar barraSaude;

    @FXML private Label labelEnergiaNum;
    @FXML private Label labelConhecimentoNum;
    @FXML private Label labelMotivacaoNum;
    @FXML private Label labelSaudeNum;

    @FXML private Label labelDinheiro;
    @FXML private Label labelDesempenho;
    @FXML private Label labelMaterias;
    @FXML private Label labelMateriasAprovadas;
    @FXML private Label labelSemana;
    @FXML private Label labelSemestre;

    private BixoController bixoController;

    // Conecta o HUD ao observador do controller para receber atualizações automáticas
    public void inicializarHUD(BixoController bc) {
        this.bixoController = bc;
        this.bixoController.registrarObserver(this);
    }

    // Sobrescreve os dados na interface toda vez que o Model notifica uma mudança no jogador
    @Override
    public void atualizarHUD(int energia, int conhecimento, int motivacao, int saude, double dinheiro, double desempenho, int semana, int semestre, int qtdMateriasAtuais, int disciplinasAprovadas, String caminhoIconeAvatar) {

        // Transforma os atributos inteiros na proporção decimal
        barraEnergia.setProgress(energia / 100.0);
        barraConhecimento.setProgress(conhecimento / 100.0);
        barraMotivacao.setProgress(motivacao / 100.0);
        barraSaude.setProgress(saude / 100.0);

        // Preenche os rótulos de valores globais formatando o visual
        labelDinheiro.setText(String.format("R$ %.2f", dinheiro));
        labelDesempenho.setText(String.format("%.1f", desempenho));
        labelMaterias.setText(String.valueOf(qtdMateriasAtuais));
        labelSemana.setText(String.valueOf(semana));
        labelSemestre.setText(String.valueOf(semestre));

        // Atualiza a numeração textual que fica sobreposta às barras
        labelEnergiaNum.setText(energia + "/100");
        labelConhecimentoNum.setText(conhecimento + "/100");
        labelMotivacaoNum.setText(motivacao + "/100");
        labelSaudeNum.setText(saude + "/100");
        labelMateriasAprovadas.setText(disciplinasAprovadas + "/24");

        // Renderiza o retrato do personagem
        if (caminhoIconeAvatar != null && !caminhoIconeAvatar.isEmpty()) {
            try {
                Image imagem = new Image(Objects.requireNonNull(getClass().getResourceAsStream(caminhoIconeAvatar)));
                imagePersonagem.setImage(imagem);
            } catch (Exception e) {
                System.err.println("Erro ao carregar o ícone do avatar:" + caminhoIconeAvatar);
            }
        }
    }

    // Busca e exibe o andamento da missão
    @FXML
    public void botaoVerQuestClicado() {
        String infoQuest = bixoController.getInformacaoQuestAtiva();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Missão Atual", infoQuest);
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