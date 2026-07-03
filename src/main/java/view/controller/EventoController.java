package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.entity.event.Escolha;
import main.java.model.entity.event.Evento;

import java.util.Objects;

// Controla a renderização visual e as decisões dos eventos aleatórios
public class EventoController implements TelaControlavel {

    @FXML private ImageView imagemDeFundo;
    @FXML private Label tituloEvento;
    @FXML private Label descricaoEvento;
    @FXML private VBox caixaDeEscolhas;

    private BixoController bixoController;

    // Resgata o evento sorteado e prepara a exibição
    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;
        Evento eventoSorteado = bc.getEventoAtual();

        if (eventoSorteado != null) {
            montarCena(eventoSorteado);
        }
    }

    // Preenche os labels de texto e cria botões dinâmicos para cada escolha do evento
    private void montarCena(Evento evento) {
        tituloEvento.setText(evento.getNome().toUpperCase());
        descricaoEvento.setText(evento.getDescricao());

        String nomeLocal = bixoController.getLocalAtual().getNome();
        trocarImagemDeFundo(evento.getNome(), nomeLocal);

        caixaDeEscolhas.getChildren().clear();

        for (Escolha escolha : evento.getEscolhas()) {
            Button btnEscolha = new Button(escolha.getDescricao());

            btnEscolha.setWrapText(true);
            btnEscolha.setMaxWidth(Double.MAX_VALUE);
            btnEscolha.setMinHeight(50);
            btnEscolha.getStyleClass().add("botao-dinamico-retro");

            // Define a consequência e o redirecionamento ao clicar no botão
            btnEscolha.setOnAction(e -> {
                bixoController.processarEscolha(escolha);
                GerenciadorCenas.getInstance().navegarParaLocalAtual();
            });

            caixaDeEscolhas.getChildren().add(btnEscolha);
        }
    }

    // Alterna a imagem de fundo de acordo com o evento
    private void trocarImagemDeFundo(String nomeDoEvento, String nomeDoLocal) {

        String local = nomeDoLocal.toLowerCase();
        boolean isLeds   = local.contains("leds");
        boolean isExatas = local.contains("exatas") || local.contains("algoritmos");

        String caminho = switch (nomeDoEvento.toLowerCase()) {
            case "greve"        -> "/imagens/GREVE.jpeg";
            case "fila gigante" -> "/imagens/FILAGRANDE.jpeg";

            case "prova surpresa" -> isLeds   ? "/imagens/PROVASURPRESALEDS.jpeg"
                    : isExatas ? "/imagens/PROVASURPRESAEXATAS.jpeg"
                      : "/imagens/EVENTOBASE.jpeg";

            case "material caro"  -> isLeds   ? "/imagens/MATERIALCAROLEDS.jpeg"
                    : isExatas ? "/imagens/MATERIALCAROEXATAS.jpeg"
                      : "/imagens/EVENTOBASE.jpeg";

            case "milagre acadêmico" -> isLeds   ? "/imagens/MILAGREACADEMICOLEDS.jpeg"
                    : isExatas ? "/imagens/MILAGREACADEMICOEXATAS.jpeg"
                      : "/imagens/EVENTOBASE.jpeg";
            default -> "/imagens/EVENTOBASE.jpeg";
        };

        try {
            Image novaImagem = new Image(Objects.requireNonNull(getClass().getResourceAsStream(caminho)));
            imagemDeFundo.setImage(novaImagem);
        } catch (Exception e) {
            System.err.println("Imagem não encontrada: " + caminho);
        }
    }
}