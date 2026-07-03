package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.world.LEDS;
import main.java.model.entity.world.Local;
import main.java.model.entity.world.Sala;
import main.java.model.exception.InteracaoEsgotadaException;
import main.java.model.exception.MovimentoInvalidoException;

import java.util.Objects;

// Controla as mecânicas das salas de aula
public class SalaController implements TelaControlavel {

    @FXML private HUDController meuHudController;
    @FXML private ImageView imagemDeFundo;
    @FXML private Label labelNomeLocal;
    @FXML private Label labelDisciplina;
    @FXML private Label labelProfessor;
    @FXML private BotaoNPCController botaoNpcComponenteController;
    @FXML private Button btnAssistirAula;

    private BixoController bixoController;
    private Local ambienteAtual;

    // Inicializa a integração com o controller, valida o conteúdo do ambiente e formata os textos
    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;

        if (meuHudController != null) meuHudController.inicializarHUD(bc);

        if (botaoNpcComponenteController != null) {
            botaoNpcComponenteController.inicializarBotao(bc, bc.getLocalAtual().getNome());
        }

        this.ambienteAtual = bc.getLocalAtual();
        montarCenaDinamica();

        // Converte visualmente o botão da sala para realizar a prova na quarta semana
        if (bixoController.getSemanaAtual() == 4) {
            btnAssistirAula.setText("FAZER PROVA");
        }
    }

    // Aplica as descrições da matéria vigente e substitui o fundo dinamicamente
    private void montarCenaDinamica() {
        labelNomeLocal.setText(ambienteAtual.getNome().toUpperCase());
        trocarImagemDeFundo(ambienteAtual.getNome());

        Disciplina disciplina = null;

        if (ambienteAtual instanceof Sala s) {
            disciplina = s.getDisciplinaAtual();
        } else if (ambienteAtual instanceof LEDS l) {
            disciplina = l.getDisciplinaAtual();
        }

        if (disciplina != null) {
            labelDisciplina.setText("MÁTERIA: " + disciplina.getNome());
            labelProfessor.setText("PROFESSOR: " + disciplina.getProfessor().getNome());
        }
    }

    // Processa a aula, bloqueia repetições ou redireciona para a avaliação
    @FXML
    public void botaoAssistirAulaClicado() {
        try {
            if (ambienteAtual != null && ambienteAtual.isInteragiu()) {
                throw new InteracaoEsgotadaException("Você já concluiu suas atividades nesta sala por hoje!");
            }

            if (bixoController.getSemanaAtual() == 4) {
                GerenciadorCenas.getInstance().irParaMinigame();
            } else {
                bixoController.executarAcao(ambienteAtual);
                if (bixoController.getEventoAtual() == null) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Aula Concluída", "Você ganhou Conhecimento e perdeu Energia.");
                }
                GerenciadorCenas.getInstance().navegarParaLocalAtual();
            }
        } catch (InteracaoEsgotadaException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", e.getMessage());
        }
    }

    // Identifica o destino adjacente e repassa a instrução de saída ao gerenciador e ao controller
    @FXML
    public void botaoSairDaSalaClicado() {
        try {
            if (ambienteAtual != null && !ambienteAtual.getConexoes().isEmpty()) {
                String nomeDestino = ambienteAtual.getConexoes().get(1).getNome();
                bixoController.moverPara(nomeDestino);
            } else {
                bixoController.moverPara("Mapa Principal");
            }

            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Identifica a instância da sala e carrega a imagem de fundo
    private void trocarImagemDeFundo(String nomeLocal) {
        String caminho = nomeLocal.equalsIgnoreCase("LEDS") ? "/imagens/LEDS.jpeg" : "/imagens/SALAEXATAS.jpeg";

        try {
            Image novaImagem = new Image(Objects.requireNonNull(getClass().getResourceAsStream(caminho)));
            imagemDeFundo.setImage(novaImagem);
        } catch (Exception e) {
            System.err.println("Imagem da sala não encontrada: " + caminho);
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