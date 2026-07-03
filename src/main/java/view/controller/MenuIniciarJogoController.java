package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.exception.MovimentoInvalidoException;

// Controla a tela de criação do personagem e o início da partida
public class MenuIniciarJogoController implements TelaControlavel {

    @FXML private TextField campoNome;
    private BixoController bixoController;
    private String avatarSelecionado = "";
    private String iconeAvatarSelecionado = "";

    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;
    }

    // Define os caminhos das imagens para o personagem masculino
    @FXML
    public void selecionarAvatarBixo() {
        this.avatarSelecionado = "/imagens/PERSONAGEMMASCULINO.png";
        this.iconeAvatarSelecionado = "/imagens/PERSONAGEMMASCULINOICONE.png";
        System.out.println("Selecionou o Bixo!");
    }

    // Define os caminhos das imagens para a personagem feminina
    @FXML
    public void selecionarAvatarBixa() {
        this.avatarSelecionado = "/imagens/PERSONAGEMFEMININA.png";
        this.iconeAvatarSelecionado = "/imagens/PERSONAGEMFEMININAICONE.png";
        System.out.println("Selecionou a Bixa!");
    }

    // Valida os dados de entrada, instancia o novo jogo no repository e aciona o mapa principal
    @FXML
    public void botaoComecarClicado() {
        String nome = campoNome.getText();

        if (nome == null || nome.trim().isEmpty()) {
            mostrarAlerta("Erro", "Você precisa digitar um nome para o seu calouro!");
            return;
        }
        if (avatarSelecionado.isEmpty()) {
            mostrarAlerta("Erro", "Você precisa clicar em um personagem para escolher seu avatar!");
            return;
        }

        bixoController.iniciarJogo(nome, avatarSelecionado, iconeAvatarSelecionado);

        try {
            bixoController.moverPara("Mapa Principal");
            GerenciadorCenas.getInstance().navegarParaLocalAtual();

        } catch (MovimentoInvalidoException e) {
            System.err.println(e.getMessage());
        }
    }

    // Retorna para o menu inicial do jogo
    @FXML
    public void botaoVoltarClicado() {
        GerenciadorCenas.getInstance().irParaMenuInicial();
    }

    // Exibe caixas de aviso para o usuário
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}