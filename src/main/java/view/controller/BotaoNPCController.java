package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.BixoController;
import main.java.model.entity.character.Personagem;
import java.util.ArrayList;
import java.util.Objects;

// Controla o botão visual do NPC e seu painel de interação correspondente
public class BotaoNPCController {

    @FXML private Button btnFotoNpc;
    @FXML private ImageView imagemNpc;
    @FXML private PainelNPCController meuPainelController;
    private Personagem npcAtual;

    // Vincula o tamanho da imagem do NPC ao tamanho do botão no FXML
    @FXML
    private void initialize() {
        imagemNpc.fitWidthProperty().bind(btnFotoNpc.prefWidthProperty());
        imagemNpc.fitHeightProperty().bind(btnFotoNpc.prefHeightProperty());
    }

    // Configura os dados do NPC presente no local e ajusta a visibilidade do botão
    public void inicializarBotao(BixoController bc, String nomeLocal) {
        if (meuPainelController != null) {
            meuPainelController.inicializarPainel(bc);
        }

        ArrayList<Personagem> npcsAqui = bc.getPersonagensNoLocal(nomeLocal);

        if (!npcsAqui.isEmpty()) {
            this.npcAtual = npcsAqui.get(0);
            configurarImagemBotao(npcAtual.getNome());
            btnFotoNpc.setVisible(true);
            btnFotoNpc.setManaged(true);
        } else {
            btnFotoNpc.setVisible(false);
            btnFotoNpc.setManaged(false);
        }
    }

    // Aciona a exibição do painel de conversa quando o botão é clicado
    @FXML
    public void botaoFotoClicado() {
        if (npcAtual != null && meuPainelController != null) {
            meuPainelController.exibirPainel(npcAtual);
        }
    }

    // Mapeia o nome do NPC para o seu respectivo arquivo de imagem
    private void configurarImagemBotao(String nomeNpc) {
        String caminhoImg = switch (nomeNpc.toLowerCase()) {
            case "felícia"                          -> "/imagens/FELICIA.png";
            case "scooby"                           -> "/imagens/SCOOBYNPC.png";
            case "arthur"                           -> "/imagens/ARTHURNPC.png";
            case "bella"                            -> "/imagens/BELLANPC.png";
            case "claudênia plinda"                 -> "/imagens/CLAUDENIA.png";
            case "pamelinda cortizona"              -> "/imagens/PAMELINDA.png";
            case "biancarlota santalinda"           -> "/imagens/BIANCARLOTA.png";
            case "gabriela peixolinda"              -> "/imagens/GABRIELA.png";
            case "anfransérgio diastronho"          -> "/imagens/ANFRANSERGIO.png";
            case "joãoberto boscolino"              -> "/imagens/JOAOBERTO.png";
            case "delmarvilho brogliovski"          -> "/imagens/DELMARVILHO.png";
            case "ângelo duartênis"                 -> "/imagens/ANGELO.png";
            case "jaquelândia sintrônica"           -> "/imagens/JAQUELANDIA.png";
            case "geraldoncio assislânio"           -> "/imagens/GERALDONCIO.png";
            case "cristianópolis mascarenhudo"      -> "/imagens/CRISTIANOPOLIS.png";
            case "ademaksonildo araujástico"        -> "/imagens/ADEMAKSONILDO.png";
            default                                 -> "/imagens/NPCPADRAO.png";
        };
        try {
            imagemNpc.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(caminhoImg))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}