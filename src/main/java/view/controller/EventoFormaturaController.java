package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;

import java.util.Objects;

// Controla a tela final de vitória quando o jogador conclui o curso
public class EventoFormaturaController implements TelaControlavel {

    @FXML private ImageView imagemAvatarJogador;
    private BixoController bixoController;

    // Vincula o Controller e injeta a foto selecionada pelo jogador na criação
    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;
        carregarAvatar();
    }

    // Busca o avatar associado ao jogador e encaixa na moldura do diploma
    private void carregarAvatar() {
        String caminhoImagem = bixoController.getCaminhoIconeAvatar();

        if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
            try {
                Image imagem = new Image(Objects.requireNonNull(getClass().getResourceAsStream(caminhoImagem)));
                imagemAvatarJogador.setImage(imagem);
            } catch (Exception e) {
                System.err.println("Não foi possível carregar a imagem do avatar: " + caminhoImagem);
            }
        }
    }

    // Apaga os registros da partida concluída e volta à tela principal do jogo
    @FXML
    public void botaoIrParaMenuInicialClicado() {
        bixoController.deletarJogo();
        GerenciadorCenas.getInstance().irParaMenuInicial();
    }
}