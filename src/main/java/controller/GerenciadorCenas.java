package main.java.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.view.controller.TelaControlavel;

import java.io.IOException;

// Centraliza a navegação de FXMLs e injeta a dependência do Controller nas Views
public class GerenciadorCenas {

    private static GerenciadorCenas instance;
    private Stage palco;
    private BixoController bc;

    private GerenciadorCenas() {}

    // Retorna a instância única do Gerenciador de Cenas (Padrão Singleton)
    public static GerenciadorCenas getInstance() {
        if (instance == null) instance = new GerenciadorCenas();
        return instance;
    }

    // Armazena o palco da aplicação
    public void setStage(Stage p) {
        this.palco = p;
    }

    // Vincula o controller que será injetado em todas as telas
    public void setBixoController(BixoController controller) {
        this.bc = controller;
    }

    // Processa a montagem visual da cena e insere o BixoController se a tela exigir
    private void carregarCena(String caminhoFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + caminhoFxml));
            Parent root = loader.load();

            if (this.bc != null) {
                // Recupera a classe Controller associada ao FXML
                Object controllerVisual = loader.getController();

                // Se a view implementar a interface, injeta a dependência
                if (controllerVisual instanceof TelaControlavel) {
                    ((TelaControlavel) controllerVisual).inicializarTela(this.bc);
                }
            } else {
                System.out.println("BixoController Nulo");
            }

            Scene cena = new Scene(root);
            palco.setScene(cena);
            palco.show();
        } catch (IOException e) {
            System.err.println("Erro: " + caminhoFxml);
        }
    }

    // Caminhos diretos de navegação de menus isolados
    public void irParaMenuInicial()       { carregarCena("MenuInicial.fxml");      }
    public void irParaMenuIniciarJogo()   { carregarCena("MenuIniciarJogo.fxml");  }
    public void irParaMenuCarregarJogo()  { carregarCena("MenuCarregarJogo.fxml"); }
    public void irParaMinigame()          { carregarCena("Minigame.fxml");}

    // Direciona dinamicamente a view com base no estado atual da partida
    public void navegarParaLocalAtual() {
        if (this.bc == null) {
            System.err.println("BixoController não injetado no GerenciadorCenas.");
            return;
        }

        // Intercepta e sobrepõe a tela caso o jogador tenha zerado saúde ou motivação
        if (bc.verificarGameOver()) {
            carregarCena("GameOver.fxml");
            return;
        }

        // Intercepta e sobrepõe a tela caso o jogador tenha cumprido a grade
        if (bc.verificarFimDeJogo()) {
            carregarCena("Formatura.fxml");
            return;
        }

        // Força a tela de evento caso o local atual tenha gerado um encontro aleatório
        if (bc.getEventoAtual() != null) {
            carregarCena("Evento.fxml");
            return;
        }

        // Identifica e carrega o FXML correspondente ao local de posição do jogador
        String nomeLocal = bc.getLocalAtual().getNome();

        switch (nomeLocal.trim()) {
            case "Mapa Principal"    -> carregarCena("MapaPrincipal.fxml");
            case "Módulo 3"          -> carregarCena("MapaModulo3.fxml");
            case "Módulo 5"          -> carregarCena("MapaModulo5.fxml");
            case "Módulo Auxiliar"   -> carregarCena("MapaModuloAuxiliar.fxml");
            case "Cantina"           -> carregarCena("MapaCantina.fxml");
            case "Colegiado"         -> carregarCena("MapaColegiado.fxml");
            case "Ponto de Ônibus"   -> carregarCena("MapaPontoDeOnibus.fxml");
            case "Sala de Exatas",
                 "Sala de Algoritmos",
                 "LEDS"              -> carregarCena("MapaSala.fxml");
            default -> System.err.println("Erro:" + nomeLocal);
        }
    }
}